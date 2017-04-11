package com.event.management.controller;

import com.event.management.controller.create.CreateLocation;
import com.event.management.controller.exception.LocationNotFoundException;
import com.event.management.domain.Location;
import com.event.management.repository.LocationRepository;
import com.event.management.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/rest/locations")
public class LocationEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationEndpoint.class);

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationService locationService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createLocation(@RequestBody @Valid CreateLocation createLocation){

        LOGGER.debug(String.format("Creating location '%s'", createLocation.getLocationName()));

        final Location location = new Location();
        location.setLocationName(createLocation.getLocationName());
        location.setDistrict(createLocation.getDistrict());
        location.setStreet(createLocation.getStreet());
        location.setNumber(createLocation.getNumber());

        try{
            final Location createdLocation = locationService.addLocation(location);
            final URI uriLocation = URI.create("/rest/locations/" + createdLocation.getId());
            return ResponseEntity.created(uriLocation).body(createdLocation.getId());
        }catch(UnsupportedOperationException exception){
            return ResponseEntity.badRequest().body(new ObjectError("location.id", exception.getMessage()));
        }
    }

    @RequestMapping(value = "/{locationId}", method = RequestMethod.GET)
    public Location retrieveLocation(@PathVariable Long locationId){
        Location requestedLocation = locationRepository.findOne(locationId);

        if(requestedLocation == null){
            final String message = String.format("Location with id '%d' does not exist.", locationId);
            LOGGER.info(message);
            throw new LocationNotFoundException(message);
        }
        return requestedLocation;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Location> retrieveAllLocations(){
        return locationService.getAllLocations();
    }

    @RequestMapping(value = "/{locationId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLocation(@PathVariable Long locationId, @RequestBody @Valid CreateLocation createLocation){
        if(!locationRepository.exists(locationId)) {
            return ResponseEntity.badRequest().body(new ObjectError("location.id", "Invalid location ID."));
        }

        LOGGER.debug(String.format("Updating location '%s'", createLocation.getLocationName()));

        Location updatedLocation = locationRepository.findOne(locationId);
        updatedLocation.setLocationName(createLocation.getLocationName());
        updatedLocation.setDistrict(createLocation.getDistrict());
        updatedLocation.setStreet(createLocation.getStreet());
        updatedLocation.setNumber(createLocation.getNumber());

        locationService.addLocation(updatedLocation);
        URI uriLocation = URI.create("/rest/locations/" + updatedLocation.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @RequestMapping(value = "/{locationId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId){
        if(!locationRepository.exists(locationId)) {
            return ResponseEntity.badRequest().body(new ObjectError("location.id", "Invalid location ID."));
        }

        LOGGER.debug(String.format("Deleting location '%s'", locationRepository.findOne(locationId).getLocationName()));

        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }
}

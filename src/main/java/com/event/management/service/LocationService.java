package com.event.management.service;

import com.event.management.domain.Location;
import com.event.management.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Service
@Transactional
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location addLocation(Location location){
        return locationRepository.save(location);
    }

    public Location getLocation(Long locationId){
        return locationRepository.findOne(locationId);
    }

    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    public void deleteLocation(Long locationId){
        locationRepository.delete(locationId);
    }
}

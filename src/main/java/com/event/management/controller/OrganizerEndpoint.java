package com.event.management.controller;

import com.event.management.controller.create.CreateOrganizer;
import com.event.management.controller.exception.OrganizerNotFoundException;
import com.event.management.domain.Organizer;
import com.event.management.repository.OrganizerRepository;
import com.event.management.service.OrganizerService;
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

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/rest/organizers")
public class OrganizerEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizerEndpoint.class);

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private OrganizerService organizerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createOrganizer(@RequestBody @Valid CreateOrganizer createOrganizer){

        LOGGER.debug(String.format("Creating organizer '%s': ", createOrganizer.getOrganizerName()));

        final Organizer organizer = new Organizer();
        organizer.setOrganizerName(createOrganizer.getOrganizerName());
        organizer.setOrganizerDescription(createOrganizer.getOrganizerDescription());
        organizer.setSite(createOrganizer.getSite());

        try{
            final Organizer createdOrganizer = organizerService.addOrganizer(organizer);
            final URI location = URI.create("/rest/organizers/" + createdOrganizer.getId());
            return ResponseEntity.created(location).body(createdOrganizer.getId());
        } catch(UnsupportedOperationException exception){
            return ResponseEntity.badRequest().body(new ObjectError("organizer.id", exception.getMessage()));
        }
    }

    @RequestMapping(value = "/{organizerId}", method = RequestMethod.GET)
    public Organizer retrieveOrganizer(@PathVariable Long organizerId){
        Organizer requestedOrganizer = organizerRepository.findOne(organizerId);
        if(requestedOrganizer == null){
            final String message = String.format("Organizer with id '%d' does not exist.", organizerId);
            LOGGER.info(message);
            throw new OrganizerNotFoundException(message);
        }

        return requestedOrganizer;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Organizer> retrieveAllOrganizers(){
        return organizerService.getAllOrganizers();
    }

    @RequestMapping(value = "/{organizerId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrganizer(@PathVariable Long organizerId, @RequestBody @Valid CreateOrganizer createOrganizer){

        if(!organizerRepository.exists(organizerId)) {
            return ResponseEntity.badRequest().body(new ObjectError("organizer.id", "Invalid organizer ID."));
        }

        LOGGER.debug(String.format("Updating organizer '%s'", createOrganizer.getOrganizerName()));

        Organizer updatedOrganizer = organizerRepository.findOne(organizerId);
        updatedOrganizer.setOrganizerName(createOrganizer.getOrganizerName());
        updatedOrganizer.setOrganizerDescription(createOrganizer.getOrganizerDescription());
        updatedOrganizer.setSite(createOrganizer.getSite());

        organizerService.addOrganizer(updatedOrganizer);
        URI location = URI.create("/rest/organizers" + updatedOrganizer.getId());
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/{organizerId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrganizer(@PathVariable Long organizerId){

        if(!organizerRepository.exists(organizerId)) {
            return ResponseEntity.badRequest().body(new ObjectError("organizer.id", "Invalid organizer ID."));
        }

        LOGGER.debug(String.format("Deleting organizer '%s'", organizerRepository.findOne(organizerId).getOrganizerName()));

        organizerService.deleteOrganizer(organizerId);
        return ResponseEntity.noContent().build();
    }

}

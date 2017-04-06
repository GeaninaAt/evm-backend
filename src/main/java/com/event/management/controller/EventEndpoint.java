package com.event.management.controller;

import com.event.management.controller.create.CreateEvent;
import com.event.management.controller.exception.EventNotFoundException;
import com.event.management.domain.Event;
import com.event.management.repository.EventRepository;
import com.event.management.repository.LocationRepository;
import com.event.management.repository.OrganizerRepository;
import com.event.management.service.EventService;
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
@RequestMapping("/rest/events")
public class EventEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventEndpoint.class);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private LocationRepository locationRepository;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createEvent(@RequestBody @Valid CreateEvent createEvent) {

        LOGGER.debug(String.format("Creating event '%s'", createEvent.getEventName()));

        if (!organizerRepository.exists(createEvent.getOrganizerId())) {
            return ResponseEntity.badRequest().body(new ObjectError("event.organizer", "Invalid organizer ID."));
        }

        if (!locationRepository.exists(createEvent.getLocationId())) {
            return ResponseEntity.badRequest().body(new ObjectError("event.location", "Invalid location ID."));
        }

        final Event event = new Event();
        event.setEventName(createEvent.getEventName());
        event.setDescription(createEvent.getEventDescription());
        event.setOrganizer(organizerRepository.findOne(createEvent.getOrganizerId()));
        event.setLocation(locationRepository.findOne(createEvent.getLocationId()));
        event.setStartDate(createEvent.getStartDate());

        if(createEvent.getEndDate().before(createEvent.getStartDate())){
            return ResponseEntity.badRequest().body(new ObjectError("event.endDate", "Invalid end date."));
        }else {
            event.setEndDate(createEvent.getEndDate());
        }

        event.setCategory(createEvent.getCategory());
        event.setFree(createEvent.isFree());

        if (createEvent.isFree()) {
            event.setTicketPrice(0);
        } else {
            event.setTicketPrice(createEvent.getTicketPrice());
        }

        try {
            final Event createdEvent = eventService.addEvent(event);
            final URI location = URI.create("/rest/events/" + createdEvent.getId());
            return ResponseEntity.created(location).body(createdEvent.getId());
        } catch (UnsupportedOperationException ex) {
            return ResponseEntity.badRequest().body(new ObjectError("event.id", ex.getMessage()));
        }
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public Event retrieveEvent(@PathVariable Long eventId) {

        Event requestedEvent = eventRepository.findOne(eventId);
        if (requestedEvent == null) {
            final String message = String.format("Event with id '%d' does not exist.", eventId);
            LOGGER.info(message);
            throw new EventNotFoundException(message);
        }
        return requestedEvent;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Event> retrieveAllEvents() {
        return eventService.getAllEvents();
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEvent(@PathVariable Long eventId, @RequestBody @Valid CreateEvent createEvent) {

        if (!eventRepository.exists(eventId)) {
            return ResponseEntity.badRequest().body(new ObjectError("event.id", "Invalid event ID."));
        }
        if (!organizerRepository.exists(createEvent.getOrganizerId())) {
            return ResponseEntity.badRequest().body(new ObjectError("event.organizer", "Invalid organizer ID."));
        }
        if (!locationRepository.exists(createEvent.getLocationId())) {
            return ResponseEntity.badRequest().body(new ObjectError("event.location", "Invalid location ID."));
        }

        LOGGER.debug(String.format("Updating event '%s'", createEvent.getEventName()));

        Event updatedEvent = eventRepository.findOne(eventId);
        updatedEvent.setEventName(createEvent.getEventName());
        updatedEvent.setDescription(createEvent.getEventDescription());
        updatedEvent.setOrganizer(organizerRepository.findOne(createEvent.getOrganizerId()));
        updatedEvent.setLocation(locationRepository.findOne(createEvent.getLocationId()));


        updatedEvent.setStartDate(createEvent.getStartDate());
        updatedEvent.setEndDate(createEvent.getEndDate());
        updatedEvent.setCategory(createEvent.getCategory());
        updatedEvent.setFree(createEvent.isFree());
        updatedEvent.setTicketPrice(createEvent.getTicketPrice());

        eventService.addEvent(updatedEvent);
        URI location = URI.create("/rest/events/" + updatedEvent.getId());
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) {

        if (!eventRepository.exists(eventId)) {
            return ResponseEntity.badRequest().body(new ObjectError("event.id", "Invalid event ID."));
        }

        LOGGER.debug(String.format("Deleting event '%s'", eventRepository.findOne(eventId).getEventName()));

        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}

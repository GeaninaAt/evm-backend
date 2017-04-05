package com.event.management.service;

import com.event.management.domain.Event;
import com.event.management.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event addEvent(Event event){
        return eventRepository.save(event);
    }

    public Event getEvent(Long eventId){
        return eventRepository.findOne(eventId);
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public void deleteEvent(Long eventId){
        eventRepository.delete(eventId);
    }
}

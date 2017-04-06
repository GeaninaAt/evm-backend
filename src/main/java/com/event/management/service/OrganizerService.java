package com.event.management.service;

import com.event.management.domain.Organizer;
import com.event.management.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Service
@Transactional
public class OrganizerService {

    @Autowired
    private OrganizerRepository organizerRepository;

    public Organizer addOrganizer(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    public Organizer getOrganizer(Long organizerId) {
        return organizerRepository.findOne(organizerId);
    }

    public List<Organizer> getAllOrganizers() {
        return organizerRepository.findAll();
    }

    public void deleteOrganizer(Long organizerId) {
        organizerRepository.delete(organizerId);
    }
}

package com.event.management.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Entity
public class Organizer extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String organizerName;
    private String organizerDescription;
    private String site;

    @OneToMany
    private List<Event> events;

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getOrganizerDescription() {
        return organizerDescription;
    }

    public void setOrganizerDescription(String organizerDescription) {
        this.organizerDescription = organizerDescription;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}

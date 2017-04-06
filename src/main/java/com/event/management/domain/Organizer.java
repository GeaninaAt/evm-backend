package com.event.management.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Entity
public class Organizer extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private static final int MINIMUM_LENGTH = 2;
    private static final int MAXIMUM_LENGTH = 50;

    @NotBlank(message = "{organizer.validation.error.organizerName}")
    @Pattern(regexp = "^([a-zA-Z]+([ '-][a-zA-Z]+)*){" + MINIMUM_LENGTH + "," + MAXIMUM_LENGTH + "}$", message = "{organizer.validation.error.match.organizerName}")
    private String organizerName;

    @Lob
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

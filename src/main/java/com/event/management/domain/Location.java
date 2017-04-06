package com.event.management.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Entity
public class Location extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private static final int MINIMUM_LENGTH = 2;
    private static final int MAXIMUM_LENGTH = 50;

    @NotBlank(message = "{location.validation.error.locationName}")
    @Pattern(regexp = "^([a-zA-Z]+([ '-][a-zA-Z]+)*){" + MINIMUM_LENGTH + "," + MAXIMUM_LENGTH + "}$", message = "{location.validation.error.match.locationName}")
    private String locationName;

    private String district;
    private String street;
    private String number;

    private boolean isIndoor;

    @OneToMany
    private List<Event> events;

    //private double latitude;
    //private double longitude;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isIndoor() {
        return isIndoor;
    }

    public void setIndoor(boolean indoor) {
        isIndoor = indoor;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}

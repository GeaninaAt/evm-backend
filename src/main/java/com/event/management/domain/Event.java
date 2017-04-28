package com.event.management.domain;

import com.event.management.domain.validation.CheckEventCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Entity
public class Event extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private static final int MINIMUM_LENGTH = 2;
    private static final int MAXIMUM_LENGTH = 50;

    @NotBlank(message = "{event.validation.error.eventName}")
    @Pattern(regexp = "^([a-zA-Z]+([ '-][a-zA-Z]+)*){" + MINIMUM_LENGTH + "," + MAXIMUM_LENGTH + "}$", message = "{event.validation.error.match.eventName}")
    private String eventName;

    @Lob
    private String description;

    @ManyToOne
    private Organizer organizer;

    @ManyToOne
    private Location location;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "{event.validation.error.startDate}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "{event.validation.error.endDate}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @CheckEventCategory
    private String category;

    private boolean isFree;

    private double ticketPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    private Rating rating;

    private Float totalScore;


/*    @Transient
    private List<Long> usersIds;

    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "event-eventuser")
    @JsonIgnore
    private Set<EventUser> eventUsers;

    @Transient
    private List<User> users;*/



    public enum Category{
        MUSIC, THEATRE, SPORTS, TECHNOLOGY, OTHER;

        public static List<String> getAllCategories(){
            List<String> categories = new ArrayList<>();
            categories.add(MUSIC.name());
            categories.add(THEATRE.name());
            categories.add(SPORTS.name());
            categories.add(TECHNOLOGY.name());
            categories.add(OTHER.name());

            return categories;
        }
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
    }


/*
    public List<Long> getUsersIds() {
        return usersIds;
    }

    public void setUsersIds(List<Long> usersIds) {
        this.usersIds = usersIds;
    }

    public Set<EventUser> getEventUsers() {
        return eventUsers;
    }

    public void setEventUsers(Set<EventUser> eventUsers) {
        this.eventUsers = eventUsers;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }*/
}

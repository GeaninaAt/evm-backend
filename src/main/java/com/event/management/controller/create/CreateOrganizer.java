package com.event.management.controller.create;

/**
 * Created by gatomulesei on 4/5/2017.
 */
public class CreateOrganizer {

    private String organizerName;
    private String organizerDescription;
    private String site;

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
}

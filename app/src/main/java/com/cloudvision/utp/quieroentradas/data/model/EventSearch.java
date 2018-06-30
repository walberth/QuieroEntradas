package com.cloudvision.utp.quieroentradas.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EventSearch {
    private String idUser;
    private long dateTimeSearch;
    private String eventName;
    private String eventDate;
    private String eventPicture;
    private String eventDescription;
    private String idLocation;
    private String groupName;

    public EventSearch() {
    }

    public EventSearch(String idUser, long dateTimeSearch, String eventName, String eventDate, String eventPicture, String eventDescription, String idLocation, String groupName) {
        this.idUser = idUser;
        this.dateTimeSearch = dateTimeSearch;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventPicture = eventPicture;
        this.eventDescription = eventDescription;
        this.idLocation = idLocation;
        this.groupName = groupName;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public long getDateTimeSearch() {
        return dateTimeSearch;
    }

    public void setDateTimeSearch(long dateTimeSearch) {
        this.dateTimeSearch = dateTimeSearch;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

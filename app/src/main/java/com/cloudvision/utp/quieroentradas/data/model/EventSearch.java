package com.cloudvision.utp.quieroentradas.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class EventSearch {
    private String uid;
    private String idUser;
    private String eventName;
    private String eventDate;
    private String eventPicture;
    private String eventDescription;
    private String idLocation;

    public EventSearch() {
    }

    public EventSearch(String uid, String idUser, String eventName, String eventDate, String eventPicture, String eventDescription, String idLocation) {
        this.uid = uid;
        this.idUser = idUser;
        long dateTimeSearch = new Date().getTime();
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventPicture = eventPicture;
        this.eventDescription = eventDescription;
        this.idLocation = idLocation;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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
}

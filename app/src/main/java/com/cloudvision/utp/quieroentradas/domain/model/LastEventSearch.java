package com.cloudvision.utp.quieroentradas.domain.model;

/**
 * Created by Walberth Gutierrez Telles on 03,July,2018
 */
public class LastEventSearch
{
    private String idFirebaseEventSearch;
    private String latitud;
    private String longitud;
    private String eventName;
    private String eventGroup;
    private String eventPicture;

    public LastEventSearch() {
    }

    public LastEventSearch(String eventPicture, String eventName) {
        this.eventPicture = eventPicture;
        this.eventName = eventName;
    }

    public String getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}

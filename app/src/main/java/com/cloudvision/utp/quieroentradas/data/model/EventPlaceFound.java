package com.cloudvision.utp.quieroentradas.data.model;

/**
 * Created by Walberth Gutierrez Telles on 04,July,2018
 */
public class EventPlaceFound {
    private String idFirebaseEventSearch;
    private String latitud;
    private String longitud;
    private String eventName;
    private String eventGroup;
    private String eventPicture;
    private long DateTimeRegister;
    private String userSearchKey;

    public EventPlaceFound() {
    }

    public EventPlaceFound(String userSearchKey, String idFirebaseEventSearch, String latitud, String longitud, String eventName, String eventGroup, long dateTimeRegister, String eventPicture) {
        this.userSearchKey = userSearchKey;
        this.idFirebaseEventSearch = idFirebaseEventSearch;
        this.latitud = latitud;
        this.longitud = longitud;
        this.eventName = eventName;
        this.eventGroup = eventGroup;
        this.DateTimeRegister = dateTimeRegister;
        this.eventPicture = eventPicture;
    }

    public String getUserSearchKey() {
        return userSearchKey;
    }

    public void setUserSearchKey(String userSearchKey) {
        this.userSearchKey = userSearchKey;
    }

    public String getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }

    public String getIdFirebaseEventSearch() {
        return idFirebaseEventSearch;
    }

    public void setIdFirebaseEventSearch(String idFirebaseEventSearch) {
        this.idFirebaseEventSearch = idFirebaseEventSearch;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventGroup() {
        return eventGroup;
    }

    public void setEventGroup(String eventGroup) {
        this.eventGroup = eventGroup;
    }

    public long getDateTimeRegister() {
        return DateTimeRegister;
    }

    public void setDateTimeRegister(long dateTimeRegister) {
        DateTimeRegister = dateTimeRegister;
    }
}
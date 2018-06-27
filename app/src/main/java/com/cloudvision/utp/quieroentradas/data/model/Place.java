package com.cloudvision.utp.quieroentradas.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Place {
    private String eventid;
    private String name;
    private String direction;
    private String latitud;
    private String longitud;

    public Place() {
    }

    public Place(String eventid, String name, String direction, String latitud, String longitud) {
        this.eventid = eventid;
        this.name = name;
        this.direction = direction;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
}

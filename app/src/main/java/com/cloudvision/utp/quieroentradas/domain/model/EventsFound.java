package com.cloudvision.utp.quieroentradas.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Walberth Gutierrez Telles on 24,June,2018
 */
public class EventsFound implements Parcelable {
    private String eventName;
    private String eventLocation;
    private String eventPicture;
    private String eventId;
    private String eventSongClickId;
    private String eventLocationId;
    private String latitud;
    private String longitud;
    private String eventGroup;
    private String userSearchKey;

    public EventsFound() {
    }

    public EventsFound(String userSearchKey, String eventGroup, String latitud, String longitud, String eventId, String eventSongClickId, String eventLocationId, String eventName, String eventLocation, String eventPicture) {
        this.userSearchKey = userSearchKey;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventPicture = eventPicture;
        this.eventId = eventId;
        this.eventSongClickId = eventSongClickId;
        this.eventLocationId = eventLocationId;
        this.latitud = latitud;
        this.longitud = longitud;
        this.eventGroup = eventGroup;
    }

    protected EventsFound(Parcel in) {
        eventName = in.readString();
        eventLocation = in.readString();
        eventPicture = in.readString();
    }

    public static final Creator<EventsFound> CREATOR = new Creator<EventsFound>() {
        @Override
        public EventsFound createFromParcel(Parcel in) {
            return new EventsFound(in);
        }

        @Override
        public EventsFound[] newArray(int size) {
            return new EventsFound[size];
        }
    };

    public String getUserSearchKey() {
        return userSearchKey;
    }

    public void setUserSearchKey(String userSearchKey) {
        this.userSearchKey = userSearchKey;
    }

    public String getEventGroup() {
        return eventGroup;
    }

    public void setEventGroup(String eventGroup) {
        this.eventGroup = eventGroup;
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventSongClickId() {
        return eventSongClickId;
    }

    public void setEventSongClickId(String eventSongClickId) {
        this.eventSongClickId = eventSongClickId;
    }

    public String getEventLocationId() {
        return eventLocationId;
    }

    public void setEventLocationId(String eventLocationId) {
        this.eventLocationId = eventLocationId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(eventLocation);
        dest.writeString(eventPicture);
    }
}

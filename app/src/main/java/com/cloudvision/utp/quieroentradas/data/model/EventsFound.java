package com.cloudvision.utp.quieroentradas.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Walberth Gutierrez Telles on 24,June,2018
 */
public class EventsFound implements Parcelable {
    private String eventName;
    private String eventLocation;
    private String eventPicture;

    public EventsFound() {
    }

    public EventsFound(String eventName, String eventLocation, String eventPicture) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventPicture = eventPicture;
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

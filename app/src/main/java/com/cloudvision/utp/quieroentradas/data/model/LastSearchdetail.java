package com.cloudvision.utp.quieroentradas.data.model;

public class LastSearchdetail {
    private String eventName;
    private String eventLocationidplace;
    private String eventPicture;
    private String eventId;
    private String eventgroupName;
    private String idUser;
    private String eventDescription;
    private String eventDate;
    private Long eventdateTimeSearch;


    public LastSearchdetail() {
    }

    public LastSearchdetail(String eventName, String eventLocationidplace, String eventPicture, String eventId, String eventgroupName, String idUser, String eventDescription, String eventDate, Long eventdateTimeSearch) {
        this.setEventName(eventName);
        this.setEventLocationidplace(eventLocationidplace);
        this.setEventPicture(eventPicture);
        this.setEventId(eventId);
        this.setEventgroupName(eventgroupName);
        this.setIdUser(idUser);
        this.setEventDescription(eventDescription);
        this.setEventDate(eventDate);
        this.setEventdateTimeSearch(eventdateTimeSearch);
    }





    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocationidplace() {
        return eventLocationidplace;
    }

    public void setEventLocationidplace(String eventLocationidplace) {
        this.eventLocationidplace = eventLocationidplace;
    }

    public String getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventgroupName() {
        return eventgroupName;
    }

    public void setEventgroupName(String eventgroupName) {
        this.eventgroupName = eventgroupName;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Long getEventdateTimeSearch() {
        return eventdateTimeSearch;
    }

    public void setEventdateTimeSearch(Long eventdateTimeSearch) {
        this.eventdateTimeSearch = eventdateTimeSearch;
    }
}

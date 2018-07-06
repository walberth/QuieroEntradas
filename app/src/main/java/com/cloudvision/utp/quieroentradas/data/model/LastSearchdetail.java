package com.cloudvision.utp.quieroentradas.data.model;

public class LastSearchdetail {
    private Long eventdateTimeSearch;
    private String eventDate;
    private String eventDescription;
    private String eventName;
    private String eventPicture;
    private String eventgroupName;
    private String eventId;

    private String eventLocationidplace;
    private String idUser;
    private String eventlatitud;
    private String eventlongitud;
    private String eventplacedirection;
    private String eventplacename;
    private String eventuid;
    private String eventuserSearchKey;




    public LastSearchdetail(){

    }

    public LastSearchdetail(Long eventdateTimeSearch, String eventDate, String eventDescription, String eventName, String eventPicture, String eventgroupName, String eventId, String eventLocationidplace, String idUser, String eventlatitud, String eventlongitud, String eventplacedirection, String eventplacename, String eventuid, String eventuserSearchKey) {
        this.setEventdateTimeSearch(eventdateTimeSearch);
        this.setEventDate(eventDate);
        this.setEventDescription(eventDescription);
        this.setEventName(eventName);
        this.setEventPicture(eventPicture);
        this.setEventgroupName(eventgroupName);
        this.setEventId(eventId);
        this.setEventLocationidplace(eventLocationidplace);
        this.setIdUser(idUser);
        this.setEventlatitud(eventlatitud);
        this.setEventlongitud(eventlongitud);
        this.setEventplacedirection(eventplacedirection);
        this.setEventplacename(eventplacename);
        this.setEventuid(eventuid);
        this.setEventuserSearchKey(eventuserSearchKey);
    }

    public Long getEventdateTimeSearch() {
        return eventdateTimeSearch;
    }

    public void setEventdateTimeSearch(Long eventdateTimeSearch) {
        this.eventdateTimeSearch = eventdateTimeSearch;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }

    public String getEventgroupName() {
        return eventgroupName;
    }

    public void setEventgroupName(String eventgroupName) {
        this.eventgroupName = eventgroupName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventLocationidplace() {
        return eventLocationidplace;
    }

    public void setEventLocationidplace(String eventLocationidplace) {
        this.eventLocationidplace = eventLocationidplace;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getEventlatitud() {
        return eventlatitud;
    }

    public void setEventlatitud(String eventlatitud) {
        this.eventlatitud = eventlatitud;
    }

    public String getEventlongitud() {
        return eventlongitud;
    }

    public void setEventlongitud(String eventlongitud) {
        this.eventlongitud = eventlongitud;
    }

    public String getEventplacedirection() {
        return eventplacedirection;
    }

    public void setEventplacedirection(String eventplacedirection) {
        this.eventplacedirection = eventplacedirection;
    }

    public String getEventplacename() {
        return eventplacename;
    }

    public void setEventplacename(String eventplacename) {
        this.eventplacename = eventplacename;
    }

    public String getEventuid() {
        return eventuid;
    }

    public void setEventuid(String eventuid) {
        this.eventuid = eventuid;
    }

    public String getEventuserSearchKey() {
        return eventuserSearchKey;
    }

    public void setEventuserSearchKey(String eventuserSearchKey) {
        this.eventuserSearchKey = eventuserSearchKey;
    }
}


package com.cloudvision.utp.quieroentradas.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserSearch {
    private String idUser;
    private String picture;
    private long dateTimeSearch;
    private String groupName;

    public UserSearch() {
    }

    public UserSearch(String idUser, String picture, long dateTimeSearch, String groupName) {
        this.idUser = idUser;
        this.picture = picture;
        this.dateTimeSearch = dateTimeSearch;
        this.groupName = groupName;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getDateTimeSearch() {
        return dateTimeSearch;
    }

    public void setDateTimeSearch(long dateTimeSearch) {
        this.dateTimeSearch = dateTimeSearch;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

package com.cloudvision.utp.quieroentradas.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class UserSearch {
    private String uid;
    private String idUser;
    private String picture;

    public UserSearch() {
    }

    public UserSearch(String uid, String idUser, String picture) {
        this.uid = uid;
        this.idUser = idUser;
        long dateTimeSearch = new Date().getTime();
        this.picture = picture;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

package com.cloudvision.utp.quieroentradas.data.model;

import java.util.Date;

public class CommentPlace {
    private String uid;
    private String idPlace;
    private String idUser;
    private String messageText;

    public CommentPlace() {
    }

    //TODO: NECESARRY CHANGE
    public CommentPlace(String uid, String idPlace, String idUser, String messageText) {
        this.uid = uid;
        this.idPlace = idPlace;
        this.idUser = idUser;
        this.messageText = messageText;
        long dateTimeComment = new Date().getTime();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}

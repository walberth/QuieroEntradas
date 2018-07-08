package com.cloudvision.utp.quieroentradas.data.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CommentPlace {
    private String idSongClickPlace;
    private String idUser;
    private String messageText;
    private long dateTimeComment;
    private String userCompleteName;

    public CommentPlace() {
    }

    public CommentPlace(String idSongClickPlace, String idUser, String messageText, long dateTimeComment, String userCompleteName) {
        this.idUser = idUser;
        this.idSongClickPlace = idSongClickPlace;
        this.messageText = messageText;
        this.dateTimeComment = dateTimeComment;
        this.userCompleteName = userCompleteName;
    }

    public String getUserCompleteName() {
        return userCompleteName;
    }

    public void setUserCompleteName(String userCompleteName) {
        this.userCompleteName = userCompleteName;
    }

    public String getIdSongclickPlace() {
        return idSongClickPlace;
    }

    public void setIdSongclickPlace(String idSongClickPlace) {
        this.idSongClickPlace = idSongClickPlace;
    }

    public long getDateTimeComment() {
        return dateTimeComment;
    }

    public void setDateTimeComment(long dateTimeComment) {
        this.dateTimeComment = dateTimeComment;
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

package com.cloudvision.utp.quieroentradas.data.model;

/**
 * Created by Walberth Gutierrez Telles on 27,June,2018
 */
public class LastSearch {
    private String pictureSearched;
    private String groupName;
    private String dateTimeSearched;

    public LastSearch() {
    }

    public LastSearch(String pictureSearched, String groupName, String dateTimeSearched) {
        this.pictureSearched = pictureSearched;
        this.groupName = groupName;
        this.dateTimeSearched = dateTimeSearched;
    }

    public String getPictureSearched() {
        return pictureSearched;
    }

    public void setPictureSearched(String pictureSearched) {
        this.pictureSearched = pictureSearched;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDateTimeSearched() {
        return dateTimeSearched;
    }

    public void setDateTimeSearched(String dateTimeSearched) {
        this.dateTimeSearched = dateTimeSearched;
    }
}

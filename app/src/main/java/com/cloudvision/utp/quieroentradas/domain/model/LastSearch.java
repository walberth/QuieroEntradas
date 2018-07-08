package com.cloudvision.utp.quieroentradas.domain.model;

/**
 * Created by Walberth Gutierrez Telles on 27,June,2018
 */
public class LastSearch {
    private String pictureSearched;
    private String groupName;
    private String dateTimeSearched;
    private String imageStorageName;
    private String keyUserImageSearch;

    public LastSearch() {
    }

    public LastSearch(String keyUserImageSearch, String imageStorageName, String pictureSearched, String groupName, String dateTimeSearched) {
        this.pictureSearched = pictureSearched;
        this.groupName = groupName;
        this.dateTimeSearched = dateTimeSearched;
        this.imageStorageName = imageStorageName;
        this.keyUserImageSearch = keyUserImageSearch;
    }

    public String getKeyUserImageSearch() {
        return keyUserImageSearch;
    }

    public void setKeyUserImageSearch(String keyUserImageSearch) {
        this.keyUserImageSearch = keyUserImageSearch;
    }

    public String getPictureSearched() {
        return pictureSearched;
    }

    public void setPictureSearched(String pictureSearched) {
        this.pictureSearched = pictureSearched;
    }

    public String getImageStorageName() {
        return imageStorageName;
    }

    public void setImageStorageName(String imageStorageName) {
        this.imageStorageName = imageStorageName;
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

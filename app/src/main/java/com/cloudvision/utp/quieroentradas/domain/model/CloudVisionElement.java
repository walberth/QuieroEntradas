package com.cloudvision.utp.quieroentradas.domain.model;

/**
 * Created by Walberth Gutierrez Telles on 02,July,2018
 */
public class CloudVisionElement {
    private String descriptionFounded;
    private Float scoreFounded;

    public CloudVisionElement() {
    }

    public CloudVisionElement(String descriptionFounded, Float scoreFounded) {
        this.descriptionFounded = descriptionFounded;
        this.scoreFounded = scoreFounded;
    }

    public String getDescriptionFounded() {
        return descriptionFounded;
    }

    public void setDescriptionFounded(String descriptionFounded) {
        this.descriptionFounded = descriptionFounded;
    }

    public Float getScoreFounded() {
        return scoreFounded;
    }

    public void setScoreFounded(Float scoreFounded) {
        this.scoreFounded = scoreFounded;
    }
}

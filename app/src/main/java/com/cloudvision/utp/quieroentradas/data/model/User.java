package com.cloudvision.utp.quieroentradas.data.model;

import java.util.Date;

public class User {
    private String uid;
    private String idLogin;
    private String name;
    private String lastName;
    private String email;

    public User() {
    }

    public User(String uid, String idLogin, String name, String lastName, String email) {
        this.uid = uid;
        this.uid = idLogin;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        long userCreatedTime = new Date().getTime();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(String idLogin) {
        this.idLogin = idLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

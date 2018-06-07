package com.cloudvision.utp.quieroentradas.data.model;

public class User {
    private String idLogin;
    private String name;
    private String lastName;
    private String email;
    private long userCreatedTime;

    public User() {
    }

    public User(String idLogin, String name, String lastName, String email, long userCreatedTime) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.userCreatedTime = userCreatedTime;
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

    public long getUserCreatedTime() {
        return userCreatedTime;
    }

    public void setUserCreatedTime(long userCreatedTime) {
        this.userCreatedTime = userCreatedTime;
    }
}

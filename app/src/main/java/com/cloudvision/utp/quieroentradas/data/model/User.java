package com.cloudvision.utp.quieroentradas.data.model;

public class User {
    private String idLogin;
    private String name;
    private String lastName;
    private String email;
    private long userCreatedTime;
    private String username;
    private String sexName;
    private String sexUri;

    public User() {
    }

    public User(String idLogin, String name, String lastName, String email, long userCreatedTime, String username,
                String sexName, String sexUri) {
        this.idLogin = idLogin;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.userCreatedTime = userCreatedTime;
        this.username = username;
        this.sexName = sexName;
        this.sexUri = sexUri;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getSexUri() {
        return sexUri;
    }

    public void setSexUri(String sexUri) {
        this.sexUri = sexUri;
    }
}

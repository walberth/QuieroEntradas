package com.cloudvision.utp.quieroentradas.data.model;

/**
 * Created by gustavorm on 21/10/2017.
 */

public class UserResponse {

    private String email;
    private String username;
    private String name;
    private String lastname;
    private String sexName;
    private String sexUri;

    public UserResponse() {
    }

    public UserResponse(String email, String username, String name, String lastname, String sexName,
                        String sexUri) {
        this.setEmail(email);
        this.setUsername(username);
        this.setName(name);
        this.setLastname(lastname);
        this.setSexName(sexName);
        this.setSexUri(sexUri);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

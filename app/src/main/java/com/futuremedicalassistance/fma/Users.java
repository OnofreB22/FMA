package com.futuremedicalassistance.fma;

public class Users {

    String name, lastName, imageURL, id, status;

    public Users() {
    }

    public Users(String name, String lastName, String imageURL,String id,String status) {
        this.name = name;
        this.lastName = lastName;
        this.imageURL = imageURL;
        this.id = id;
        this.status = status;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
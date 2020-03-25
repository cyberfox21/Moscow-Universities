package com.example.registration;

public class Card {

    public int picture;
    public String title;
    public String description;

    public Card(){}

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Card(String title, String description, int picture) {
        this.title = title;
        this.description = description;
        this.picture = picture;
    }
}
package com.example.registration;

public class Card {

    public String title;
    private String title_descr;
    private String descr;
    private String site;
    private Double x;
    private Double y;
    private String logo;
    private String image;

    public Card(){}

    public Card(String title, String title_descr, String descr, String site, Double x, Double y, String logo, String image) {
        this.title = title;
        this.title_descr = title_descr;
        this.descr = descr;
        this.site = site;
        this.x = x;
        this.y = y;
        this.logo = logo;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_descr() {
        return title_descr;
    }

    public void setTitle_descr(String title_descr) {
        this.title_descr = title_descr;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
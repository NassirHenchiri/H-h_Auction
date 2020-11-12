package com.Exodia.H_and_N.entity;

import java.util.Date;

public class Produit {

    private int id;
    private String image;
    private String name;

    private  String description;

    private  double prixdebut;
    private   int encour;
    private int user_id;
    private Date tobid_at;

    public Produit(int id, String image, String name, String description, double prixdebut, int encour, int user_id, Date tobid_at) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.prixdebut = prixdebut;
        this.encour = encour;
        this.user_id = user_id;
        this.tobid_at = tobid_at;
    }

    public Produit(int id, String image, String name, String description, double prixdebut, int encour, int user_id) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.prixdebut = prixdebut;
        this.encour = encour;
        this.user_id = user_id;
    }

    public Produit(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrption() {
        return description;
    }

    public void setDescrption(String description) {
        this.description = description;
    }

    public double getPrixdebut() {
        return prixdebut;
    }

    public void setPrixdebut(double prixdebut) {
        this.prixdebut = prixdebut;
    }

    public int getEncour() {
        return encour;
    }

    public void setEncour(int encour) {
        this.encour = encour;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getTobid_at() {
        return tobid_at;
    }

    public void setTobid_at(Date tobid_at) {
        this.tobid_at = tobid_at;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", prixdebut=" + prixdebut +
                ", encour=" + encour +
                ", user_id=" + user_id +
                '}';
    }
}

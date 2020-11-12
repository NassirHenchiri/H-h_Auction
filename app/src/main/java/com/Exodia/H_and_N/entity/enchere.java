package com.Exodia.H_and_N.entity;

public class enchere {
    int id;
    int id_user_achete;
    int id_produit;
    double cost;

    public enchere(int id, int id_user_achete, int id_produit, double cost) {
        this.id = id;
        this.id_user_achete = id_user_achete;
        this.id_produit = id_produit;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user_achete() {
        return id_user_achete;
    }

    public void setId_user_achete(int id_user_achete) {
        this.id_user_achete = id_user_achete;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "enchere{" +
                "id=" + id +
                ", id_user_achete=" + id_user_achete +
                ", id_produit=" + id_produit +
                ", cost=" + cost +
                '}';
    }
}

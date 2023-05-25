package com.example.lawsystem;

public class advocates {

    private int id;

    private String name;

    private String experiance;

    private double rating;

    private double fee;

    private int image;

    public advocates(int id, String name, String experiance, double rating, double fee, int image) {
        this.id = id;
        this.name = name;
        this.experiance = experiance;
        this.rating = rating;
        this.fee = fee;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperiance() {
        return experiance;
    }

    public void setExperiance(String experiance) {
        this.experiance = experiance;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

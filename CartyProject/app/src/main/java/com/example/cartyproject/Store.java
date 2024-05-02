package com.example.cartyproject;

public class Store {
    private String name;
    private String location;
    private int imageId;  // Optional, if you have store images

    public Store(String name, String location, int imageId) {
        this.name = name;
        this.location = location;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getImageId() {
        return imageId;
    }
}

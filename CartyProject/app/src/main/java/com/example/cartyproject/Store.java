package com.example.cartyproject;

public class Store {
    private String name;
    private String location;
    private String facing;
    private String imageUrl;

    public Store(String name, String location, String facing, String imageUrl) {
        this.name = name;
        this.location = location;
        this.facing = facing;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getFacing() {
        return facing;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

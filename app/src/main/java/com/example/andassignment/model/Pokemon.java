package com.example.andassignment.model;

public class Pokemon {

    private int id;
    private String originalName;
    private String customName;
    private String imageUrl;

    public Pokemon() {
    }

    public Pokemon(int id, String originalName, String customName, String imageUrl) {
        this.id = id;
        this.originalName = originalName;
        this.customName = customName;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

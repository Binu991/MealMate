package com.example.mealmate.model;

public class Ingredient {
    private String name;
    private String description;
    private int quantity;
    private double price;
    private String imageUri;
    private boolean isPurchased;

    public Ingredient(String name, String description, int quantity, double price, String imageUri) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.imageUri = imageUri;
        this.isPurchased = false; // default to not purchased
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}

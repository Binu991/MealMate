package com.example.mealmate;

public class GroceryItem {
    private String name;
    private String quantity;
    private String category;
    private String price;
    private String storeName;
    private boolean purchased;
    private boolean selected;
    private String userEmail;

    public GroceryItem() {
        this.name = "";
        this.quantity = "";
        this.category = "";
        this.price = "";
        this.storeName = "";
        this.purchased = false;
        this.selected = false;
        this.userEmail = "";
    }

    public GroceryItem(String name, String quantity, String category) {
        this(name, quantity, category, "", "", false, "");
    }

    public GroceryItem(String name, String quantity, String category, String price, String storeName) {
        this(name, quantity, category, price, storeName, false, "");
    }

    public GroceryItem(String name, String quantity, String category, String price, String storeName, boolean purchased) {
        this(name, quantity, category, price, storeName, purchased, "");
    }

    public GroceryItem(String name, String quantity, String category, String price, String storeName, boolean purchased, String userEmail) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
        this.storeName = storeName;
        this.purchased = purchased;
        this.selected = false;
        this.userEmail = userEmail;
    }

    public String getName() { return name; }
    public String getQuantity() { return quantity; }
    public String getCategory() { return category; }
    public String getPrice() { return price; }
    public String getStoreName() { return storeName; }
    public boolean isPurchased() { return purchased; }
    public boolean isSelected() { return selected; }
    public String getUserEmail() { return userEmail; }

    public void setName(String name) { this.name = name; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(String price) { this.price = price; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public void setPurchased(boolean purchased) { this.purchased = purchased; }
    public void setSelected(boolean selected) { this.selected = selected; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}

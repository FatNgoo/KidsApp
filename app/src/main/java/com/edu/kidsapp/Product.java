package com.edu.kidsapp;

/**
 * Product - Data model for shop items
 */
public class Product {
    private String name;
    private int price;
    private int imageResId;

    /**
     * Constructor
     * @param name Product name
     * @param price Product price in coins
     * @param imageResId Drawable resource ID for product image
     */
    public Product(String name, int price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}

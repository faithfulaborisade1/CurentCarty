package com.example.cartyproject;

public class Product {
    private String name;
    private String category;
    private String description;
    private double price;
    private String productPic;

    public Product(String name, String category, String description, double price, String productPic) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.productPic = productPic;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getProductPic() {
        return productPic;
    }
}

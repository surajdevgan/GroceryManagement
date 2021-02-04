package com.surajdev.grocerymanagementsystem;

public class ProductModel {

    int product_id;
    String product_name, imageUrl, category, price, discount_price, attributes, quantity;

    public ProductModel() {
    }


    // eh category wise products da constructor va
    public ProductModel(int product_id, String product_name, String imageUrl, String category, String price, String discount_price, String attributes) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.discount_price = discount_price;
        this.attributes = attributes;
    }


// eh mycart items da constructor va

    public ProductModel(int product_id, String product_name, String imageUrl, String price, String discount_price,  String quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.discount_price = discount_price;
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                ", discount_price='" + discount_price + '\'' +
                ", attributes='" + attributes + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
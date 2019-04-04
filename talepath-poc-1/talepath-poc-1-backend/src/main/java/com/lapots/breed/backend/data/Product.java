package com.lapots.breed.backend.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class Product implements Serializable {

    private int id = -1;
    private String productName = "";
    private BigDecimal price = BigDecimal.ZERO;
    private Set<Category> category;
    private int stockCount = 0;
    private Availability availability = Availability.COMING;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public boolean isNewProduct() {
        return getId() == -1;
    }

}

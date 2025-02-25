package com.files;

public class Stock {
    private String label;
    private int quantity;

    public Stock(String label, int quantity) {
        this.label = label;
        this.quantity = quantity;
    }

    public String getLabel() {
        return label;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

package com.berry.groceryapp.model;

public class StockItemData {
    private int itemCode;
    private String itemName;
    private int qtyStock;
    private float price;
    private boolean returnable;

    public StockItemData(int itemCode, String itemName, int qtyStock, float price, boolean returnable) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.qtyStock = qtyStock;
        this.price = price;
        this.returnable = returnable;
    }

    public int getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQtyStock() {
        return qtyStock;
    }

    public float getPrice() {
        return price;
    }

    public boolean isReturnable() {
        return returnable;
    }
}

package com.example.listitgrocery;

public class GroceryItem {

    private boolean isChecked;
    private final String itemName;

    public GroceryItem(String itemName, boolean checked) {
        this.isChecked =checked;
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}

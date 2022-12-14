package com.example.listitgrocery;

public class GItem {
    private boolean checked;
    private String ItemName;

    public GItem(String Itemname, boolean checked) {
        this.checked=checked;
        this.ItemName = Itemname;
    }

    public String getItemName() {
        return ItemName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

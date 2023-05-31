package com.example.listitgrocery;

import java.io.Serializable;
import java.util.ArrayList;

public class Grocery implements Serializable {
    private String name;
    private ArrayList<GroceryItem> items;

    public Grocery(String name){
       this.name=name;
   }

   public String getName() {
        return name;
    }

   public ArrayList<GroceryItem> getItems() {
        return items;
    }

   public void setItems(ArrayList<GroceryItem> items) {
        this.items=items;
    }

}

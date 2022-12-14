package com.example.listitgrocery;

import java.io.Serializable;
import java.util.ArrayList;

public class Grocery implements Serializable {
    private String name;
    private ArrayList<GItem> items;

    public Grocery(String name){
       this.name=name;
   }

   public String getName() {
        return name;
    }

   public ArrayList<GItem> getItems() {
        return items;
    }

   public void setItems(ArrayList<GItem> items) {
        this.items=items;
    }

}

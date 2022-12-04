package com.example.listitgrocery;

import java.util.ArrayList;

public class Grocery {
   public Grocery(String name){
       this.name=name;
   }

   public String getName() {
        return name;
    }

   public void setName(String name) {
        this.name = name;
    }

   private String name;
   private ArrayList<GItem> items;
   public void setItems(ArrayList<GItem> items) {
        this.items=items;
    }
}

package com.example.listitgrocery;

public class Recipe { //implements Recipe API

    private String recipeName;
    private GroceryList ingredients;

    Recipe() {

    }

    public String getRecipeName() {
        return recipeName;
    }

    public GroceryList getIngredients() {
        return ingredients;
    }
}

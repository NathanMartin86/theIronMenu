package com.theironyard;

/**
 * Created by macbookair on 11/6/15.
 */
public class MenuItem {
    int id;
    String name;
    String type;
    boolean breakfast;
    boolean lunch;
    boolean dinner;
    double price;
    Boolean vegetarian;
    Boolean glutenFree;
    int priceRange;

    public MenuItem(){}

    public int getId() {return id;}

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isLunch() {return lunch;}

    public boolean isDinner() {return dinner;}

    public double getPrice() {
        return price;
    }

    public Boolean getVegetarian() {
        return vegetarian;
    }

    public Boolean getGlutenFree() {
        return glutenFree;
    }

    public int getPriceRange() {
        return priceRange;
    }
}

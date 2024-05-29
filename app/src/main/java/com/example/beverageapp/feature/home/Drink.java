package com.example.beverageapp.feature.home;


public class Drink {

    private int id;
    private int drinkType;
    private String image;
    private float price;
    private String name;
    private String desc;
    private int rateCounter;


    public Drink(int id, String image, float price, String name, String desc, int rateCounter, int drinkType) {
        this.id = id;
        this.drinkType = drinkType;
        this.image = image;
        this.price = price;
        this.name = name;
        this.desc = desc;
        this.rateCounter = rateCounter;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrinkType() {
        return drinkType;
    }

    public void setDrinkType(int drinkType) {
        this.drinkType = drinkType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRateCounter() {
        return rateCounter;
    }

    public void setRateCounter(int rateCounter) {
        this.rateCounter = rateCounter;
    }

}

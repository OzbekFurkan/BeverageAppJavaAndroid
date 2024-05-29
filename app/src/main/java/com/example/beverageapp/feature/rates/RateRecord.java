package com.example.beverageapp.feature.rates;

public class RateRecord {
    private int rate_id;
    private int rate_value;
    private int drink_id;

    public RateRecord(int rate_id, int rate_value, int drink_id) {
        this.rate_id = rate_id;
        this.rate_value = rate_value;
        this.drink_id = drink_id;
    }

    public int getRate_id() {
        return rate_id;
    }

    public void setRate_id(int rate_id) {
        this.rate_id = rate_id;
    }

    public int getRate_value() {
        return rate_value;
    }

    public void setRate_value(int rate_value) {
        this.rate_value = rate_value;
    }

    public int getDrink_id() {
        return drink_id;
    }

    public void setDrink_id(int drink_id) {
        this.drink_id = drink_id;
    }
}

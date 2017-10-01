package com.jazasoft.rollplan.model;

/**
 * Created by mdzahidraza on 30/09/17.
 */
public class Roll {
    private int id;
    private int length;
    private int cost;

    public Roll() {
    }

    public Roll(int id, int length) {
        this.id = id;
        this.length = length;
        this.cost = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getLabel() {
        return "Roll " + id;
    }
}

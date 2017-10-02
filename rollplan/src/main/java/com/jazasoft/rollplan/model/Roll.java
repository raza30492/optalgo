package com.jazasoft.rollplan.model;

/**
 * Created by mdzahidraza on 30/09/17.
 */
public class Roll {
    private int id;
    private double length;
    private int cost;

    public Roll() {
    }

    public Roll(int id, double length) {
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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
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

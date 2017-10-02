package com.jazasoft.rollplan.model;

/**
 * Created by mdzahidraza on 30/09/17.
 */
public class Marker {
    private int id;
    private double length;
    private int noOfPly;

    public Marker() {
    }

    public Marker(int id, double length, int noOfPly) {
        this.id = id;
        this.length = length;
        this.noOfPly = noOfPly;
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

    public int getNoOfPly() {
        return noOfPly;
    }

    public void setNoOfPly(int noOfPly) {
        this.noOfPly = noOfPly;
    }

    public String getLabel() {
        return "Marker " + id;
    }
}

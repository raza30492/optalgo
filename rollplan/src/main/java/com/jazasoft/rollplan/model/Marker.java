package com.jazasoft.rollplan.model;

/**
 * Created by mdzahidraza on 30/09/17.
 */
public class Marker {
    private int id;
    private int length;
    private int noOfPly;

    public Marker() {
    }

    public Marker(int id, int length, int noOfPly) {
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
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

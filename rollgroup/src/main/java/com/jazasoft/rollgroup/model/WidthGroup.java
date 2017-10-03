package com.jazasoft.rollgroup.model;

import java.util.Comparator;

/**
 * Created by mdzahidraza on 03/10/17.
 */
public class WidthGroup implements Comparable<WidthGroup>{
    private double axis;
    private double deviation;
    private double fabricLength;
    private double waste;
    private double possibleWaste;


    public WidthGroup() {
    }

    public WidthGroup(double axis, double deviation) {
        this.axis = axis;
        this.deviation = deviation;
    }

    public double getAxis() {
        return axis;
    }

    public void setAxis(double axis) {
        this.axis = axis;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    public double getFabricLength() {
        return fabricLength;
    }

    public void setFabricLength(double fabricLength) {
        this.fabricLength = fabricLength;
    }

    public double getWaste() {
        return waste;
    }

    public void setWaste(double waste) {
        this.waste = waste;
    }

    public double getPossibleWaste() {
        return possibleWaste;
    }

    public void setPossibleWaste(double possibleWaste) {
        this.possibleWaste = possibleWaste;
    }

    public double upperLimit() {
        return axis+deviation;
    }
    public String getLabel() {
        return "Width Group ["+ axis + " - " + (axis+deviation) + "]";
    }

    @Override
    public int compareTo(WidthGroup o) {
        if (o == null) return -1;
        if (this.getAxis() - o.getAxis() == 0) return 0;
        if (this.getAxis() > o.getAxis()) return 1;
        else return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WidthGroup group = (WidthGroup) o;

        if (Double.compare(group.axis, axis) != 0) return false;
        return Double.compare(group.deviation, deviation) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(axis);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(deviation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

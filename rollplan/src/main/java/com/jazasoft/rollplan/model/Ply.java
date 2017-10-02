package com.jazasoft.rollplan.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * Created by mdzahidraza on 30/09/17.
 */
@PlanningEntity
public class Ply {
    private int id;
    private int markerId;
    private double length;

    private Roll roll;

    public Ply() {
    }

    public Ply(int id, int markerId, double length) {
        this.id = id;
        this.markerId = markerId;
        this.length = length;
    }


    @PlanningVariable(valueRangeProviderRefs = "rollRange")
    public Roll getRoll() {
        return roll;
    }

    public void setRoll(Roll roll) {
        this.roll = roll;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMarkerId() {
        return markerId;
    }

    public void setMarkerId(int markerId) {
        this.markerId = markerId;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Ply ply = (Ply) o;
//
//        return markerId == ply.markerId;
//    }
//
//    @Override
//    public int hashCode() {
//        return markerId;
//    }
}

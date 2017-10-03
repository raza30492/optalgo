package com.jazasoft.rollgroup.model;

import com.opencsv.bean.CsvBindByName;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * Created by mdzahidraza on 02/10/17.
 */
@PlanningEntity
public class Roll {

    @CsvBindByName(column = "ROLL_NO")
    private Double rollNo;

    @CsvBindByName(column = "LENGTH")
    private Double length;

    @CsvBindByName(column = "WIDTH")
    private Double width;

    @CsvBindByName(column = "WARP")
    private Double warp;

    @CsvBindByName(column = "WEFT")
    private Double weft;

    private WidthGroup widthGroup;  //Planning variable

    public Roll() {
    }

    public Double getRollNo() {
        return rollNo;
    }

    public void setRollNo(Double rollNo) {
        this.rollNo = rollNo;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getWarp() {
        return warp;
    }

    public void setWarp(Double warp) {
        this.warp = warp;
    }

    public Double getWeft() {
        return weft;
    }

    public void setWeft(Double weft) {
        this.weft = weft;
    }

    @PlanningVariable(valueRangeProviderRefs = {"widthRange"})
    public WidthGroup getWidthGroup() {
        return widthGroup;
    }

    public void setWidthGroup(WidthGroup widthGroup) {
        this.widthGroup = widthGroup;
    }

    @Override
    public String toString() {
        return "Roll{" +
                "rollNo=" + rollNo +
                ", length=" + length +
                ", width=" + width +
                ", warp=" + warp +
                ", weft=" + weft +
                '}';
    }
}

package com.jazasoft.rollgroup.model;

import com.opencsv.bean.CsvBindByName;

/**
 * Created by mdzahidraza on 02/10/17.
 */
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

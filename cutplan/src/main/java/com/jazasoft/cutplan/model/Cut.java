package com.jazasoft.cutplan.model;

import java.util.List;

/**
 * Created by mdzahidraza on 02/10/17.
 */
public class Cut {
    private int id;
    private int noOfPly;
    private List<Size> sizeList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Size> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<Size> sizeList) {
        this.sizeList = sizeList;
    }

    public String getLabel() {
        return "Cut " + id;
    }
}

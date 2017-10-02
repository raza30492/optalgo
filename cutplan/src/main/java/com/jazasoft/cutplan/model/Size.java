package com.jazasoft.cutplan.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * Created by mdzahidraza on 02/10/17.
 */
@PlanningEntity
public class Size {
    private int size;
    private int cutId;
    private int qty;

    private Ratio ratio;   //planning variable

    public Size() {
    }

    public Size(int size) {
        this.size = size;
    }

    public Size(int size, int cutId) {
        this.size = size;
        this.cutId = cutId;
    }

    public int getCutId() {
        return cutId;
    }

    public void setCutId(int cutId) {
        this.cutId = cutId;
    }


    @PlanningVariable(valueRangeProviderRefs = "ratioRange")
    public Ratio getRatio() {
        return ratio;
    }

    public void setRatio(Ratio ratio) {
        this.ratio = ratio;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getLabel() {
        return "Size " + size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Size size1 = (Size) o;

        return size == size1.size;
    }

    @Override
    public int hashCode() {
        return size;
    }

    @Override
    public String toString() {
        return "Size = " + size + ", ratio = " + (ratio!=null ? ratio.getRatio() : 0);
    }
}

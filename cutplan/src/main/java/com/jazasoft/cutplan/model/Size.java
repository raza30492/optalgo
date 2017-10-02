package com.jazasoft.cutplan.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * Created by mdzahidraza on 02/10/17.
 */
@PlanningEntity
public class Size {
    private int id;

    private Ratio ratio;   //planning variable

    public Size() {
    }

    public Size(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PlanningVariable(valueRangeProviderRefs = "ratioRange")
    public Ratio getRatio() {
        return ratio;
    }

    public void setRatio(Ratio ratio) {
        this.ratio = ratio;
    }

    public String getLabel() {
        return "Size " + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Size size = (Size) o;

        return id == size.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Size " + id;
    }
}

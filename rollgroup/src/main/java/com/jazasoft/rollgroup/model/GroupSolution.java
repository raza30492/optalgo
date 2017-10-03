package com.jazasoft.rollgroup.model;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

/**
 * Created by mdzahidraza on 03/10/17.
 */
@PlanningSolution
public class GroupSolution {
    private double maxWastePercent;
    private List<Roll> rollList;
    private List<WidthGroup> widthGroupList;

    private HardSoftScore score;

    @PlanningEntityCollectionProperty
    public List<Roll> getRollList() {
        return rollList;
    }

    public void setRollList(List<Roll> rollList) {
        this.rollList = rollList;
    }

    @ValueRangeProvider(id = "widthRange")
    @ProblemFactCollectionProperty
    public List<WidthGroup> getWidthGroupList() {
        return widthGroupList;
    }

    public void setWidthGroupList(List<WidthGroup> widthGroupList) {
        this.widthGroupList = widthGroupList;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public double getMaxWastePercent() {
        return maxWastePercent;
    }

    public void setMaxWastePercent(double maxWastePercent) {
        this.maxWastePercent = maxWastePercent;
    }
}

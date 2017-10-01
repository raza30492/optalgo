package com.jazasoft.rollplan.model;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdzahidraza on 30/09/17.
 */
@PlanningSolution
public class RollSolution {

    private List<Roll> rollList;
    private List<Marker> markerList;
    private List<Ply> plyList;

    private HardSoftScore score;

    public void init(){
        rollList.forEach(roll -> roll.setCost(1));
        plyList = new ArrayList<Ply>();
        int i = 0;
        for (Marker marker: markerList) {
            for (int j = 0; j < marker.getNoOfPly(); j++) {
                plyList.add(new Ply(i,marker.getId(),marker.getLength()));
                i++;
            }
        }
    }

    @ValueRangeProvider(id = "rollRange")
    @ProblemFactCollectionProperty
    public List<Roll> getRollList() {
        return rollList;
    }

    public void setRollList(List<Roll> rollList) {
        this.rollList = rollList;
    }

    public List<Marker> getMarkerList() {
        return markerList;
    }

    public void setMarkerList(List<Marker> markerList) {
        this.markerList = markerList;
    }

    @PlanningEntityCollectionProperty
    public List<Ply> getPlyList() {
        return plyList;
    }

    public void setPlyList(List<Ply> plyList) {
        this.plyList = plyList;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }
}

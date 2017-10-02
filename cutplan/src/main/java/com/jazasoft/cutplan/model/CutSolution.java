package com.jazasoft.cutplan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mdzahidraza on 02/10/17.
 */
@PlanningSolution
public class CutSolution {
    private int noOfCut;
    private int maxGarments;
    private List<Size> orderList;
    @JsonIgnore
    private Map<Integer, Integer> order;

    private List<Ratio> ratioList;
    private List<Size> sizeList;

    private HardSoftScore score;

    public void init() {
        ratioList = new ArrayList<>();
        sizeList = new ArrayList<>();
        order = new HashMap<>();
        for (Size size: orderList) {
            order.put(size.getSize(), size.getQty());
            sizeList.add(new Size(size.getSize()));
        }
        for (int i = 0; i <= maxGarments; i++) {
            ratioList.add(new Ratio(i));
        }
        orderList = null;
    }

    public int getMaxGarments() {
        return maxGarments;
    }

    public void setMaxGarments(int maxGarments) {
        this.maxGarments = maxGarments;
    }

    public Map<Integer, Integer> getOrder() {
        return order;
    }

    public void setOrder(Map<Integer, Integer> order) {
        this.order = order;
    }

    @ValueRangeProvider(id = "ratioRange")
    @ProblemFactCollectionProperty
    public List<Ratio> getRatioList() {
        return ratioList;
    }

    public void setRatioList(List<Ratio> ratioList) {
        this.ratioList = ratioList;
    }

    @PlanningEntityCollectionProperty
    public List<Size> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<Size> sizeList) {
        this.sizeList = sizeList;
    }

    @PlanningScore
    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public int getNoOfCut() {
        return noOfCut;
    }

    public void setNoOfCut(int noOfCut) {
        this.noOfCut = noOfCut;
    }

    public List<Size> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Size> orderList) {
        this.orderList = orderList;
    }

    public int getOrderQty() {
        return order.values().stream().mapToInt(Integer::intValue).sum();
    }
}

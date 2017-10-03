package com.jazasoft.rollgroup;

import com.jazasoft.rollgroup.model.GroupSolution;
import com.jazasoft.rollgroup.model.Roll;
import com.jazasoft.rollgroup.model.WidthGroup;
import com.jazasoft.util.ExcelUtil;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mdzahidraza on 02/10/17.
 */
public class Main {

    private static double maxWastePercent = 0.5;

    private static WidthGroup prev, prev2, least, next;
    private static double totalFabric = 0;

    public static void main(String[] args) {

        String dataFile = "test-data1.xlsx";

        Collection<Roll> data = readFile(dataFile);

        SolverFactory<GroupSolution> solverFactory = SolverFactory.createFromXmlResource("com/jazasoft/rollgroup/rollGroupSolverConfig.xml");

        Solver<GroupSolution> solver = solverFactory.buildSolver();

        GroupSolution unsolved = createProblem(data);

        GroupSolution solved = solver.solve(unsolved);

        display(solved);
    }

    private static void display(GroupSolution solution) {
        System.out.println("display...");
        List<Roll> rollList = solution.getRollList();
        List<Roll> filteredRollList = null;


        for (Roll roll: solution.getRollList()) {
            totalFabric += (roll.getLength()*roll.getWidth()/100);
        }

        for (WidthGroup group: solution.getWidthGroupList()) {
            filteredRollList = rollList.stream().filter(roll -> roll.getWidthGroup().equals(group)).collect(Collectors.toList());
            double waste = filteredRollList.stream().mapToDouble(roll -> roll.getLength()*(roll.getWidth()-group.getAxis())/100).sum();
            double fabricLength = filteredRollList.stream().mapToDouble(Roll::getLength).sum();
            group.setFabricLength(fabricLength);
            group.setWaste(waste);
        }
        TreeSet<WidthGroup> sortedSet = new TreeSet<>(solution.getWidthGroupList());

        WidthGroup prev1 = null;
        for (WidthGroup group: sortedSet) {
            //System.out.println("axis="+group.getAxis());
            if (prev1 != null) {
                group.setPossibleWaste(group.getFabricLength()*prev1.getDeviation()/100 + group.getWaste());
            }
            prev1 = group;
        }

        double maxWaste = solution.getMaxWastePercent()*totalFabric/100;
        double totalWaste = 0;
        while (true) {
            //disp(sortedSet);
            totalWaste = sortedSet.stream().mapToDouble(WidthGroup::getWaste).sum();
            findLeastPossibleWasteGroup(sortedSet);

            if (least != null){
                if ((totalWaste + least.getPossibleWaste()) < maxWaste ) {
                    WidthGroup merged = merge();
                    sortedSet.remove(prev);
                    sortedSet.remove(least);
                    sortedSet.add(merged);
                }else {
                    break;
                }
            }
            //System.out.println("totalWaste = " + totalWaste + ", maxWaste = " + maxWaste);
        }

        totalWaste = sortedSet.stream().mapToDouble(WidthGroup::getWaste).sum();
        System.out.println("\nNo. of Groups = " + sortedSet.size() + ", total Waste = " + totalWaste+ ", % waste = " + (totalWaste*100/totalFabric));
        for (WidthGroup group: sortedSet) {
            System.out.println(group.getLabel() + " : fabric length = " + group.getFabricLength() + ", waste = " + group.getWaste() + ", possible waste = " + group.getPossibleWaste());
        }

        System.out.println("Total Fabric = " + totalFabric);
    }

    private static WidthGroup merge() {
        WidthGroup group = new WidthGroup();
        group.setAxis(prev.getAxis());
        group.setDeviation(prev.getDeviation()+least.getDeviation());
        group.setFabricLength(prev.getFabricLength()+least.getFabricLength());
        group.setWaste(prev.getWaste()+least.getPossibleWaste());
        if (prev2 != null) {
            group.setPossibleWaste(group.getWaste() + group.getFabricLength()*prev2.getDeviation()/100);
        }
        if (next != null){
            next.setPossibleWaste(next.getWaste() + next.getFabricLength()*group.getDeviation());
        }
        return group;
    }

    private static GroupSolution createProblem(Collection<Roll> rolls) {
        System.out.println("create Problem...");
        GroupSolution solution = new GroupSolution();
        List<Roll> rollList = rolls.stream().sorted(Comparator.comparingDouble(Roll::getWidth)).collect(Collectors.toList());
        double start = rollList.get(0).getWidth();
        double end  = rollList.get(rollList.size()-1).getWidth();

        List<WidthGroup> groupList = new ArrayList<>();
        for (int i = (int)start; i < (int)(end+1); i++) {
            groupList.add(new WidthGroup(i,0.5));
            groupList.add(new WidthGroup(i+0.5, 0.5));
        }
        solution.setWidthGroupList(groupList);
        solution.setRollList(rollList);
        solution.setMaxWastePercent(maxWastePercent);
        return solution;
    }

    private static void findLeastPossibleWasteGroup(TreeSet<WidthGroup> list) {
        //System.out.println("\n");
        least = null;
        prev = null;
        prev2 = null;
        next = null;
        double leastPossibelWaste = Double.MAX_VALUE;
        int i = 0;
        WidthGroup p1 = null, p2 = null;
        boolean setNext = false;
        for (WidthGroup group: list) {
            //System.out.println("axis="+group.getAxis());
            if (setNext){
                next = group;
            }
            if (i != 0) {
                if (group.getPossibleWaste() < leastPossibelWaste) {
                    leastPossibelWaste = group.getPossibleWaste();
                    least = group;
                    prev = p1;
                    prev2 = p2;
                    next = null;
                    setNext = true;
                }else {
                    setNext = false;
                }
            }
            i++;
            p2 = p1;
            p1 = group;

        }
    }

    private static Collection<Roll> readFile(String filename) {
        filename = System.getenv("RP_HOME") + File.separator + "rollgroup" + File.separator + filename;
        return ExcelUtil.readExcelXlsx(new File(filename),0,Roll.class);
    }

    private static void disp(TreeSet<WidthGroup> list){
        double totalWaste = list.stream().mapToDouble(WidthGroup::getWaste).sum();

        double used = 0;
        for (WidthGroup group: list) {
            used += group.getAxis()*group.getFabricLength()/100;
            System.out.println(group.getLabel() + " : fabric length = " + group.getFabricLength() + ", waste = " + group.getWaste() + ", possible waste = " + group.getPossibleWaste());
        }
        System.out.println("No. of Groups = " + list.size() + ", total Waste = " + totalWaste+ ", % waste = " + (totalWaste*100/totalFabric));
        System.out.println("total="+totalFabric+", used="+used+", wasted="+(totalFabric-used));
        System.out.println();
    }
}

package com.jazasoft.cutplan;

import com.jazasoft.cutplan.model.CutSolution;
import com.jazasoft.cutplan.model.Size;
import com.jazasoft.util.JsonUtils;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * common:
 *      hard: less than max garment
 *      soft: maximize no of garments cut
 * Case 1.
 *      soft: fairness constraint
 * case 2:
 *      hard: only even no of ways
 * case 3:
 *      hard: ratio not more than 2 for any size
 * case 4:
 *      hard: only even no of ways
 *      hard: ratio not more than 2 for any size
 *
 * Created by mdzahidraza on 02/10/17.
 */
public class Main {

    private static int totalOrderQty = 0;
    private static List<CutSolution> solutions = new ArrayList<>();
    private static Map<String, List<CutSolution>> solMap = new HashMap<>();

    public static void main(String[] args) {
        String dataFile = "test-data1.json";
        if (args.length != 0) {
            dataFile = args[0];
        }

        SolverFactory<CutSolution> solverFactory = SolverFactory.createFromXmlResource("com/jazasoft/cutplan/cutPlanSolverConfig.xml");

        Solver<CutSolution> solver = solverFactory.buildSolver();

        CutSolution unsolved = createProblem(dataFile);
        totalOrderQty = unsolved.getOrderQty();

        while (unsolved.getOrderQty() != 0) {
            CutSolution solved = solver.solve(unsolved);
            solutions.add(solved);
            unsolved = reinit(solved);
        }
        solMap.put("Case : Optimal",solutions);

        unsolved = createProblem(dataFile);
        unsolved.setOnlyEvenNoOfWays(true);
        solutions = new ArrayList<>();

        while (unsolved.getOrderQty() != 0) {
            CutSolution solved = solver.solve(unsolved);
            solutions.add(solved);
            unsolved = reinit(solved);
        }
        solMap.put("Case : Only Even no of ways(Marker)",solutions);

        unsolved = createProblem(dataFile);
        unsolved.setOnlyRatio2ForAnySize(true);
        solutions = new ArrayList<>();

        while (unsolved.getOrderQty() != 0) {
            CutSolution solved = solver.solve(unsolved);
            solutions.add(solved);
            unsolved = reinit(solved);
        }
        solMap.put("Case : Ratio not more than 2 for any Size",solutions);

        unsolved = createProblem(dataFile);
        unsolved.setOnlyEvenNoOfWays(true);
        unsolved.setOnlyRatio2ForAnySize(true);
        solutions = new ArrayList<>();

        while (unsolved.getOrderQty() != 0) {
            CutSolution solved = solver.solve(unsolved);
            solutions.add(solved);
            unsolved = reinit(solved);
        }
        solMap.put("Case : Only Even Marker and ratio not more than 2",solutions);
        display();

    }

    private static CutSolution createProblem(String filename) {

        String basePath = System.getenv("RP_HOME") + File.separator + "cutplan" + File.separator;
        CutSolution cutSolution = null;
        try {
            cutSolution = JsonUtils.readJsonObject(new File(basePath + filename), CutSolution.class);
            //System.out.println(JsonUtils.toFormattedString(cutSolution));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Invalid data in JSON. check if data is correct.");
        }

        cutSolution.init();


        return cutSolution;
    }

    private static void display() {
        for (Map.Entry<String, List<CutSolution>> entry: solMap.entrySet()) {
            System.out.println("\n"+entry.getKey());
            List<CutSolution> solutions = entry.getValue();
            int i = 1;
            System.out.print("Cut\t   ");
            for (Size size: solutions.get(0).getSizeList()){
                System.out.print("Size"+size.getSize() + "\t");
            }
            System.out.print("total\tWay \t%cont.\tScore\n");

            for (CutSolution solution: solutions) {
                System.out.print("Cut " + i++ + " \t");
                int totalGarments = 0;
                int noOfPly = Utils.getNoOfPly(solution.getOrder(),solution.getSizeList());
                int totalRatio = 0;
                for (Size size: solution.getSizeList()) {
                    totalRatio += size.getRatio().getRatio();
                    int garments = size.getRatio().getRatio()*noOfPly;
                    totalGarments += garments;
                    System.out.printf("%4d \t", garments);
                }
                System.out.printf(" %4d \t%d way\t %2.2f\t%s\n",totalGarments, totalRatio, (double)(totalGarments*100)/totalOrderQty, solution.getScore());
            }
        }
    }

    private static CutSolution reinit(CutSolution solution) {
        CutSolution solution1 = new CutSolution();
        solution1.setMaxGarments(solution.getMaxGarments());
        solution1.setOnlyRatio2ForAnySize(solution.isOnlyRatio2ForAnySize());
        solution1.setOnlyEvenNoOfWays(solution.isOnlyEvenNoOfWays());

        solution1.setOrder(Utils.getRemainingOrder(solution.getOrder(),solution.getSizeList()));
        List<Size> sizeList = solution.getSizeList().stream().map(size -> new Size(size.getSize())).collect(Collectors.toList());
        solution1.setSizeList(sizeList);
        solution1.setRatioList(solution.getRatioList());
        return solution1;
    }
}

//           System.out.println("Reamining: " + unsolved.getOrderQty());
//            for (Map.Entry<Integer,Integer> entry: unsolved.getOrder().entrySet()){
//                System.out.println("Size " + entry.getKey() + " : " + entry.getValue());
//            }
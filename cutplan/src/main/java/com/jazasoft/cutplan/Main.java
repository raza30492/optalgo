package com.jazasoft.cutplan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jazasoft.cutplan.model.CutSolution;
import com.jazasoft.cutplan.model.Ratio;
import com.jazasoft.cutplan.model.Size;
import com.jazasoft.util.JsonUtils;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mdzahidraza on 02/10/17.
 */
public class Main {

    private static int totalOrderQty = 0;
    private static List<CutSolution> solutions = new ArrayList<>();

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
            System.out.println("Reamining: " + unsolved.getOrderQty());
//            for (Map.Entry<Integer,Integer> entry: unsolved.getOrder().entrySet()){
//                System.out.println("Size " + entry.getKey() + " : " + entry.getValue());
//            }
        }
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
        System.out.println("\n");
        int i = 1;
        System.out.print("Cut\t   ");
        for (Size size: solutions.get(0).getSizeList()){
            System.out.print("Size"+size.getSize() + "\t");
        }
        System.out.print("total\t%cont.\n");

        for (CutSolution solution: solutions) {
            System.out.print("Cut " + i++ + " \t");
            int totalGarments = 0;
            int noOfPly = Utils.getNoOfPly(solution.getOrder(),solution.getSizeList());
            for (Size size: solution.getSizeList()) {
                int garments = size.getRatio().getRatio()*noOfPly;
                totalGarments += garments;
                System.out.printf("%4d \t", garments);
            }
            System.out.printf(" %4d \t%2.2f \n",totalGarments, (double)(totalGarments*100)/totalOrderQty);
        }

    }

    private static CutSolution reinit(CutSolution solution) {
        CutSolution solution1 = new CutSolution();
        solution1.setMaxGarments(solution.getMaxGarments());
        solution1.setOrder(Utils.getRemainingOrder(solution.getOrder(),solution.getSizeList()));
        List<Size> sizeList = solution.getSizeList().stream().map(size -> new Size(size.getSize())).collect(Collectors.toList());
        solution1.setSizeList(sizeList);
        solution1.setRatioList(solution.getRatioList());
        return solution1;
    }
}

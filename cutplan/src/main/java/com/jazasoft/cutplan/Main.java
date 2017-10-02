package com.jazasoft.cutplan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jazasoft.cutplan.model.CutSolution;
import com.jazasoft.cutplan.model.Ratio;
import com.jazasoft.cutplan.model.Size;
import com.jazasoft.util.JsonUtils;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mdzahidraza on 02/10/17.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Starting...");

        String dataFile = "test-data1.json";
        if (args.length != 0) {
            dataFile = args[0];
        }

        SolverFactory<CutSolution> solverFactory = SolverFactory.createFromXmlResource("com/jazasoft/cutplan/cutPlanSolverConfig.xml");

        Solver<CutSolution> solver = solverFactory.buildSolver();

        CutSolution unsolved = createProblem(dataFile);

        CutSolution solved = solver.solve(unsolved);

        display(solved);
        System.out.println("Ended...");
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
//        try {
//            System.out.println(JsonUtils.toFormattedString(cutSolution));
//            for (Map.Entry<Size,Integer> entry: cutSolution.getOrder().entrySet()) {
//                System.out.println(entry.getKey() + " :  " + entry.getValue());
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        return cutSolution;
    }

    private static void display(CutSolution solution) {
        int totalGarments = 0;
        for (Size size: solution.getSizeList()) {
            int garments = size.getRatio().getRatio()*solution.getNoOfPly();
            totalGarments += garments;
            System.out.println(size.getLabel() + ": " + garments);
        }
        System.out.println("Total Garments: " + totalGarments);
    }
}

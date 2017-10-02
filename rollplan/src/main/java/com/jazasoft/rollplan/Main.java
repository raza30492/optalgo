package com.jazasoft.rollplan;

import com.jazasoft.rollplan.model.Marker;
import com.jazasoft.rollplan.model.Ply;
import com.jazasoft.rollplan.model.Roll;
import com.jazasoft.rollplan.model.RollSolution;
import com.jazasoft.util.JsonUtils;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mdzahidraza on 30/09/17.
 */
public class Main {
    public static void main(String[] args) {

        String dataFile = "test-data2.json";
        if (args.length > 0) {
            dataFile = args[0];
        }

        SolverFactory<RollSolution> solverFactory = SolverFactory.createFromXmlResource("com/jazasoft/rollplan/rollPlanSolverConfig.xml");

        Solver<RollSolution> solver = solverFactory.buildSolver();

        RollSolution unsolved = createProblem(dataFile);

        RollSolution solved = solver.solve(unsolved);

        display(solved);

    }

    private static RollSolution createProblem(String filename) {
        String basePath = System.getenv("RP_HOME") + File.separator + "rollplan" + File.separator;
        RollSolution rollSolution = null;
        try {
            rollSolution = JsonUtils.readJsonObject(new File(basePath + filename), RollSolution.class);
            System.out.println(JsonUtils.toFormattedString(rollSolution));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Invalid data in JSON. check if data is correct.");
        }
        rollSolution.init();
        return rollSolution;
    }

    private static void display(RollSolution solution) {
        //Find Smallest marker
        Marker smallestMarker = solution.getMarkerList().stream()
                .sorted(Comparator.comparingDouble(Marker::getLength))
                .findFirst().orElse(null);
        double unused = 0;
        double waste = 0;
        for (final Roll roll: solution.getRollList()) {
            //Plies allocated to this roll
            List<Ply> plies = solution.getPlyList().stream()
                    .filter(ply -> {
                        if (ply.getRoll() != null) {
                            return roll.getId() == ply.getRoll().getId();
                        }else {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
            //Sum total length of plies
            double sum = plies.stream().mapToDouble(Ply::getLength).sum();
            System.out.println(roll.getLabel() + ": left over = " + (roll.getLength()-sum));
            if (roll.getLength() > sum) {
                if (roll.getLength() - sum > smallestMarker.getLength()) {
                    unused += (roll.getLength() - sum);
                }else {
                    waste += (roll.getLength() - sum);
                }
            }

            for (Marker marker: solution.getMarkerList()) {
                long noOfPly = plies.stream().filter(ply -> ply.getMarkerId() == marker.getId()).count();
                if (noOfPly > 0) {
                    System.out.println(roll.getLabel() + " -> " + marker.getLabel() + ": " + noOfPly + " plies");
                }
            }
        }
        double totalFabric = solution.getRollList().stream().mapToDouble(Roll::getLength).sum();
        double usedFabric = solution.getPlyList().stream().mapToDouble(Ply::getLength).sum();
        System.out.println("Total Fabric = " + totalFabric);
        System.out.println("Used Fabric = " + usedFabric);
        System.out.println("Unused Fabric = " + unused);
        System.out.println("wasted Fabric = " + waste);

        System.out.println(solution.getScore().toString());
    }
}

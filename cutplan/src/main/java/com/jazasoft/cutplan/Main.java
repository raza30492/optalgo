package com.jazasoft.cutplan;

import com.jazasoft.cutplan.model.CutSolution;
import com.jazasoft.cutplan.model.Ratio;
import com.jazasoft.cutplan.model.Size;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

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

        SolverFactory<CutSolution> solverFactory = SolverFactory.createFromXmlResource("com/jazasoft/cutplan/cutPlanSolverConfig.xml");

        Solver<CutSolution> solver = solverFactory.buildSolver();

        CutSolution unsolved = createProblem();

        CutSolution solved = solver.solve(unsolved);

        display(solved);
        System.out.println("Ended...");
    }

    private static CutSolution createProblem() {
        CutSolution cutSolution = new CutSolution();
        Size size1 = new Size(1);
        Size size2 = new Size(2);
        Size size3 = new Size(3);
        Size size4 = new Size(4);
        Size size5 = new Size(5);

        List<Size> sizeList = Arrays.asList(size1, size2, size3, size4, size5);
        Map<Size, Integer> order = new HashMap<>();
        order.put(size1,1286);
        order.put(size2,2324);
        order.put(size3,2107);
        order.put(size4,1287);
        order.put(size5,607);

        List<Ratio> ratioList = Arrays.asList(new Ratio(0),new Ratio(1),new Ratio(2),new Ratio(3),new Ratio(4),new Ratio(5),new Ratio(6));

        cutSolution.setMaxGarments(6);
        cutSolution.setOrder(order);
        cutSolution.setSizeList(sizeList);
        cutSolution.setRatioList(ratioList);

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

package com.jazasoft.cloudbalance;

import com.jazasoft.cloudbalance.model.CloudBalance;
import com.jazasoft.cloudbalance.model.CloudProcess;
import com.jazasoft.cloudbalance.model.Computer;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdzahidraza on 30/09/17.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Starting...");

        SolverFactory<CloudBalance> solverFactory = SolverFactory.createFromXmlResource("com/jazasoft/cloudbalance/cloudBalanceSolverConfig.xml");

        Solver<CloudBalance> solver = solverFactory.buildSolver();

        CloudBalance unsolved = createProblem();

        CloudBalance solved = solver.solve(unsolved);

        display(solved);
        System.out.println("Ended...");

    }

    private static CloudBalance createProblem() {
        CloudBalance cloudBalance = new CloudBalance();
        List<Computer> computerList = new ArrayList<Computer>();
        computerList.add(new Computer(1,7,6,6,1500));
        computerList.add(new Computer(2,6,6,10,1000));
        List<CloudProcess> processList = new ArrayList<CloudProcess>();
        processList.add(new CloudProcess(1,5,5,2));
        processList.add(new CloudProcess(2,4,3,2));
        processList.add(new CloudProcess(3,2,3,3));
        processList.add(new CloudProcess(4,2,1,2));
        cloudBalance.setComputerList(computerList);
        cloudBalance.setProcessList(processList);
        return cloudBalance;
    }

    private static void display(CloudBalance cloudBalance) {
        for (CloudProcess process: cloudBalance.getProcessList()) {
            System.out.println(process.getLabel() + " -> " + (process.getComputer() == null ? "Not Assigned" : process.getComputer().getLabel()));
        }

        System.out.println("Score = " + cloudBalance.getScore().toShortString());
    }
}

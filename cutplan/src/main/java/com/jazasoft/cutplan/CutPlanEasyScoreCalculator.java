package com.jazasoft.cutplan;

import com.jazasoft.cutplan.model.CutSolution;
import com.jazasoft.cutplan.model.Size;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import java.util.Map;

/**
 * Created by mdzahidraza on 02/10/17.
 */
public class CutPlanEasyScoreCalculator implements EasyScoreCalculator<CutSolution> {
    //HardConstraint
    //1. sum ratio cannot be greater than maxGarments
    //2. no of qty in a size cannot be greater than total oreder qty in that size
    //3.
    //Soft Constraint
    //1. Maximize total no of garments in a cut
    //2. maximize totalRatio
    @Override
    public Score calculateScore(CutSolution cutSolution) {
        //System.out.println("Calculate Score...");
        int hardScore = 0;
        int softScore = 0;

        //Calculate no of ply
        int noOfPly = Utils.getNoOfPly(cutSolution.getOrder(),cutSolution.getSizeList());
        int totalRatio = 0;
        int fairnessScore = 0;
        for (Size size: cutSolution.getSizeList()) {
            if (size.getRatio() != null) {
                int ratio = size.getRatio().getRatio();
                if (cutSolution.isOnlyRatio2ForAnySize()) {
                    if (ratio > 2) {
                        hardScore += (2 - ratio);
                    } else {
                        fairnessScore += ratio * ratio * ratio * ratio;
                    }
                } else {
                    fairnessScore += ratio*ratio*ratio*ratio;
                }
                totalRatio += ratio;
            }
        }

        if (totalRatio > cutSolution.getMaxGarments()) {
            hardScore += (cutSolution.getMaxGarments() - totalRatio);
        } else {
            if (cutSolution.isOnlyEvenNoOfWays()) {
                if (totalRatio > 1 && totalRatio % 2 == 1) {
                    hardScore += -totalRatio;
                } else {
                    softScore += (noOfPly * totalRatio);
                }
            } else {
                softScore += (noOfPly*totalRatio);
            }
        }

        if (cutSolution.getOrderQty() > 500) {
            softScore -= fairnessScore;
        }
//        System.out.println("No of Ply = " + noOfPly);
//        System.out.println("totalRatio = " + totalRatio);
//        System.out.println("Soft Score = " + softScore);

        return HardSoftScore.valueOf(hardScore,softScore);
    }
}

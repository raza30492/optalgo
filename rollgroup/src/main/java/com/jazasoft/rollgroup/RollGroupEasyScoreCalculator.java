package com.jazasoft.rollgroup;

import com.jazasoft.rollgroup.model.GroupSolution;
import com.jazasoft.rollgroup.model.Roll;
import com.jazasoft.rollgroup.model.WidthGroup;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

/**
 * Created by mdzahidraza on 03/10/17.
 */
public class RollGroupEasyScoreCalculator implements EasyScoreCalculator<GroupSolution> {
    @Override
    public Score calculateScore(GroupSolution groupSolution) {
        int hardScore = 0;
        int softScore = 0;

        WidthGroup group = null;
        for (Roll roll: groupSolution.getRollList()) {
            group = roll.getWidthGroup();
            if (group != null && (roll.getWidth() < group.getAxis() || roll.getWidth() >= group.upperLimit())) {
                hardScore += -1;
            }
        }

        return HardSoftScore.valueOf(hardScore,softScore);
    }
}

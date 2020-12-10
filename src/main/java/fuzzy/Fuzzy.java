package fuzzy;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

public class Fuzzy {
    FIS fis;

    public Fuzzy() {
        this.fis = FIS.load("src/main/resources/fuzzy_cleaner.fcl", false);
    }

    public int evaluate(int frontContaminationLevel, int frontObstacleDistance, int frontLeftContaminationLevel, int frontRightContaminationLevel) {
        FuzzyRuleSet fuzzyRuleSet = fis.getFuzzyRuleSet();
        fuzzyRuleSet.setVariable("front_contamination_level", frontContaminationLevel);
        fuzzyRuleSet.setVariable("front_obstacle_distance", frontObstacleDistance);
        fuzzyRuleSet.setVariable("front_left_contamination_level", frontLeftContaminationLevel);
        fuzzyRuleSet.setVariable("front_right_contamination_level", frontRightContaminationLevel);
        fuzzyRuleSet.evaluate();
        return (int) fuzzyRuleSet.getVariable("angle_change").getLatestDefuzzifiedValue();
    }
}

package fuzzy;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

public class FuzzyExample {
    public static void main(String[] args) throws Exception {
        try {
            String fileName = args[0];
            int frontContaminationLevel = Integer.parseInt(args[1]);
            int frontObstacleDistance = Integer.parseInt(args[2]);
            int frontLeftContaminationLevel = Integer.parseInt(args[3]);
            int frontRightContaminationLevel = Integer.parseInt(args[4]);
            FIS fis = FIS.load(fileName, false);
            FuzzyRuleSet fuzzyRuleSet = fis.getFuzzyRuleSet();
            fuzzyRuleSet.chart();
            fuzzyRuleSet.setVariable("front_obstacle_distance", frontObstacleDistance);
            fuzzyRuleSet.setVariable("front_contamination_level", frontContaminationLevel);
            fuzzyRuleSet.setVariable("front_left_contamination_level", frontLeftContaminationLevel);
            fuzzyRuleSet.setVariable("front_right_contamination_level", frontRightContaminationLevel);
            fuzzyRuleSet.evaluate();
            fuzzyRuleSet.getVariable("angle_change").chartDefuzzifier(true);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(
                "Niepoprawna liczba parametrow. Przyklad: java FuzzyExample string<plik_fcl> int<poziom natezenia> int<pora dnia>");
        } catch (NumberFormatException ex) {
            System.out.println(
                "Niepoprawny parametr. Przyklad: java FuzzyExample string<plik_fcl> int<poziom natezenia> int<pora dnia>");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
package Framework;
import Main.Individual;
import robocode.BattleResults;

/**
 * BattleMaker interface
 */

@SuppressWarnings({"EmptyMethod", "SameReturnValue"})
public interface BattleMaker {
    void setOpponents(String[] opponents);
    void setNumOpponents(int number);
    float getIndividualFitness(Individual individual);
    float getIndividualFitnessBatchRun(Individual individual);
    float getFitness(BattleResults[] battleResults);
    String stringifyOpponentArray(String[] opponents);
}

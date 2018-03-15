package Framework;

import Main.Individual;
import robocode.BattleResults;

/**
 * Created by Oliver on 04/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
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

package Framework;

import Main.Individual;

/**
 * Created by Oliver on 04/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public interface BattleMaker {
    void setOpponents(String[] opponents);
    void setNumOpponents(int number);
    float getIndividualFitness(Individual individual);
    float getIndividualFitnessBatchRun(Individual individual);
    String stringifyOpponentArray(String[] opponents);
}

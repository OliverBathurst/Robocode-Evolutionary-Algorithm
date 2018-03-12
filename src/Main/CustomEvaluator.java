package Main;

import Framework.Evaluator;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class CustomEvaluator implements Evaluator {
    private final Battle battle;

    CustomEvaluator(boolean visible, int numClones){
        this.battle = new Battle(visible, numClones);//setup Battle object
    }

    /**
     * Evaluates the individual using a Battle
     */
    @Override
    public float evaluateFitness(Individual individual) {
        return battle.getIndividualFitnessBatchRun(individual);//setup battle
    }

}
package Main;

import Framework.Evaluator;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class CustomEvaluator implements Evaluator {
    private boolean visible = false;
    private int numberHelpers = 0;

    CustomEvaluator(boolean visible, int numClones){
        this.visible = visible;
        this.numberHelpers = numClones;
    }

    /**
     * Evaluates the individual using a Battle
     */
    @Override
    public float evaluateFitness(Individual individual) {
        return new Battle(visible, numberHelpers).getIndividualFitnessBatchRun(individual);//setup battle
    }

}
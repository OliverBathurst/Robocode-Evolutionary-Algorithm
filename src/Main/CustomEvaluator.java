package Main;

import Framework.Evaluator;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class CustomEvaluator implements Evaluator {

    CustomEvaluator(){}

    @Override
    public float evaluateFitness(Individual individual) {
        return new Battle(false).getIndividualFitnessBatchRun(individual);
    }

}
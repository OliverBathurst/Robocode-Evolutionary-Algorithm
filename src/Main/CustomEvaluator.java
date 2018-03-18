package Main;

import Framework.Evaluator;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 * Evaluates the fitness of an individual
 * In this implementation, this is done via a robot battle
 */

class CustomEvaluator implements Evaluator {
    private final Battle battle;

    /**
     * Overloaded non-default constructor, can set number of collaborative robots
     */
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
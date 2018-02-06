package Framework;

import Main.Individual;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public interface EvolutionaryAlgorithm {
    void init(float targetFitness, boolean minimise, Population population,
              Evaluator customEvaluator, Mutator mutate, Selector parent, Selector generation, Crossover crossover);
    boolean terminateCondition();
    void setNumGenerations(int number);
    void setLogger(Logger log);
    Individual run();
}

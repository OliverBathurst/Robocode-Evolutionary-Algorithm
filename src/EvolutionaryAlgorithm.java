/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public interface EvolutionaryAlgorithm {
    void init(int populationSize, float targetFitness, float mutationRate,
           Evaluator customEvaluator, Mutator mutate, Selector selector);
    void run();
}

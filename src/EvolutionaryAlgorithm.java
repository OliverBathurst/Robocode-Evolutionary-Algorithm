public interface EvolutionaryAlgorithm {
    void init(int populationSize, float targetFitness, float mutationRate,
           Evaluator customEvaluator, Mutator mutate, Selector selector);
    void run();
}

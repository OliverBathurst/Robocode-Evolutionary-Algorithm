package Framework;
import Main.Individual;

/**
 * Standard evolutionary algorithm interface, including Logger, another interface
 */
public interface EvolutionaryAlgorithm {
    void init(float targetFitness, boolean minimise, boolean elitism, Population population,
              Evaluator customEvaluator, Mutator mutate, Selector parent, Selector generation, Crossover crossover);
    boolean terminateCondition();
    void setNumGenerations(int number);
    void setLogger(Logger log);
    Logger getLogger();
    Individual getBest();
    Individual getTotalBest();
}

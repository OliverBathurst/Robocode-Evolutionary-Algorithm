/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class TestEA implements EvolutionaryAlgorithm {
    private float targetFitness, mutationRate;
    private int populationSize;
    private Evaluator evalOp;
    private Selector selectOp;
    private Mutator mutateOp;
    private int generations;

    @Override
    public void init(int populationSize, float targetFitness, float mutationPercent,
                     Evaluator customEvaluator, Mutator mutate, Selector selector) {
        this.populationSize = populationSize;
        this.targetFitness = targetFitness;
        this.mutationRate = mutationPercent;
        this.evalOp = customEvaluator;
        this.selectOp = selector;
        this.mutateOp = mutate;
    }

    @Override
    public void run() {
        NewPopulation p = new NewPopulation(populationSize);
        p.createPopulation();
        //do rest here
        generations++;
    }
}
class TestEA{
    private final float targetFitness, mutationRate;
    private final int populationSize;
    private final Evaluator evalOp;
    private final Mutator mutateOp;
    private int generations;

    TestEA(int populationSize, float targetFitness, float mutationRate,
           Evaluator evaluator, Mutator mutate){
        this.populationSize = populationSize;
        this.targetFitness = targetFitness;
        this.mutationRate = mutationRate;
        this.evalOp = evaluator;
        this.mutateOp = mutate;
    }

    void run(){
        Population p = new Population(populationSize);
        p.generatePopulation();

        generations++;
    }
}

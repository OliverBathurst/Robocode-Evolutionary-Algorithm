class TestEA{
    private final float targetFitness, mutationRate;
    private final int populationSize;
    private final CustomEvaluator evalOp;
    private final RandomMutator mutateOp;
    private int generations;

    TestEA(int populationSize, float targetFitness, float mutationRate,
           CustomEvaluator customEvaluator, RandomMutator mutate){
        this.populationSize = populationSize;
        this.targetFitness = targetFitness;
        this.mutationRate = mutationRate;
        this.evalOp = customEvaluator;
        this.mutateOp = mutate;
    }

    void run(){
        Population p = new Population(populationSize);
        p.generatePopulation();

        generations++;
    }
}
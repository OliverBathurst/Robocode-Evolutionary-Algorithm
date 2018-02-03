import java.util.ArrayList;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class TestEA implements EvolutionaryAlgorithm {
    private ArrayList<Individual> population = new ArrayList<>();
    private Individual best;
    private Population populationInit;
    private Evaluator evalOp;
    private Crossover crossOp;
    private Selector parentSelectOp, genSelectOp;
    private Mutator mutateOp;
    private int generations, populationSize;
    private float targetFitness;
    private boolean minimize;

    @Override
    public void init(int populationSize, float targetFitness, boolean minimise, Population population, Evaluator customEvaluator, Mutator mutate, Selector parent, Selector generation, Crossover crossover) {
        this.populationSize = populationSize;
        this.targetFitness = targetFitness;
        this.minimize = minimise;
        this.populationInit = population;
        this.evalOp = customEvaluator;
        this.parentSelectOp = parent;
        this.genSelectOp = generation;
        this.mutateOp = mutate;
        this.crossOp = crossover;
    }

    @Override
    public Individual run() {
        populationInit.createPopulation();//INITIALISE
        population = populationInit.returnPopulation();
        ArrayList<Individual> children = new ArrayList<>();//temp pop for storing children

        boolean finished = false;//Sentinel for while loop
        this.generations = 0;//set generations back to 0

        for (Individual individual: population) {
            individual.setFitness(evalOp.evaluateFitness(individual));//EVALUATE
        }

        while(!finished){
            children.clear();
            while(children.size() < population.size()) {//perform crossover on selected parents and add to children
                children.add(this.crossOp.crossover(parentSelectOp.selectFromPopulation(population),parentSelectOp.selectFromPopulation(population)));//SELECT AND CROSSOVER
            }
            //MUTATE RESULTING OFFSPRING
            for (Individual individual: children) {
                mutateOp.mutate(individual);//mutate
                individual.setFitness(evalOp.evaluateFitness(individual));//evaluate
            }
            //EVALUATE NEW CANDIDATES
            for(Individual childIndividual: children) {
                childIndividual.setFitness(evalOp.evaluateFitness(childIndividual));
            }

            population.clear();//clear pop ready for next gen
            population.addAll(genSelectOp.selectIndividualsFromPopulation(children, populationSize));//SELECT FOR NEXT GEN
            setBest();

            finished = terminateCondition();

            this.generations++;
            System.out.println("Generations: " + this.generations);

            if(generations == 1){
                System.out.println("Generation:\tFitness:\tGene1:\tGene2:");
            }else{
                System.out.println(this.generations + "\t" + this.best.getFitness() + "\t" + this.best.genes[0]+"\t"+this.best.genes[1]);
            }
        }
        setBest();
        return this.best;
    }
    @Override
    public boolean terminateCondition() {
        boolean terminate = false;
        if((this.best.getFitness() < this.targetFitness) && (this.minimize)){
            terminate = true;
        }else if((this.best.getFitness() > this.targetFitness) && (!this.minimize)){
            terminate = true;
        }
        if(this.generations >= 50000){
            terminate = true;
        }
        return terminate;
    }
    private void setBest(){
        if(this.generations <= 1){
            this.best = population.get(0);
        }
        for (Individual individual: population) {
            if(this.best.compareTo(individual) > 0) {
                this.best = individual;
            }
        }
    }
}
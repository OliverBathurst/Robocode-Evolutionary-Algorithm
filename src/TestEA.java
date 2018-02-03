import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class TestEA implements EvolutionaryAlgorithm {
    private ArrayList<Individual> population = new ArrayList<>();
    private BattleRunner br = new BattleRunner();
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
        Individual[] tmpPop = new Individual[this.populationInit.getSize()];//Temporary Arrays
        Individual[] tmpSelect = new Individual[this.populationInit.getSize() * 2];

        populationInit.createPopulation();
        population = populationInit.returnPopulation();//ensure empty

        for (Individual aPopulation : population) {
            aPopulation.setFitness(evalOp.evaluateFitness(aPopulation));
        }
        //Main Evolutionary Loop
        boolean finished = false;//Sentinel for while loop
        ArrayList<Individual> Children = new ArrayList<>();//temp pop for storing children

        this.generations = 0;//set generations back to 0

        while(!finished){
            //float[] finesses = br.runBatchWithSamples(population, population, 1);
            //Perform mutation
            for (Individual individual: population) {
                mutateOp.mutate(individual);//mutate
                individual.setFitness(evalOp.evaluateFitness(individual));//evaluate
            }
            //Clear Children from old gen and generate new ones
            Children.clear();
            tmpPop = population.toArray(tmpPop);
            while(Children.size() < population.size()) {//perform crossover on selected parents and add to children
                Children.add(this.crossOp.crossover(parentSelectOp.selectFromPopulation(tmpPop), parentSelectOp.selectFromPopulation(tmpPop)));
            }
            //Evaluate children
            for(Individual childIndividual: Children) {
                childIndividual.setFitness(evalOp.evaluateFitness(childIndividual));
            }
            //Perform selection for next generation
            //temporarily add all pop to children
            Children.addAll(population);
            tmpSelect = Children.toArray(tmpSelect);

            population.clear();//clear pop ready for next gen
            population.addAll(Arrays.asList(genSelectOp.selectIndividualsFromPopulation(tmpSelect, populationSize)));
            //make sure to find the best for this gen
            //setBest();
            //increment generations
            this.generations++;
            //check for terminal condition
            //finished = terminateCondition();

            if(generations==1){
                System.out.println("Generation:\tFitness:\tGene1:\tGene2:");
            }else if((generations%50)==0){
                System.out.println(this.generations + "\t" +
                        this.best.getFitness() + "\t" +
                        this.best.genes[0]+"\t"+this.best.genes[1]);
            }
            finished = true;
        }
        //setBest();//check that best really is before returning
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
        if(this.generations >= 500000){
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
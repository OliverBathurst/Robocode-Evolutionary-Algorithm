package Main;

import Framework.*;

import java.util.ArrayList;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class TestEA implements EvolutionaryAlgorithm {
    private int generations, noRuns = 0, generationsLimit = Integer.MAX_VALUE;
    private ArrayList<Individual> population = new ArrayList<>();
    private Individual best;
    private Population populationInit;
    private Evaluator evalOp;
    private Crossover crossOp;
    private Selector parentSelectOp, genSelectOp;
    private Mutator mutateOp;
    private Logger log;
    private float targetFitness;
    private boolean minimize;

    @Override
    public void init(float targetFitness, boolean minimise, Population population, Evaluator customEvaluator,
                     Mutator mutate, Selector parent, Selector generation, Crossover crossover) {
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
    public void setNumGenerations(int number) {this.generationsLimit = number;}

    @Override
    public void setLogger(Logger log) {
        this.log = log;
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    public Individual run() {
        populationInit.createPopulation();//INITIALISE
        population = populationInit.returnPopulation();
        ArrayList<Individual> children = new ArrayList<>();//temp pop for storing children

        boolean finished = false;//Sentinel for while loop
        this.generations = 0;//set generations back to
        this.noRuns++;//increment number of times run() has been called

        System.out.println("Current number of runs: " + this.noRuns);
        System.out.println("Population size: " + population.size());
        System.out.println("Evaluating population...");
        for (Individual individual: population) {
            individual.setFitness(evalOp.evaluateFitness(individual));//EVALUATE
        }

        System.out.println("Entering evolutionary loop");
        while(!finished){
            children.clear();
            System.out.println("Crossing over...");
            while(children.size() < population.size()) {//perform crossover on selected parents and add to children
                children.add(this.crossOp.crossover(parentSelectOp.selectFromPopulation(population),parentSelectOp.selectFromPopulation(population)));//SELECT AND CROSSOVER
            }
            //MUTATE RESULTING OFFSPRING
            System.out.println("Children size: " + children.size());
            System.out.println("Mutating...");
            for (Individual individual: children) {
                mutateOp.mutate(individual);//mutate
            }
            //EVALUATE NEW CANDIDATES
            System.out.println("Evaluating children...");
            for(Individual childIndividual: children) {
                childIndividual.setFitness(evalOp.evaluateFitness(childIndividual));
            }

            System.out.println("Clearing and adding...");
            int populationSize = population.size();
            population.clear();//clear pop ready for next gen
            ///error here
            population.addAll(genSelectOp.selectIndividualsFromPopulation(children, populationSize));//SELECT FOR NEXT GEN
            System.out.println("New population size: " + population.size());

            setBest();
            finished = terminateCondition();

            this.generations++;

            if(this.best != null) {
                log.log(generations, this.best.fitness);
                System.out.println("Generation: " + this.generations + "\nFitness: " + this.best.fitness
                        + "\nGenome:\n" + getBestGenome());
            }
        }
        setBest();
        return this.best;
    }
    @Override
    public boolean terminateCondition() {
        boolean terminate = false;
        if(this.best != null) {
            if ((this.best.fitness < this.targetFitness) && (this.minimize)) {
                terminate = true;
            } else if ((this.best.fitness > this.targetFitness) && (!this.minimize)) {
                terminate = true;
            }
            if (this.generations >= generationsLimit) {
                terminate = true;
            }
        }
        return terminate;
    }

    private void setBest(){
        if(this.generations <= 1){
            if(population.size() > 0) {
                this.best = population.get(0);
            }
        }
        float currentFitness = 0f;
        for (Individual individual: population) {
            if(individual.fitness > currentFitness){
                currentFitness = individual.fitness;
                this.best = individual;
            }
        }
    }
    private String getBestGenome(){
        StringBuilder asString = new StringBuilder();
        int length = this.best.genes.length;
        for(int i = 0; i < length; i++){
            asString.append("Gene: ").append(i).append(" Value: ").append(this.best.genes[i]).append("\n");
        }
        return asString.toString();
    }
}
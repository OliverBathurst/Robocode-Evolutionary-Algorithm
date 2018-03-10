package Main;
import Framework.*;
import java.util.ArrayList;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class TestEA extends Thread implements EvolutionaryAlgorithm {
    private int generations = 0, generationsLimit = Integer.MAX_VALUE;//default max value
    private boolean minimize, finished = false, hasTerminated = false;//Sentinel for while loop, minimise quality
    private ArrayList<Individual> population = new ArrayList<>();
    private Individual best, totalBest;
    private Population populationInit;
    private Evaluator evalOp;
    private Crossover crossOp;
    private Selector parentSelectOp, genSelectOp;
    private Mutator mutateOp;
    private Logger log;
    private float targetFitness;

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
    public Individual getBest() {
        return best;
    }

    @Override
    public Individual getTotalBest() {
        return totalBest;
    }

    @Override
    public void run() {
        populationInit.createPopulation();//INITIALISE
        population = populationInit.returnPopulation();
        ArrayList<Individual> children = new ArrayList<>();//temp pop for storing children

        System.out.println("Generations Limit: " + generationsLimit);
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
                children.add(this.crossOp.crossover(parentSelectOp.selectFromPopulation(population),
                        parentSelectOp.selectFromPopulation(population)));//SELECT AND CROSSOVER
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
            population.addAll(genSelectOp.selectIndividualsFromPopulation(children, populationSize));//SELECT FOR NEXT GEN
            System.out.println("New population size: " + population.size());

            setBest();

            finished = terminateCondition();

            this.generations++;

            if(this.best != null) {
                log.log(generations, this.best.fitness);
                System.out.println("Generation: " + this.generations + "\nFitness: " + this.best.fitness
                        + "\nGenome:\n" + printGenome());
            }
        }
        setBest();
    }
    @Override
    public boolean terminateCondition() {
        boolean terminate = false;
        if(this.best != null) {
            if ((this.best.fitness < this.targetFitness) && (this.minimize)) {
                terminate = true;
            }
            if ((this.best.fitness > this.targetFitness) && (!this.minimize)) {
                terminate = true;
            }
            if (this.generations >= (generationsLimit - 1)) {
                terminate = true;
            }
            if(hasTerminated){
                terminate = true;
            }
        }
        return terminate;
    }

    boolean hasTerminated(){
        return hasTerminated;
    }

    void forceTerminate(){
        this.hasTerminated = true;
    }

    void resetTermination(){
        this.hasTerminated = false;
    }

    private void setBest(){
        float currentFitness = 0f;

        for (Individual individual: population) {
            if(individual.fitness >= currentFitness){
                currentFitness = individual.fitness;
                this.best = individual;
            }
        }
        //set global best over run
        if(this.totalBest != null){
            if(this.best.fitness > this.totalBest.fitness){
                this.totalBest = this.best;
            }
        }else{
            this.totalBest = this.best;
        }
    }
    String printGenome(){
        StringBuilder asString = new StringBuilder();
        if(this.best != null) {
            for (int i = 0; i < this.best.getGeneLength(); i++) {
                asString.append("Gene: ").append(i).append(" Value: ").append(this.best.genes[i]).append("\n");
            }
        }
        return asString.toString();
    }
    String printGenome(Individual individual){
        StringBuilder asString = new StringBuilder();
        if(individual != null) {
            for (int i = 0; i < individual.getGeneLength(); i++) {
                asString.append("Gene: ").append(i).append(" Value: ").append(individual.genes[i]).append("\n");
            }
        }
        return asString.toString();
    }
}
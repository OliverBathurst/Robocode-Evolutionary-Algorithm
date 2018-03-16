package Main;
import Framework.*;
import java.util.ArrayList;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class TestEA extends Thread implements EvolutionaryAlgorithm {
    private int generations = 0, generationsLimit = Integer.MAX_VALUE;//default max value
    private boolean minimize, finished = false, hasTerminated = false, elitism = false;//Sentinel for while loop, minimise quality
    private ArrayList<Individual> population = new ArrayList<>();
    private Logger logAverages = new Log();
    private Individual best, totalBest;
    private Population populationInit;
    private Evaluator evalOp;
    private Crossover crossOp;
    private Selector parentSelectOp, genSelectOp;
    private Mutator mutateOp;
    private Logger log;
    private float targetFitness;

    @Override
    public void init(float targetFitness, boolean minimise, boolean elitism, Population population, Evaluator customEvaluator,
                     Mutator mutate, Selector parent, Selector generation, Crossover crossover) {
        this.targetFitness = targetFitness;
        this.minimize = minimise;
        this.elitism = elitism;
        this.populationInit = population;
        this.evalOp = customEvaluator;
        this.mutateOp = mutate;
        this.parentSelectOp = parent;
        this.genSelectOp = generation;
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
        population = populationInit.createPopulation();//INITIALISE POP
        ArrayList<Individual> children = new ArrayList<>();//temp pop for storing children

        System.out.println("Generations Limit: " + generationsLimit);
        System.out.println("Population size: " + population.size());
        System.out.println("Elitism: " + this.elitism);

        System.out.println("Entering evolutionary loop");

        while(!finished){
            System.out.println("Evaluating population...");

            //EVALUATE INITIAL POP
            for (Individual individual: population) {
                individual.setFitness(evalOp.evaluateFitness(individual));
            }

            //CALCULATE AVERAGE FITNESS FOR GENERATION
            float averageFitnessGeneration = 0.0f;
            for (Individual individual: population) {
                averageFitnessGeneration += individual.fitness;
            }
            logAverages.log(generations, (averageFitnessGeneration/population.size()));
            //////////////////////////////////////////


            children.clear();//clear out children for next run

            //CROSSOVER CHILDREN
            System.out.println("Crossing over...");
            while(children.size() < population.size()) {//perform crossover on selected parents and add to children
                children.add(crossOp.crossover(parentSelectOp.selectFromPopulation(population),
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
                childIndividual.setFitness(evalOp.evaluateFitness(childIndividual));//set their fitnesses by battle
            }

            System.out.println("Clearing and adding...");
            int populationSize = population.size();//retain size of population

            Individual best = genSelectOp.selectFromPopulation(population);//select best from pop to bring forward (if elitism enabled)
            population.clear();//clear pop ready for next gen

            if(this.elitism){//if elitism is enabled
                population.add(best);//add in best from parents population
                population.addAll(genSelectOp.selectIndividualsFromPopulation(children, populationSize-1));//SELECT FOR NEXT GEN (fill in the rest of places with fittest children)
            }else{//copy all children in to population via greedy selection
                population.addAll(genSelectOp.selectIndividualsFromPopulation(children, populationSize));
            }

            System.out.println("New population size: " + population.size());//should be the same size, use for verification

            setBest();//set current and global best
            finished = terminateCondition();//check terminate condition
            this.generations++;//increment generations

            log.log(generations, this.best.fitness);//log for writing to CSV and graph view
            System.out.println("Generation: " + this.generations + "\nFitness: " + this.best.fitness + "\nGenome:\n" + printGenome());

        }
        setBest();//finally, set best
    }

    /**
     * Handles the termination of the evolutionary algorithm run
     */
    @Override
    public boolean terminateCondition() {
        boolean terminate = false;

        if(this.best != null) {//initial check
            if ((this.best.fitness < this.targetFitness) && (this.minimize)) {
                terminate = true;//if the job is to minimise fitness and best is below that target, terminate
            }
            if ((this.best.fitness > this.targetFitness) && (!this.minimize)) {
                terminate = true;//if the job is to maximise fitness and best is above that target, terminate
            }
        }

        if (this.generations >= (generationsLimit - 1)) {
            terminate = true;//reached gen limit
        }
        if(hasTerminated){
            terminate = true;//check the user has terminated forcefully
        }
        return terminate;
    }

    /**
     * Forcefully terminate the run (completes current run before finishing)
     */
    void forceTerminate(){
        this.hasTerminated = true;
    }

    /**
     * Sets the current best and the global best solution (individual
     */
    private void setBest(){
        float currentFitness = 0f;//initialise to 0 so the best individual will always be set after completion
        //SET CURRENT BEST
        for (Individual individual: population) {//iterate over population members
            if(individual.fitness >= currentFitness){//if the individual fitness > the current fitness
                currentFitness = individual.fitness;//set new highest fitness
                this.best = individual;//set current best
            }
        }
        //SET GLOBAL BEST OVER RUN SO FAR
        if(this.totalBest != null){
            if(this.best.fitness > this.totalBest.fitness){
                this.totalBest = this.best;
            }
        }else{
            this.totalBest = this.best;//if not currently set, total best = current best (gen 0)
        }
    }

    /**
     * Collects the genome of the current best individual
     */
    String printGenome(){
        StringBuilder asString = new StringBuilder();
        if(this.best != null) {
            for (int i = 0; i < this.best.getGeneLength(); i++) {
                asString.append("Gene: ").append(i).append(" Value: ").append(this.best.genes[i]).append("\n");
            }
        }
        return asString.toString();
    }
    /**
     * Collects the genome of a given individual as a string
     */
    String printGenome(Individual individual){
        StringBuilder asString = new StringBuilder();
        if(individual != null) {
            for (int i = 0; i < individual.getGeneLength(); i++) {
                asString.append("Gene: ").append(i).append(" Value: ").append(individual.genes[i]).append("\n");
            }
        }
        return asString.toString();
    }
    Logger getAverages(){
        return logAverages;
    }
}
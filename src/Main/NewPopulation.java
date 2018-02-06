package Main;

import Framework.Population;

import java.util.*;
/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class NewPopulation implements Population {
    private final Double[] geneMin = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};//example mins
    private final Double[] geneMax = new Double[]{100.0,100.0,360.0,360.0,360.0,360.0,100.0,100.0};//example maxes
    private final ArrayList<Individual> population;
    private final int populationSize;
    private final Random geneRandomize = new Random();

    NewPopulation(int size){
        this.populationSize = size;
        this.population = new ArrayList<>(size);
    }
    @Override
    public ArrayList<Individual> returnPopulation() {
        return population;
    }
    @Override
    public void sort(){
        population.sort((o1, o2) -> (int) (o1.fitness - o2.fitness));
    }
    @Override
    public int getSize() {
        return population.size();
    }
    @Override
    public void createPopulation() {
        for(int i = 0; i < populationSize; i++) {
            population.add(createIndividual());
        }
    }
    @Override
    public Individual createIndividual() {
        Double[] genes = new Double[geneMin.length];

        for(int i = 0; i < geneMin.length; i++){
            genes[i] = geneMin[i] + (geneMax[i] - geneMin[i]) * geneRandomize.nextDouble();
        }

        return new Individual(genes, geneMin, geneMax, geneMin.length);
    }
}
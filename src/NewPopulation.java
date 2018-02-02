import robocode.Robot;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class NewPopulation implements Population {
    private final Double[] geneMin = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0};//example mins
    private final Double[] geneMax = new Double[]{100.0,100.0,100.0,100.0,100.0,100.0,100.0};//example maxes
    private final Individual[] population;
    private final Random geneRandomize = new Random();

    NewPopulation(int size){
        this.population = new Individual[size];
    }

    Individual[] returnPop(){
        return population;
    }

    Individual returnBest(){
        return population[0];
    }

    void sort(){
        Arrays.sort(population, (o1, o2) -> (int) (o1.getFitness() - o2.getFitness()));//sort ascending
    }

    @Override
    public void createPopulation() {
        for(int i = 0; i < population.length - 1; i++) {
            population[i] = (createMember(new Individual()));
        }
    }
    @Override
    public Individual createMember(Individual individual) {
        Double[] genes = new Double[geneMin.length];

        for(int i = 0; i < geneMin.length - 1; i++){
            genes[i] = geneMin[i] + (geneMax[i] - geneMin[i]) * geneRandomize.nextDouble();
        }

        return new Individual(new Robot(), genes, geneMin, geneMax, geneMin.length);
    }
}
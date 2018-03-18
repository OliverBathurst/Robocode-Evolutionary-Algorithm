package Main;
import Framework.Population;
import java.security.SecureRandom;
import java.util.*;
/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

@SuppressWarnings("Convert2Lambda")
public class NewPopulation implements Population {
    private final Double[] geneMin = new Double[]{
            //0-3 the number of methods to include in each event
            0.0,0.0,0.0,0.0,//min values for methods per callback (event)

            //the method indexes for event 1, 4-12
            0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,//min values for event 1's methods
            //the method indexes for event 2, 13-21
            0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,//min values for event 2's methods
            //the method indexes for event 3, 22-30
            0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,//min values for event 3's methods
            //the method indexes for event 4, 31-39
            0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,//min values for event 4's methods

            //the method values to use for event 1, 40-48
            -500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,//which method values to use
            //the method values to use for event 2, 49-57
            -500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,//which method values to use
            //the method values to use for event 3, 58-66
            -500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,//which method values to use
            //the method values to use for event 4, 67-75
            -500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,-500.0,//min values for event 4's methods
    };
    private final Double[] geneMax = new Double[]{
            8.0,8.0,8.0,8.0,

            8.1,8.1,8.1,8.1,8.1,8.1,8.1,8.1,8.1,//max values for event 1's method indexes (8.1 for rounding (so 8 is possible))
            8.1,8.1,8.1,8.1,8.1,8.1,8.1,8.1,8.1,//max values for event 2's method indexes
            8.1,8.1,8.1,8.1,8.1,8.1,8.1,8.1,8.1,//max values for event 3's method indexes
            8.1,8.1,8.1,8.1,8.1,8.1,8.1,8.1,8.1,//max values for event 4's method indexes

            500.0,500.0,500.0,500.0,500.0,500.0,500.0,500.0,500.0,
            500.0,500.0,500.0,500.0,500.0,500.0,500.0,500.0,500.0,
            500.0,500.0,500.0,500.0,500.0,500.0,500.0,500.0,500.0,
            500.0,500.0,500.0,500.0,500.0,500.0,500.0,500.0,500.0//method max values to use
    };
    private final SecureRandom geneRandomize = new SecureRandom();//use SecureRandom for more random randoms
    private final ArrayList<Individual> population;//population members list
    private final int populationSize;

    /**
     * Initialise constructor with size
     */
    NewPopulation(int size){
        this.populationSize = size;
        this.population = new ArrayList<>(size);
    }
    @Override
    public ArrayList<Individual> returnPopulation() {
        return population;
    }

    /**
     * Sort population via fitness
     */
    @Override
    public void sort(){
        population.sort(Individual::compareTo);
    }

    /**
     * Get size of population
     */
    @Override
    public int getSize() {
        return population.size();
    }

    /**
     * Fills in population with individuals, returns population
     */
    @Override
    public ArrayList<Individual> createPopulation() {
        for(int i = 0; i < populationSize; i++) {
            population.add(createIndividual());//add in a new individual
        }
        return population;
    }

    /**
     * Creates an individual with randomised starting genes, returns new individual
     */
    @Override
    public Individual createIndividual() {
        Double[] genes = new Double[geneMin.length];

        for(int i = 0; i < genes.length; i++){
            genes[i] = geneMin[i] + (geneMax[i] - geneMin[i]) * geneRandomize.nextDouble();
        }

        return new Individual(genes, geneMin, geneMax, geneMin.length);
    }
}
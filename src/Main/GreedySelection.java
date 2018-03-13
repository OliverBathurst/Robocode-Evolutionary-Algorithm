package Main;

import Framework.Selector;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class GreedySelection implements Selector {

    @Override
    public Individual selectFromPopulation(ArrayList<Individual> pop) {
        return selectIndividualsFromPopulation(pop, 1).get(0);//get current best
    }

    /**
     * Sorts and returns the fittest N candidates
     */
    @Override
    public ArrayList<Individual> selectIndividualsFromPopulation(ArrayList<Individual> pop, int number) {
        sort(pop);//sort population by fitness
        return new ArrayList<>(pop.subList(0,number));//return sublist from first (best) individual down to number
    }

    /**
     * Sorts in increasing fitness
     */
    @Override
    public void sort(ArrayList<Individual> individuals) {
        individuals.sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                return o1.compareTo(o2);
            }
        });//sort ascending
    }
}

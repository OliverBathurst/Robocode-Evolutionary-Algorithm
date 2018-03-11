package Main;

import Framework.Selector;

import java.util.ArrayList;

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
        individuals.sort((o1, o2) -> (int) (o1.fitness - o2.fitness));//sort ascending
    }
}

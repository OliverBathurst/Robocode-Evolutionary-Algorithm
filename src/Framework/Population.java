package Framework;

import Main.Individual;

import java.util.ArrayList;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public interface Population {
    void createPopulation();
    Individual createIndividual();
    ArrayList<Individual> returnPopulation();
    void sort();
    int getSize();
}

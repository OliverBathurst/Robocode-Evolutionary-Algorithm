package Framework;
import Main.Individual;

import java.util.ArrayList;

/**
 * Population interface
 */
public interface Population {
    ArrayList<Individual> createPopulation();
    Individual createIndividual();
    ArrayList<Individual> returnPopulation();
    void sort();
    int getSize();
}

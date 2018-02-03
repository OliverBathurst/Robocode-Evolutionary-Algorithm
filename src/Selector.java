import java.util.ArrayList;
/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

interface Selector {
    Individual selectFromPopulation(ArrayList<Individual> pop);
    ArrayList<Individual> selectIndividualsFromPopulation(ArrayList<Individual> pop, int number);
    void sort(ArrayList<Individual> individuals);
}

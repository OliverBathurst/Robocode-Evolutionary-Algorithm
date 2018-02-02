/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

interface Selector {
    Individual selectFromPopulation(Individual[] pop);
    Individual[] selectIndividualsFromPopulation(Individual[] pop, int number);
    void sort(Individual[] individuals);
}

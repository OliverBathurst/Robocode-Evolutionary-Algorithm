/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

interface Selector {
    Individual selectFromPopulation(Population pop);
    Individual[] selectIndividualsFromPopulation(Population pop, int number);
}

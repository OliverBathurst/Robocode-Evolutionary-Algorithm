import java.util.Arrays;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class GreedySelection implements Selector{
    @Override
    public Individual selectFromPopulation(Population pop) {
        return selectIndividualsFromPopulation(pop, 1)[0];
    }

    @Override
    public Individual[] selectIndividualsFromPopulation(Population pop, int number) {
        pop.sort();
        return Arrays.copyOfRange(pop.returnPopulation(),0,number);
    }
}

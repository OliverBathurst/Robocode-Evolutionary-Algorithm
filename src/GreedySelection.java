import java.util.Arrays;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class GreedySelection implements Selector{
    @Override
    public Individual selectFromPopulation(Individual[] pop) {
        return selectIndividualsFromPopulation(pop, 1)[0];
    }

    @Override
    public Individual[] selectIndividualsFromPopulation(Individual[] pop, int number) {
        sort(pop);
        return Arrays.copyOfRange(pop,0,number);
    }

    @Override
    public void sort(Individual[] individuals) {
        Arrays.sort(individuals, (o1, o2) -> (int) (o1.getFitness() - o2.getFitness()));//sort ascending
    }
}

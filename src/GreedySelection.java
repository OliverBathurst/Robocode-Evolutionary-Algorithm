import java.util.ArrayList;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class GreedySelection implements Selector{

    @Override
    public Individual selectFromPopulation(ArrayList<Individual> pop) {
        return selectIndividualsFromPopulation(pop, 1).get(0);
    }

    @Override
    public ArrayList<Individual> selectIndividualsFromPopulation(ArrayList<Individual> pop, int number) {
        sort(pop); //err here
        System.out.println(pop.size());
        return new ArrayList<>(pop.subList(0,number));
    }

    @Override
    public void sort(ArrayList<Individual> individuals) {
        individuals.sort((o1, o2) -> (int) (o1.getFitness() - o2.getFitness()));//sort ascending
    }
}

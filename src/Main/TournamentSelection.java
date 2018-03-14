package Main;
import Framework.Selector;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Oliver on 02/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class TournamentSelection implements Selector {

    @Override
    public Individual selectFromPopulation(ArrayList<Individual> pop) {
        return selectIndividualsFromPopulation(pop, 5).get(0);
    }

    @Override
    public ArrayList<Individual> selectIndividualsFromPopulation(ArrayList<Individual> pop, int number) {
        assert number > 0;
        SecureRandom numGen = new SecureRandom();

        ArrayList<Individual> selected = new ArrayList<>();
        ArrayList<Individual> competitors = new ArrayList<>();

        while(selected.size() < number){
            competitors.clear();//clear the competitors array list for new selection

            for(int competitor = 0; competitor < number; competitor++){
                competitors.add(pop.get(numGen.nextInt(pop.size())));
            }

            Individual tempBest = competitors.get(0);//select first (random number)
            for(int tournament = 0; tournament < number; tournament++){
                if(tempBest.fitness < competitors.get(tournament).fitness){
                    tempBest = competitors.get(tournament);
                }
            }
            selected.add(tempBest);
        }

        sort(selected);
        return selected;
    }

    @Override
    public void sort(ArrayList<Individual> individuals) {
        individuals.sort(new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                return o1.compareTo(o2);
            }
        });
    }
}

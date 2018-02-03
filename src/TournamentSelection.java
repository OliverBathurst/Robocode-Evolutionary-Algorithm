import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Oliver on 02/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class TournamentSelection implements Selector{

    @Override
    public Individual selectFromPopulation(ArrayList<Individual> pop) {
        return selectIndividualsFromPopulation(pop, 5).get(0);
    }

    @Override
    public ArrayList<Individual> selectIndividualsFromPopulation(ArrayList<Individual> pop, int number) {
        Random numGen = new Random(System.currentTimeMillis());

        ArrayList<Individual> selected = new ArrayList<>();
        ArrayList<Individual> competitors = new ArrayList<>();
        Individual tempBest;

        int numSelected = 0;
        while(numSelected < number){
            for(int competitor = 0; competitor < number; competitor++){
                competitors.add(competitor, pop.get(numGen.nextInt(pop.size())));
            }
            tempBest = competitors.get(0);
            for(int tournament = 0; tournament < number; tournament++){
                if(tempBest.compareTo(competitors.get(tournament)) > 0){
                    tempBest = competitors.get(tournament);
                }
            }
            selected.add(numSelected, tempBest);
            numSelected++;
        }
        return selected;
    }

    @Override
    public void sort(ArrayList<Individual> individuals) { }
}

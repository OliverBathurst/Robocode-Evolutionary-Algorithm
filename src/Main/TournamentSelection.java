package Main;
import Framework.Selector;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Oliver on 02/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 * This class performs Tournament Selection on N randomly chosen competitors
 */

@SuppressWarnings("Convert2Lambda")
public class TournamentSelection implements Selector {
    private final int tournamentSize;//size of tournament

    /**
     * Initialise with tournament size
     */
    TournamentSelection(int tournamentSize){
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Individual selectFromPopulation(ArrayList<Individual> pop) {
        return selectIndividualsFromPopulation(pop, tournamentSize).get(0);//use N competitors, get fittest (index 0)
    }

    @Override
    public ArrayList<Individual> selectIndividualsFromPopulation(ArrayList<Individual> pop, int number) {
        assert number > 0;//has to be greater than 0
        SecureRandom numGen = new SecureRandom();//make sure truly random randoms

        ArrayList<Individual> selected = new ArrayList<>(number);//selected individuals (the fittest)
        ArrayList<Individual> competitors = new ArrayList<>(number);//competitors array

        while(number != selected.size()){//while not all added
            competitors.clear();//clear the competitors array list for new selection

            for(int competitor = 0; competitor < number; competitor++){
                competitors.add(pop.get(numGen.nextInt(pop.size())));//add competitors randomly
            }

            Individual tempBest = competitors.get(0);//select first as temporary best
            for (Individual competitor : competitors) {//
                if (competitor.fitness > tempBest.fitness) {//if greater than current
                    tempBest = competitor;//set new best
                }
            }
            selected.add(tempBest);//add best from tournament
        }

        sort(selected);//sort by fitness
        return selected;//return sorted list
    }

    /**
     * Sorts by fitness
     */
    @Override
    public void sort(ArrayList<Individual> individuals) {
        individuals.sort(Individual::compareTo);
    }
}

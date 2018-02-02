import java.util.Random;

/**
 * Created by Oliver on 02/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class TournamentSelection implements Selector{
    @Override
    public Individual selectFromPopulation(Individual[] pop) {
        return selectIndividualsFromPopulation(pop, 5)[0];
    }

    @Override
    public Individual[] selectIndividualsFromPopulation(Individual[] pop, int number) {
        Random numGen = new Random(System.currentTimeMillis());

        Individual[] selected = new Individual[number];
        Individual[] competitors = new Individual[number];
        Individual tempBest;

        int numSelected = 0;
        while(numSelected < number){
            for(int competitor = 0; competitor < number; competitor++){
                competitors[competitor] = pop[numGen.nextInt(pop.length)];
            }
            tempBest = competitors[0];
            for(int tournament = 0; tournament < number; tournament++){
                if(tempBest.compareTo(competitors[tournament]) > 0){
                    tempBest = competitors[tournament];
                }
            }
            selected[numSelected] = tempBest;
            numSelected++;
        }
        return selected;
    }

    @Override
    public void sort(Individual[] individuals) {}
}

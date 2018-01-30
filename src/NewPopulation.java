import robocode.Robot;
import java.util.ArrayList;
import java.util.Comparator;
/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class NewPopulation implements Population {
    private final ArrayList<Individual> population = new ArrayList<>();
    private final int size;
    private Comparator<Individual> comparator;

    NewPopulation(int size){
        this.size = size;
    }

    ArrayList<Individual> returnPop(){
        return population;
    }

    void setComparator(Comparator<Individual> c){
        this.comparator = c;
    }

    Individual returnBest(){
        return population.get(0);
    }

    void sort(){
        if(comparator != null){
            population.sort(comparator);
        }else{
            population.sort((o1, o2) -> (int) (o1.getFitness() - o2.getFitness()));
        }
    }

    @Override
    public void createPopulation() {
        for(int i = 0; i < size; i++) {
            population.add(new Individual(new Robot()));
        }
    }
}
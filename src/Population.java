import robocode.Robot;
import java.util.ArrayList;
import java.util.Comparator;

class Population {
    private final ArrayList<Individual> population = new ArrayList<>();
    private Comparator<Individual> comparator;
    private final int size;

    Population(int size){
        this.size = size;
    }

    ArrayList<Individual> returnPop(){
        return population;
    }

    void generatePopulation(){
        for(int i = 0; i < size; i++) {
            population.add(new Individual(new Robot()));
        }
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
}

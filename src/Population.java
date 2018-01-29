import robocode.Robot;
import java.util.ArrayList;

class Population {
    private ArrayList<Individual> population = new ArrayList<>();
    private int size;

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
}

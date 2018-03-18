package Framework;
import Main.Individual;
import java.util.ArrayList;

/**
 * Selection operator basic components interface
 */
public interface Selector {
    Individual selectFromPopulation(ArrayList<Individual> pop);
    ArrayList<Individual> selectIndividualsFromPopulation(ArrayList<Individual> pop, int number);
    void sort(ArrayList<Individual> individuals);
}

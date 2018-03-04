/**
 * Created by Oliver on 04/03/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package Main;
import Framework.Crossover;
import java.util.Arrays;

public class NPointCrossover implements Crossover {
    private final int crossoverPoint;

    NPointCrossover(int crossoverPoint){
        this.crossoverPoint = crossoverPoint;
    }

    @Override
    public Individual crossover(Individual parentA, Individual parentB) {
        Double[] newGenes = Arrays.copyOf(parentA.genes, parentA.genes.length);

        int counter = 0;
        while (counter < newGenes.length-1){
            newGenes[counter] = parentB.genes[counter];//crossover
            counter += crossoverPoint;//add n to counter
        }
        return new Individual(newGenes, parentA.geneMin, parentA.geneMax, parentA.geneLength);
    }
}

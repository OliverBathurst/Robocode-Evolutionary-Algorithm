package Main;
import Framework.Crossover;
import java.util.Arrays;
/**
 * Created by Oliver on 04/03/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 * N-point crossover (crosses over at N intervals in the genome)
 */

public class NPointCrossover implements Crossover {
    private final int crossoverPoint;

    /**
     * Init with crossover point (N)
     */
    NPointCrossover(int crossoverPoint){
        this.crossoverPoint = crossoverPoint;
    }

    /**
     * Use parent A as a base genome, swap gene at every Nth position with parent B's gene at the Nth position
     */
    @Override
    public Individual crossover(Individual parentA, Individual parentB) {
        Double[] newGenes = Arrays.copyOf(parentA.genes, parentA.genes.length);//copy in parent A's genes

        int counter = 0;//setup counter
        while (counter < newGenes.length){
            newGenes[counter] = parentB.genes[counter];//crossover with parent B's gene
            counter += crossoverPoint;//add n to counter
        }
        return new Individual(newGenes, parentA.geneMin, parentA.geneMax, parentA.geneLength);//return a new individual
    }
}

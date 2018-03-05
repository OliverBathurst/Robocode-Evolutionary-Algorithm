package Main;

import Framework.Crossover;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class UniformCrossover implements Crossover {
    private final Random numGen = new Random(System.currentTimeMillis());

    UniformCrossover(){}

    @Override
    public Individual crossover(Individual parentA, Individual parentB) {
        Double[] newGenes = Arrays.copyOf(parentA.genes, parentA.genes.length);//setup with parent A's genes as a base

        for(int gene = 0; gene < newGenes.length - 1; gene++){//iterate over all genes
            if(this.numGen.nextDouble() > 0.5){//50/50 chance
                newGenes[gene] = parentB.genes[gene];//substitute with parent B's gene
            }
        }
        return new Individual(newGenes, parentA.geneMin, parentA.geneMax, parentA.geneLength);
    }
}
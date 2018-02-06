package Main;

import Framework.Crossover;

import java.util.Random;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class UniformCrossover implements Crossover {
    private final Random numGen = new Random(System.currentTimeMillis());

    UniformCrossover(){}

    Individual performCrossover(Individual parentA, Individual parentB){
        return crossover(parentA, parentB);
    }

    @Override
    public Individual crossover(Individual parentA, Individual parentB) {
        int numGenes = parentA.geneLength;
        Double[] newGenes = new Double[numGenes];

        for(int gene = 0; gene < numGenes - 1; gene++){
            if(this.numGen.nextDouble() > 0.5){
                newGenes[gene] = parentA.genes[gene];
            }else{
                newGenes[gene] = parentB.genes[gene];
            }
        }

        return new Individual(newGenes, parentA.geneMin, parentA.geneMax, parentA.geneLength);
    }
}
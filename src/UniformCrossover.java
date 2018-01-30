import java.util.Random;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class UniformCrossover {
    private final Random numGen = new Random(System.currentTimeMillis());

    UniformCrossover(){}

    Individual performCrossover(Individual parentA, Individual parentB){
        return crossover(parentA, parentB);
    }
    private Individual crossover(Individual parentA, Individual parentB){
        int numGenes = parentA.getNumGenes();
        Double[] newGenes = new Double[numGenes];

        for(int gInd=0; gInd<numGenes; gInd++){
            if(this.numGen.nextDouble()>0.5){
                newGenes[gInd] = parentA.genes[gInd];
            }else{
                newGenes[gInd] = parentB.genes[gInd];
            }
        }

        return new Individual(numGenes,newGenes,parentA.geneMin,parentA.geneMax);
    }
}
package Main;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class Individual extends PopulationMember implements Comparable<Individual>{

    /**
     * Create new individual with initial genes, the bounds for the genes and the genome length
     */
    Individual(Double[] startGenes, Double[] geneMin, Double[] geneMax, int genomeLength){
        this.genes = startGenes;
        this.geneLength = genomeLength;
        this.geneMin = geneMin;
        this.geneMax = geneMax;
        this.fitness = 0f;//init fitness
    }

    /**
     * Returns length of genome
     */
    int getGenomeLength(){
        return this.genes.length;//return length of genome
    }

    /**
     * Set individual fitness
     */
    void setFitness(float fitness){
        this.fitness = fitness;//set fitness of individual
    }

    /**
     * Compare fitness to other population member (individual)
     */
    @Override
    public int compareTo(Individual o) {
        if(this.fitness == o.fitness){
            return 0;
        }
        return this.fitness > o.fitness ? -1 : 1;
    }
}
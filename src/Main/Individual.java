package Main;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class Individual extends PopulationMember implements Comparable<Individual>{

    Individual(Double[] startGenes, Double[] geneMin, Double[] geneMax, int genomeLength){
        super.genes = startGenes;
        super.geneLength = genomeLength;
        super.geneMin = geneMin;
        super.geneMax = geneMax;
        super.fitness = 0f;
    }

    int getGeneLength(){
        return this.genes.length;//return length of genome
    }

    void setFitness(float fitness){
        this.fitness = fitness;//set fitness of individual
    }

    @Override
    public int compareTo(Individual o) {
        if(this.fitness == o.fitness){
            return 0;
        }
        return this.fitness > o.fitness ? -1 : 1;
    }
}
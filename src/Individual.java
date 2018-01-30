import robocode.Robot;
/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class Individual implements Comparable<Individual>{
    Double[] geneMin, geneMax, genes;
    private float fitness = 0;
    private int numGenes;
    private Robot r;

    Individual(){}

    Individual(Robot robot){
        this.r = robot;
    }

    Individual(int memberLength, Double[] startGenes, Double[] gMin, Double[] gMax){
        this.geneMin = gMin;
        this.geneMax = gMax;
        this.numGenes = memberLength;
        this.genes = startGenes;
    }

    int getNumGenes(){return this.numGenes;}

    float getFitness(){
        return this.fitness;
    }

    void setFitness(float fitness){
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Individual o) {
        if(this.fitness == o.getFitness()){
            return 0;
        }
        return this.fitness > o.getFitness() ? 1 : -1;
    }
}
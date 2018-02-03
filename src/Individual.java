/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class Individual extends PopulationMember implements Comparable<Individual>{

    Individual(Double[] startGenes, Double[] geneMin, Double[] geneMax, int genomeLength){
        super.genes = startGenes;
        super.geneLength = genomeLength;
        super.geneMin = geneMin;
        super.geneMax = geneMax;
    }

    Double[] getGenes(){return this.genes;}

    int getGeneLength(){return this.genes.length;}

    float getFitness(){
        return this.fitness;
    }

    void setFitness(float fitness){
        this.fitness = fitness;
    }

    String getRobotName(){
        return "oliver.Bathurst";
    }

    @Override
    public int compareTo(Individual o) {
        if(this.fitness == o.getFitness()){
            return 0;
        }
        return this.fitness > o.getFitness() ? 1 : -1;
    }
}
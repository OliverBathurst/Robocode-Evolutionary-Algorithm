package Main;

import Framework.Mutator;

import java.util.Random;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class RandomMutator implements Mutator {
    private double percentage;
    private final Random random = new Random();

    RandomMutator(double mutationPercentage){this.percentage = mutationPercentage;}

    @Override
    public void mutate(Individual individual) {
        for(int i = 0; i < individual.getGeneLength(); i++){
            if(random.nextDouble() < percentage){
                individual.genes[i] =
                        individual.geneMin[i] + (individual.geneMax[i] - individual.geneMin[i]) * random.nextDouble();
            }
        }
    }
    @Override
    public void setMutationPercentage(double percent) {
        this.percentage = percent;
    }
}
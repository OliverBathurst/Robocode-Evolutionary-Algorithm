package Main;

import Framework.Mutator;
import java.security.SecureRandom;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class RandomMutator implements Mutator {
    private final SecureRandom random = new SecureRandom();
    private double percentage;

    RandomMutator(double mutationPercentage){this.percentage = mutationPercentage;}

    @Override
    public void mutate(Individual individual) {
        for(int i = 0; i < individual.getGenomeLength(); i++){
            if((random.nextDouble() * (100)) < percentage){
                individual.genes[i] = individual.geneMin[i] + (individual.geneMax[i] - individual.geneMin[i]) * random.nextDouble();//mutate that gene
            }
        }
    }
    @Override
    public void setMutationPercentage(double percent) {
        this.percentage = percent;
    }
}
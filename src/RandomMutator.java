import java.util.Random;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class RandomMutator implements Mutator {
    private float percentage;
    private final Random random = new Random();

    RandomMutator(){}

    @Override
    public void mutate(Individual individual) {
        for(int i = 0; i < individual.getGeneLength() - 1; i++){
            if(random.nextFloat() < percentage){
                individual.getGenes()[i] =
                        individual.geneMin[i] + (individual.geneMax[i] -  individual.geneMin[i]) * random.nextDouble();
            }
        }
    }
    @Override
    public void setMutationPercentage(float percent) {
        this.percentage = (percent/100);
    }
}
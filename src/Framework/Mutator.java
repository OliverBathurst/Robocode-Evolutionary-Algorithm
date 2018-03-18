package Framework;
import Main.Individual;

/**
 * Small mutation interface
 */
public interface Mutator {
    void mutate(Individual individual);
    void setMutationPercentage(double percent);
}

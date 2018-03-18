package Framework;
import Main.Individual;

/**
 * Simple crossover interface
 */

public interface Crossover {
    Individual crossover(Individual parentA, Individual parentB);
}
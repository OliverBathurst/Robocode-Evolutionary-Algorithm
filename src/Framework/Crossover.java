package Framework;
import Main.Individual;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public interface Crossover {
    Individual crossover(Individual parentA, Individual parentB);
}
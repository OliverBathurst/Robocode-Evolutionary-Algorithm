package Main;

/**
 * Created by Oliver on 31/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 * This class defines essential components of a population member
 */

abstract class PopulationMember {
    Double[] geneMin, geneMax, genes;
    int geneLength;
    float fitness = 0f;
}

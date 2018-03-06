package Framework;

import Main.Individual;

/**
 * Created by Oliver on 04/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public interface BattleMaker {
    void setOpponents(String[] opponents);
    void setNumOpponents(int number);
    void setRobocodeDir(String path);
    void setRobotName(String name);
    void setJarPath(String jarPath);
    void setRobotsDir(String path);
    void setPackageName(String packageName);
    String writeAndCompileIndividual(Individual individual);
    float getIndividualFitness(Individual individual);
    float getIndividualFitnessBatchRun(Individual individual);
    String stringifyOpponentArray(String[] opponents);
    String generateRobotCode(Individual individual);
}

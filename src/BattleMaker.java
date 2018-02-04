/**
 * Created by Oliver on 04/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

interface BattleMaker {
    void setOpponents(String[] opponents);
    void setNumOpponents(int number);
    void setRobocodeDir(String path);
    void setRobotName(String name);
    void setJarPath(String jarPath);
    void setRobotsDir(String path);
    void setPackageName(String packageName);
    String writeAndCompileIndividual(Individual individual);
    String generateRobotCode(Individual individual);
}

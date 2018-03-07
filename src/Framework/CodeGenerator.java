package Framework;

import Main.Individual;

public interface CodeGenerator {
    void setRobotName(String name);
    void setJarPath(String jarPath);
    void setRobotsDir(String path);
    void setPackageName(String packageName);
    String writeAndCompileIndividual(Individual individual);
    String getRobotName();
    String getJavaCode(Individual individual);
}

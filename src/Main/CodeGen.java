package Main;
import Framework.CodeGenerator;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by Oliver on 07/03/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 * Last updated 09/03/2018
 */

class CodeGen implements CodeGenerator{
    private String path = "C:\\robocode\\robots\\sample", jar = "C:\\robocode\\libs\\robocode.jar;",
            packageName = "sample", name = "OliverBathurstEA";
    private final String[] availableMethods = {"fireAtEnemy(e,", "ahead(", "back(","turnGunRight(", "turnGunLeft(",
            "turnLeft(", "turnRight(", "turnRadarLeft(", "turnRadarRight("};

    CodeGen(){}

    @Override
    public void setRobotName(String name){
        this.name = name;
    }
    @Override
    public void setRobotsDir(String path){
        this.path = path;
    }
    @Override
    public void setJarPath(String jarPath){
        this.jar = jarPath;
    }
    @Override
    public void setPackageName(String packageName){
        this.packageName = packageName;
    }

    @Override
    public String writeAndCompileIndividual(Individual individual){
        String filePath = path + "\\" + name + ".java";
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
            out.write(getJavaCode(individual));
            out.close();

            Process process = Runtime.getRuntime().exec("javac -cp " + jar + " " + filePath);
            process.waitFor();//block thread, this needs to finish
            if(process.exitValue() != 0) {
                System.out.println("Exited with value: " + process.exitValue());
            }
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        return packageName + "." + name;
    }

    @Override
    public String getRobotName() {
        return name;
    }

    @Override
    public String getJavaCode(Individual individual){
        return "package " + packageName + ";\n" +
                "import robocode.*;" + "\n" +
                "public class " + name + " extends Robot{\n\n" +
                "public void run(){\n" +
                "\twhile(true) {\n" +
                "\t\tturnGunRight(Double.POSITIVE_INFINITY);\n" +
                "\t}\n" +
                "}\n\n" +
                "public void onScannedRobot(ScannedRobotEvent e) {\n" +
                "\t" + availableMethods[(individual.genes[8]).intValue()] + individual.genes[0] +");\n" +
                "\t" + availableMethods[(individual.genes[9]).intValue()] + individual.genes[1] +");\n" +
                "\t" + availableMethods[(individual.genes[10]).intValue()] + individual.genes[2] +");\n" +
                "}\n\n" +
                "public void onHitByBullet(HitByBulletEvent e){\n" +
                "\t" + availableMethods[(individual.genes[11]).intValue()] + individual.genes[3] +");\n" +
                "\t" + availableMethods[(individual.genes[12]).intValue()] + individual.genes[4] +");\n" +
                "}\n\n" +
                "public void onHitWall(HitWallEvent e) {\n" +
                "\t" + availableMethods[(individual.genes[13]).intValue()] + individual.genes[5] +");\n" +
                "}\n\n" +
                "public void onHitRobot(HitRobotEvent e) {\n" +
                "\t" + availableMethods[(individual.genes[14]).intValue()] + individual.genes[6] +");\n" +
                "}\n\n" +
                "public void fireAtEnemy(Object e, Double d) {\n" +
                "\tif(e instanceof ScannedRobotEvent){\n" +
                    "\t\tif(!((ScannedRobotEvent) e).getName().contains(getName())){\n" +
                        "\t\t\tfire(d);\n"+
                    "\t\t}\n" +
                "\t}else if(e instanceof HitByBulletEvent){\n" +
                    "\t\tif(!((HitByBulletEvent) e).getName().contains(getName())){\n" +
                        "\t\t\tfire(d);\n"+
                    "\t\t}\n" +
                "\t}else if(e instanceof HitRobotEvent){\n" +
                    "\t\tif(!((HitRobotEvent) e).getName().contains(getName())){\n" +
                        "\t\t\tfire(d);\n"+
                    "\t\t}\n" +
                "\t}else{\n" +
                    "\t\tfire(d);\n" +
                "\t}" +
                "\n}\n}";
    }

    void writeIndividualToFile(Individual individual) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Java File", ".java"));

        if (fileChooser.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
            try {
                PrintWriter pw = new PrintWriter(new File(fileChooser.getSelectedFile().getAbsolutePath() + ".java"));
                pw.write(getJavaCode(individual));
                pw.close();
            } catch (Exception ignored) {}
        }
    }
}

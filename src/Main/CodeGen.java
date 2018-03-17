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
            packageName = "sample", currentName = "OliverBathurstEA";
    private final String[] availableMethods = {"fireAtEnemy(e,", "ahead(", "back(","turnGunRight(", "turnGunLeft(",
            "turnLeft(", "turnRight(", "turnRadarLeft(", "turnRadarRight("};//available methods, their insertion into robot's Java file determined by gene

    CodeGen(){}

    @Override
    public void setRobotName(String name){
        this.currentName = name;
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
    public String getRobotName() {
        return currentName;
    }

    /**
     * Write and compile an individual, return path e.g. sample.OliverBathurst
     */
    @Override
    public String writeAndCompileIndividual(Individual individual){//set default name
        currentName = "OliverBathurstEA";
        compile(individual, path + "\\" + currentName + ".java");//compile and write to file
        return packageName + "." + currentName;
    }
    /**
     * Write and compile an individual with custom name, return path e.g. sample.CustomName
     */
    String writeAndCompileIndividual(Individual individual, String customName){
        currentName = customName;
        compile(individual, path + "\\" + currentName + ".java");
        return packageName + "." + currentName;
    }

    /**
     * Write a given individual to a Java file
     */
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

    /**
     * Compile individual using file path
     */
    private void compile(Individual individual, String filePath){
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
    }

    /**
     * Returns a set of methods with values, number of methods to insert into a call-back (event), the methods to include,
     * and the values for each method are determined by genes
     */
    private String event(Individual individual, int index, int startIndex, int startIndexValue){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < individual.genes[index].intValue(); i++){
            Long roundedGene = Math.round(individual.genes[startIndex]);//round the gene (between 1-8)
            Double doubleRounded = roundedGene.doubleValue();//get double value of rounded gene (should be now .0)
            //get int value (we round first because intValue() ALWAYS rounds down (which is incorrect
            sb.append("\t").append(availableMethods[doubleRounded.intValue()]).append(individual.genes[startIndexValue])
                    .append(");\n");
            startIndex++;
            startIndexValue++;
        }
        return sb.toString();
    }


    /**
     * Generate Java code with an individual
     */
    @Override
    public String getJavaCode(Individual individual){
        return "package " + packageName + ";\n" +
                "import robocode.*;" + "\n" +
                "public class " + currentName + " extends AdvancedRobot{\n\n" +
                "public void run(){\n" +
                "\twhile(true) {\n" +
                "\t\tturnGunRight(Double.POSITIVE_INFINITY);\n" +
                "\t}\n" +
                "}\n\n" +
                "public void onScannedRobot(ScannedRobotEvent e) {\n" +
                    event(individual, 0, 4, 40) +
                "}\n\n" +
                "public void onHitByBullet(HitByBulletEvent e){\n" +
                    event(individual, 1, 13, 49) +
                "}\n\n" +
                "public void onHitWall(HitWallEvent e) {\n" +
                    event(individual, 2, 22, 58) +
                "}\n\n" +
                "public void onHitRobot(HitRobotEvent e) {\n" +
                    event(individual, 3, 31, 67) +
                "}\n\n" +
                "public void fireAtEnemy(Object e, Double d) {\n" +
                "\tif(e instanceof ScannedRobotEvent){\n" +
                "\t\tString opponentName = ((ScannedRobotEvent) e).getName();\n" +
                "\t\tif(!opponentName.contains(\"Clone\") && !opponentName.contains(\"OliverBathurstEA\")){\n" +
                "\t\t\tfire(d);\n"+
                "\t\t}\n" +
                "\t}else if(e instanceof HitByBulletEvent){\n" +
                "\t\tString opponentName = ((HitByBulletEvent) e).getName();\n" +
                "\t\tif(!opponentName.contains(\"Clone\") && !opponentName.contains(\"OliverBathurstEA\")){\n" +
                "\t\t\tfire(d);\n"+
                "\t\t}\n" +
                "\t}else if(e instanceof HitRobotEvent){\n" +
                "\t\tString opponentName = ((HitRobotEvent) e).getName();\n" +
                "\t\tif(!opponentName.contains(\"Clone\") && !opponentName.contains(\"OliverBathurstEA\")){\n" +
                "\t\t\tfire(d);\n"+
                "\t\t}\n" +
                "\t}else{\n" +
                "\t\tfire(d);\n" +
                "\t}" +
                "\n}\n}";
    }
}

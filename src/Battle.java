import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by Oliver on 03/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class Battle implements BattleMaker{
    private String[] opponents = new String[] {"sample.SittingDuck" ,"sample.SittingDuck","sample.SittingDuck","sample.SittingDuck","sample.SittingDuck"};
    private String path = "C:\\robocode\\robots\\sampleex", robocodePath = "C:/Robocode", jar = "C:\\robocode\\libs\\robocode.jar;",
            packageName = "sampleex", name = "OliverGP";
    private int opponentsSize = 5;
    private final boolean visible;

    Battle(boolean visible){
        this.visible = visible;
    }
    @Override
    public void setOpponents(String[] opponents){
        this.opponents = opponents;
        this.opponentsSize = opponents.length;
    }
    @Override
    public void setNumOpponents(int number) {
        opponentsSize = (number > opponents.length) ? opponents.length : number;
    }
    @Override
    public void setRobocodeDir(String path){
        this.robocodePath = path;
    }
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

    float getIndividualFitness(Individual individual){
        int counter = 0;
        float returnFitness = 0;

        String robotPath = writeAndCompileIndividual(individual);

        for (String opponent : opponents) {//fight against each opponent
            if(counter < opponentsSize) {
                System.out.println("Running battle between: " + name + " and " + opponent);
                BattleObserver battleObserver = new BattleObserver();
                RobocodeEngine engine = new RobocodeEngine(new File(robocodePath));//Run from C:/Robocode
                engine.addBattleListener(battleObserver);
                engine.setVisible(visible);
                BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
                RobotSpecification[] selectedRobots = engine.getLocalRepository(robotPath + ", " + opponent);
                engine.runBattle(new BattleSpecification(1, battlefield, selectedRobots), true); // waits till the battle finishes
                engine.close();

                BattleResults[] battleResults = battleObserver.getResults();

                int eaIndex = 0, botIndex = 1;//assume
                if (!battleResults[0].getTeamLeaderName().equals(name)) {//if not at index 0, flip indexes
                    eaIndex = 1;
                    botIndex = 0;
                }
                int eaScore = battleResults[eaIndex].getScore();//get EA score
                int botScore = battleResults[botIndex].getScore();//get Bot score

                returnFitness += ((eaScore) / (eaScore + botScore));// get fitness for round

                counter++;
            }
        }
        return returnFitness;
    }

    class BattleObserver extends BattleAdaptor {

        robocode.BattleResults[] results;

        public void onBattleCompleted(BattleCompletedEvent e){
            results = e.getIndexedResults();
        }

        public void onBattleError(BattleErrorEvent e){
            System.out.println("Error running battle: " + e.getError());
        }

        BattleResults[] getResults(){
            return results;
        }
    }

    @Override
    public String writeAndCompileIndividual(Individual individual){
        String filePath = path + "\\" + name +".java";
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
            out.write(generateRobotCode(individual));
            out.close();

            String command = "javac -cp " + jar + " " + filePath;
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            if(process.exitValue() != 0) {
                System.out.println(command + "Exited with value: " + process.exitValue());
            }
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        return packageName + "." + name;
    }

    @Override
    public String generateRobotCode(Individual individual) {
        return "package " + packageName + ";\n" +
                "import robocode.*;" + "\n" +
                "public class " + name + " extends AdvancedRobot{\n" +
                "public void run(){\n" +
                "while(true) {\n" +
                "turnGunRight(Double.POSITIVE_INFINITY);\n" +
                "}\n" +
                "}\n\n" +

                "public void onScannedRobot(ScannedRobotEvent e) {\n" +
                "ahead(" + individual.genes[0] + ");\n" +
                "turnRight("+ individual.genes[1] +");\n" +
                "turnGunRight("+ individual.genes[2] +");\n" +
                "turnRadarRight("+ individual.genes[3] +");\n" +
                "fire("+ individual.genes[4] +");\n" +
                "}\n\n" +
                "public void onHitByBullet(HitByBulletEvent e){\n" +
                "turnRadarRight("+ individual.genes[4] +");\n" +
                "}\n\n" +
                "public void onHitWall(HitWallEvent e) {\n" +
                "back(20);\n" +
                "ahead("+ individual.genes[4] +");\n" +
                "}\n}";
    }
}

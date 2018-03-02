package Main;

import Framework.BattleMaker;
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

class Battle implements BattleMaker {
    private String[] opponents = new String[] {"sample.SittingDuck" ,"sample.Corners","sample.Crazy"
            ,"sample.Fire","sample.RamFire", "sample.SpinBot", "sample.Target", "sample.VelociRobot", "sample.Walls"};
    private String path = "C:\\robocode\\robots\\sample", robocodePath = "C:/Robocode", jar = "C:\\robocode\\libs\\robocode.jar;",
            packageName = "sample", name = "OliverBathurstEA";
    private int opponentsSize = 9;
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
    @Override
    public float getIndividualFitness(Individual individual){
        float returnFitness = 0.0f;

        String robotPath = writeAndCompileIndividual(individual);

        for (int i = 0; i < opponentsSize; i++) {//fight against each opponent
            System.out.println("Running battle between: " + name + " and " + opponents[i]);
            BattleObserver battleObserver = new BattleObserver();
            RobocodeEngine engine = new RobocodeEngine(new File(robocodePath));//Run from C:/Robocode
            engine.addBattleListener(battleObserver);
            engine.setVisible(visible);
            BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
            RobotSpecification[] selectedRobots = engine.getLocalRepository(robotPath + ", " + opponents[i]);
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

            int denominator = (eaScore + botScore);
            if(denominator != 0) {
                returnFitness = (returnFitness + (eaScore / denominator))/2;//compute average fitness after each round
            }else{
                returnFitness = eaScore;//eaScore must be 0, eaScore + botScore = 0, therefore both are 0.
            }
        }

        System.out.println("Fitness of: " + returnFitness);
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
    public String stringifyOpponentArray(String[] opponents){
        StringBuilder opponentString = new StringBuilder();
        for(String s: opponents){
            opponentString.append(s).append(",");
        }
        return opponentString.toString();
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
                "public class " + name + " extends Robot{\n" +
                "public void run(){\n" +
                "while(true) {\n" +
                "turnGunRight(Double.POSITIVE_INFINITY);\n" +
                "}\n" +
                "}\n\n" +

                "public void onScannedRobot(ScannedRobotEvent e) {\n" +
                "fire("+ individual.genes[0] +");\n" +
                "ahead("+ individual.genes[1] +");\n" +
                "turnRight("+ individual.genes[2] +");\n" +
                "turnGunRight("+ individual.genes[3] +");\n" +
                "}\n\n" +
                "public void onHitByBullet(HitByBulletEvent e){\n" +
                "turnRadarRight("+ individual.genes[4] +");\n" +
                "turnRight("+ individual.genes[5] +");\n" +
                "ahead("+ individual.genes[6] +");\n" +
                "}\n\n" +
                "public void onHitWall(HitWallEvent e) {\n" +
                "back(" + individual.genes[7] + ");\n" +
                "}\n}";
    }
}

import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Created by Oliver on 03/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class Battle {
    private String[] opponents = new String[] {"sample.SittingDuck" ,"sample.SittingDuck","sample.SittingDuck","sample.SittingDuck","sample.SittingDuck"};
    private String path = "C:\\robocode\\robots\\sampleex";
    private String JARS = "C:\\robocode\\libs\\robocode.jar;";
    private String packageName = "sampleex";
    private String name = "OliverGP";

    private Individual individual;
    private boolean visible;

    Battle(boolean visible, Individual individual){
        this.visible = visible;
        this.individual = individual;
    }
    float getIndividualFitness(){
        BattleObserver battleObserver = new BattleObserver();
        RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode")); // Run from C:/Robocode
        engine.addBattleListener(battleObserver);
        engine.setVisible(visible);
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600

        String customRobot = compile();
        RobotSpecification[] selectedRobots = engine.getLocalRepository(customRobot + ", sample.Corners");

        BattleSpecification battleSpec = new BattleSpecification(1, battlefield, selectedRobots);
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        engine.close();

        float returnVal = 0;
        for(BattleResults BR: battleObserver.getResults()){
            returnVal = BR.getScore()/5;
        }
        return returnVal;
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

    private String getCode(){
        return "package " + packageName + ";\n" +
        "import robocode.*;" + "\n" +
        "public class " + name + " extends AdvancedRobot{\n" +
            "public void run(){\n" +
                "while(true) {\n" +
                    "turnGunRight(Double.POSITIVE_INFINITY);\n" +
                "}\n" +
            "}\n\n" +

            "public void onScannedRobot(ScannedRobotEvent e) {\n" +
                "ahead(" + individual.getGenes()[0] + ");\n" +
                "turnRight("+ individual.getGenes()[1] +");\n" +
                "turnGunRight("+ individual.getGenes()[2] +");\n" +
                "turnRadarRight("+ individual.getGenes()[3] +");\n" +
                "fire("+ individual.getGenes()[4] +");\n" +
            "}\n\n" +
            "public void onHitByBullet(HitByBulletEvent e){\n" +
                "turnRadarRight("+ individual.getGenes()[4] +");\n" +
            "}\n\n" +
            "public void onHitWall(HitWallEvent e) {\n" +
                "back(20);\n" +
                "ahead("+ individual.getGenes()[4] +");\n" +
            "}\n}";
    }
    String compile(){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(path + "\\" + name +".java"));
            out.write(getCode());
            out.close();
        }catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }

        // Compile code
        try {
            execute("javac -cp " + JARS + " " + path + "\\" + name + ".java");
        }catch(Exception e){
            e.printStackTrace();
        }
        return packageName + "." + name;
    }
    private static void execute(String command) throws Exception{
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        if(process.exitValue() != 0)
            System.out.println(command + "exited with value " + process.exitValue());
    }
}

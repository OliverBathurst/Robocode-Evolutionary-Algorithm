import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;

/**
 * Created by Oliver on 03/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class Battle {
    private String[] opponents = new String[] {"sample.SittingDuck" ,"sample.SittingDuck","sample.SittingDuck","sample.SittingDuck","sample.SittingDuck"};
    private boolean visible;
    private String robotName;

    Battle(String name, boolean visible){
        this.robotName = name;
        this.visible = visible;
    }
    float getIndividualFitness(){
        BattleObserver battleObserver = new BattleObserver();
        RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode")); // Run from C:/Robocode
        engine.addBattleListener(battleObserver);
        engine.setVisible(false);
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
        RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.RamFire, sample.Corners");
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
}

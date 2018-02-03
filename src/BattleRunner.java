import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;

import java.util.ArrayList;

/**
 * Created by Oliver on 03/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class BattleRunner {
    RobocodeEngine engine;
    BattlefieldSpecification battlefield;
    BattleObserver battleObserver;

    BattleRunner(){
        engine = new RobocodeEngine(new java.io.File("C:/robocode"));
        battleObserver = new BattleObserver();
        engine.addBattleListener(battleObserver);
        engine.setVisible(true);
        battlefield = new BattlefieldSpecification(800, 600);
    }

    float[] runBatchWithSamples(ArrayList<Individual> bots, ArrayList<Individual> samples, int rounds){
        engine = new RobocodeEngine(new java.io.File("C:/robocode"));
        String bot, opponent;
        float fitnesses[] = new float[bots.size()];
        BattleResults[] results;

        for(int i = 0; i < bots.size(); i++) {
            float fitnessScore = 0;
            for (Individual sample : samples) {
                bot = bots.get(i).getRobotName();
                opponent = sample.getRobotName();

                RobotSpecification[] selectedBots = engine.getLocalRepository(bot + ", " + opponent);
                BattleSpecification battleSpec = new BattleSpecification(rounds, battlefield, selectedBots);
                engine.runBattle(battleSpec, true);

                results = battleObserver.getResults();
                int myBot = (results[0].getTeamLeaderName().equals(bots.get(i).getRobotName()) ? 0 : 1);
                int opBot = (myBot == 1 ? 0 : 1);
                int botScore = results[myBot].getScore();

                float totalScore = botScore + results[opBot].getScore();
                float roundFitness = (botScore) / (totalScore);

                fitnessScore += roundFitness;
            }
            fitnesses[i] = fitnessScore / samples.size();    // take average of each round score
        }
        return fitnesses;
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

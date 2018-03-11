package Main;
import Framework.BattleMaker;
import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Oliver on 03/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class Battle implements BattleMaker {
    //private String[] opponents = new String[] {"sample.SittingDuck" ,"sample.Corners","sample.Crazy","sample.Fire","sample.RamFire", "sample.SpinBot", "sample.Target", "sample.VelociRobot", "sample.Walls"};
    private final ArrayList<String> newOpponents = new ArrayList<>();
    private String[] opponents = new String[] {"sample.Crazy"};
    private final String robocodePath = "C:/Robocode";
    private int opponentsSize = 9, helperBotsNumber = 0;
    private final CodeGen code = new CodeGen();
    private final boolean visible;

    /**
     * init with Battle visibility
     */
    Battle(boolean visible){
        this.visible = visible;
    }

    /**
     * init with a number of helper clones
     */
    Battle(boolean visible, int numberHelpers){
        this.visible = visible;
        this.helperBotsNumber = numberHelpers;
    }

    /**
     * Set opponents via string array
     */
    @Override
    public void setOpponents(String[] opponents){
        this.opponents = opponents;
        this.opponentsSize = opponents.length;
    }

    /**
     * Sets number of opponents for battle
     */
    @Override
    public void setNumOpponents(int number) {
        opponentsSize = (number > opponents.length) ? opponents.length : number;
    }

    /**
     * Gets fitness of individual via multiple battles
     */
    @Override
    public float getIndividualFitness(Individual individual){
        float returnFitness = 0.0f;

        String robotPath = code.writeAndCompileIndividual(individual);
        String helperBots = generateHelperBotStrings(individual);

        for (int i = 0; i < (opponentsSize > opponents.length ? opponents.length : opponentsSize); i++) {//fight against each opponent
            System.out.println("Running battle between: " + code.getRobotName() + " and " + opponents[i]);
            BattleObserver battleObserver = new BattleObserver();
            RobocodeEngine engine = new RobocodeEngine(new File(robocodePath));//Run from C:/Robocode
            engine.addBattleListener(battleObserver);
            engine.setVisible(visible);
            engine.runBattle(new BattleSpecification(1, new BattlefieldSpecification(800, 600),
                    engine.getLocalRepository(robotPath + ", " + opponents[i] + helperBots)), true); // waits till the battle finishes
            engine.close();

            BattleResults[] battleResults = battleObserver.getResults();
            float eaScore = battleResults[1].getScore(), botScore = battleResults[0].getScore();//assume

            if (battleResults[0].getTeamLeaderName().contains(code.getRobotName())) {//if not at index 0, flip indexes
                eaScore = battleResults[0].getScore();
                botScore = battleResults[1].getScore();
            }

            float denominator = (eaScore + botScore);
            if(denominator != 0) {
                returnFitness += (eaScore / denominator);//compute average fitness after each round
            }else{
                returnFitness += eaScore;//eaScore must be 0, eaScore + botScore = 0, therefore both are 0.
            }
        }
        returnFitness = returnFitness/opponentsSize;//average fitness over all battles
        System.out.println("Calculated fitness of: " + returnFitness);
        return returnFitness;
    }

    @Override
    public float getIndividualFitnessBatchRun(Individual individual){
        float eaFitness = 0.0f, botsFitness = 0.0f, returnFitness;
        int opponentsSize = opponents.length;

        generateHelpers(individual);

        BattleObserver battleObserver = new BattleObserver();
        RobocodeEngine engine = new RobocodeEngine(new File(robocodePath));//Run from C:/Robocode
        engine.addBattleListener(battleObserver);

        engine.setVisible(visible);
        engine.runBattle(new BattleSpecification(1, new BattlefieldSpecification(800, 600),
                engine.getLocalRepository(code.writeAndCompileIndividual(individual) + ", "
                        + stringifyOpponentArray(opponents))), true); // waits till the battle finishes
        engine.close();

        for(BattleResults br: battleObserver.getResults()){
            if(br.getTeamLeaderName().contains(code.getRobotName())){
                eaFitness = br.getScore();
            }else{
                if(!br.getTeamLeaderName().contains("Clone")) {//don't count friendly scores
                    botsFitness += br.getScore();
                }
            }
        }
        float avgBotFitness = botsFitness/opponentsSize;
        float denominator = (eaFitness + avgBotFitness);
        //System.out.println("Average bot fitness: " + avgBotFitness);
        //System.out.println("EA bot fitness: " + eaFitness);

        if(denominator != 0) {
            returnFitness = (eaFitness / denominator);//compute average fitness after each round
        }else{
            returnFitness = eaFitness;//eaScore must be 0, eaScore + botScore = 0, therefore both are 0.
        }

        System.out.println("Calculated fitness: " + returnFitness);
        return returnFitness;
    }

    /**
     * Observer to return results of a battle
     */
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

    /**
     * Creates a string from the opponent array for use in battle setup
     */
    @Override
    public String stringifyOpponentArray(String[] opponents){
        StringBuilder opponentString = new StringBuilder();
        for(String s: opponents){
            opponentString.append(s).append(",");
        }
        return opponentString.toString();
    }

    /**
     * Generates N helper clones of individual and adds them to array
     */
    private void generateHelpers(Individual individual){
        if(helperBotsNumber > 0) {
            newOpponents.clear();//clear
            newOpponents.addAll(Arrays.asList(opponents));//add existing opponents

            for (int i = 0; i < helperBotsNumber; i++) {
                newOpponents.add(code.writeAndCompileIndividual(individual, "Clone" + i));//generate a new clone and add to opponents array list
            }
            opponents = newOpponents.toArray(new String[newOpponents.size()]);//convert array list to array and rebind opponents array
        }
    }

    /**
     * Generates string of helper bots for use in battle initialisation e.g. sample.Clone1, sample.Clone2...
     */
    private String generateHelperBotStrings(Individual individual){
        StringBuilder toReturn = new StringBuilder();
        if(helperBotsNumber > 0) {//if requested
            newOpponents.clear();//clear
            newOpponents.addAll(Arrays.asList(opponents));//add existing opponents
            for (int i = 0; i < helperBotsNumber; i++) {
                toReturn.append(", ").append(code.writeAndCompileIndividual(individual, "Clone" + i));//gen clone with new name
            }
        }
        return toReturn.toString();//return the string
    }
}
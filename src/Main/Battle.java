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
 * Battle class, responsible for evaluating/returning fitness of robots
 */
class Battle implements BattleMaker {
    private final BattlefieldSpecification battleSpec = new BattlefieldSpecification(800, 600);
    private final RobocodeEngine engine = new RobocodeEngine(new File("C:/Robocode"));//Run from C:/Robocode
    private final BattleObserver battleObserver = new BattleObserver();//for observing results
    private final ArrayList<String> newOpponents = new ArrayList<>();
    private final CodeGen code = new CodeGen();//for generating Java code
    private String[] opponents = new String[] {"sample.SittingDuck,sample.SittingDuck,sample.SittingDuck,sample.SittingDuck,sample.SittingDuck"};
    private int helperBotsNumber = 0;//default helper bot number
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
    }

    /**
     * Sets number of opponents for battle
     */
    @Override
    public void setNumOpponents(int number) {}

    /**
     * Gets fitness of individual via multiple battles
     */
    @Override
    public float getIndividualFitness(Individual individual){
        return 0.0f;//method not needed anymore, opt for batch run
    }

    /**
     * Makes a battle with multiple opponents/helpers
     */
    @Override
    public float getIndividualFitnessBatchRun(Individual individual){
        generateHelpers(individual); //generate clones

        engine.addBattleListener(battleObserver);//set battle listener
        engine.setVisible(visible);//set visibility
        BattleSpecification battleSpecification = new BattleSpecification(1, battleSpec,
                engine.getLocalRepository(code.writeAndCompileIndividual(individual) + ", "
                        + stringifyOpponentArray(opponents)));
        engine.runBattle(battleSpecification, initialPositions(battleSpecification, opponents.length), true); // waits till the battle finishes
        engine.close();

        return getFitness(battleObserver.getResults());//return fitness
    }

    /**
     * Gets fitness of an individual based on battle results
     */
    @Override
    public float getFitness(BattleResults[] battleResults) {
        float returnFitness = 0.0f;
        for(BattleResults br: battleResults){
            if(br.getTeamLeaderName().contains("OliverBathurstEA")){//if it's the robot being evaluated
                returnFitness = br.getScore();//set score
                break;//break out of loop
            }
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

    /**
     * Sets initial positions for robots (EXTREMELY IMPORTANT)
     */
    private String initialPositions(BattleSpecification battleSpecification, int numberOpponents){
        StringBuilder initialPositions = new StringBuilder();//init string builder
        ArrayList<Integer> positions = new ArrayList<>();//store positions (alternating sides of the battlefield)

        for (int i = 0; i < numberOpponents; i++) {
            positions.add(i);//add two of the same index for alternating sides of battlefield
            positions.add(i);//(one for each side) such that both are the same distance from their sides (fair fight)
        }

        boolean switchSides = false;// swap sides each time (iteration)
        //ITERATE OVER ALL POSITIONS
        for(int i: positions){
            if(!switchSides) {
                initialPositions.append("(").append(Integer.toString(battleSpecification.getBattlefield().getWidth() / (numberOpponents - i))).append(",");
                switchSides = true;//switch sides
            }else{
                initialPositions.append("(").append(Integer.toString(battleSpecification.getBattlefield().getWidth() - (battleSpecification.getBattlefield().getWidth() / (numberOpponents - i)))).append(",");
                switchSides = false;
            }
            initialPositions.append(Integer.toString(battleSpecification.getBattlefield().getHeight() / 2)).append(",");
            initialPositions.append(Integer.toString(360)).append(",").append(Integer.toString(0)).append("),");
        }
        return initialPositions.toString();//return positions as a string
    }
}
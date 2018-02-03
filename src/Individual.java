import robocode.Robot;
import java.util.Random;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class Individual extends PopulationMember implements Comparable<Individual>{
    private String[] robotNames = new String[]{"sample.RamFire", "sample.Corners", "sample.Crazy", "sample.VelociRobot", "sample.TrackFire", "sample.Walls"};
    private Random rand = new Random();

    private String robotName;
    private Robot r;

    Individual(Robot robot, Double[] startGenes, Double[] geneMin, Double[] geneMax, int genomeLength){
        this.r = robot;
        this.robotName = randomRobotName();
        super.genes = startGenes;
        super.geneLength = genomeLength;
        super.geneMin = geneMin;
        super.geneMax = geneMax;
    }

    Robot getRobot(){
        return r;
    }

    Double[] getGenes(){return this.genes;}

    int getGeneLength(){return this.genes.length;}

    float getFitness(){
        return this.fitness;
    }

    void setFitness(float fitness){
        this.fitness = fitness;
    }

    private String randomRobotName(){
        return robotNames[rand.nextInt(robotNames.length)];
    }

    String getRobotName(){
        return robotName;
    }

    @Override
    public int compareTo(Individual o) {
        if(this.fitness == o.getFitness()){
            return 0;
        }
        return this.fitness > o.getFitness() ? 1 : -1;
    }
}
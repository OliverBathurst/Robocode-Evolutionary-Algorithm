import robocode.Robot;

class Individual implements Comparable<Individual>{
    private float fitness = 0;
    private Robot r;

    Individual(Robot robot){
        this.r = robot;
    }

    float getFitness(){
        return 1;
    }

    Robot getIndividual(){
        return r;
    }

    void setFitness(float fitness){
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Individual o) {
        if(this.fitness == o.getFitness()){
            return 0;
        }
        return this.fitness > o.getFitness() ? 1 : -1;
    }
}

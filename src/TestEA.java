public class TestEA {
    private float targetFitness;
    private int generations, populationSize;

    TestEA(int populationSize, float targetFitness){
        this.populationSize = populationSize;
        this.targetFitness = targetFitness;
    }

    void run(){
        Population p = new Population(populationSize);
        p.generatePopulation();

    }
}

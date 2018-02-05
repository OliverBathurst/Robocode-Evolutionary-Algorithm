/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class Main{

    public static void main(String[] args) {
        TestEA testEA = new TestEA();
        testEA.init(10,false, new NewPopulation(4),
                new CustomEvaluator(), new RandomMutator(3), new TournamentSelection(), new GreedySelection(), new UniformCrossover());
        Individual i = testEA.run();
        System.out.println("Best individual: " + i.getFitness());
    }
}

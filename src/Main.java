/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class Main {
    public static void main(String[] args) {
        TestEA newEA = new TestEA();
        newEA.init(256,3, false, new NewPopulation(256),
                new CustomEvaluator(), new RandomMutator(3), new TournamentSelection(), new GreedySelection(), new UniformCrossover());
        newEA.run();
    }
}

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class Main {
    public static void main(String[] args) {
        TestEA newEA = new TestEA();
        newEA.init(256, 100, 3,
                new CustomEvaluator(), new RandomMutator(), new TournamentSelect());
        newEA.run();
    }
}

package Main;

class Main {

    public static void main(String[] args) {
        run();
    }

    private static void run(){
        TestEA testEA = new TestEA();
        testEA.init(10,false, new NewPopulation(10),
                new CustomEvaluator(), new RandomMutator(50), new TournamentSelection(), new GreedySelection(), new UniformCrossover());
        testEA.setLogger(new Log());
        testEA.run();//run once
        //testEA.getLogger().writeToFile();
    }
}

class Main {
    public static void main(String[] args) {
        TestEA newEA = new TestEA();
        newEA.init(256, 100, 3,
                new CustomEvaluator(), new RandomMutator(), new TornamentSelect());
        newEA.run();
    }
}

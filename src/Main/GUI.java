package Main;
import Framework.Logger;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.io.OutputStream;
import java.io.PrintStream;

class GUI {
    private JPanel panel1;
    private JButton runButton;
    private JButton stopButton;
    private JButton openGraphButton;
    private JTextArea outputText;
    private JButton write;
    private JButton printBest;
    private JSlider popSize;
    private JLabel populationLabel;
    private JSlider generationSlider;
    private JLabel genLimit;
    private JCheckBox generationLimit;
    private JScrollPane textBox;
    private JButton writeBest;
    private JButton printTotal;
    private JButton writeTotalBest;
    private JLabel mutationRate;
    private JTextField mutationRateBox;
    private JCheckBox battleVisible;
    private JLabel numHelpersLabel;
    private JSlider numHelpersSlider;
    private JSlider sliderCrossoverRate;
    private JLabel crossoverRate;
    private JCheckBox elitismCheckBox;
    private TestEA testEA;
    private final CodeGen gen = new CodeGen();

    private GUI(){
        runButton.addActionListener(e -> run());
        stopButton.addActionListener(e -> stop());
        openGraphButton.addActionListener(e -> openGraphView());
        write.addActionListener(e -> writeToFile());
        printBest.addActionListener(e -> printCurrentBest());
        popSize.addChangeListener(e -> populationLabel.setText("Population Size: " + popSize.getValue()));
        generationSlider.addChangeListener(e -> genLimit.setText("Generation Limit: " + generationSlider.getValue()));
        numHelpersSlider.addChangeListener(e -> numHelpersLabel.setText("Number of Helpers: " + numHelpersSlider.getValue()));
        sliderCrossoverRate.addChangeListener(e -> crossoverRate.setText("Crossover Rate: " + sliderCrossoverRate.getValue()));
        writeBest.addActionListener(e -> writeBestIndividual());
        printTotal.addActionListener(e -> printTotalBest());
        writeTotalBest.addActionListener(e -> writeTotalBestToFile());
        ((DefaultCaret) outputText.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        PrintStream out = new PrintStream(new outputPrintStream(outputText));
        System.setOut(out);
        System.setErr(out);
    }

    /**
     * Custom output stream for printing to GUI box
     */
    private class outputPrintStream extends OutputStream  {
        private final JTextArea jta;
        outputPrintStream(JTextArea j){
            this.jta = j;
        }
        @Override
        public void write(int b) {
            jta.append((String.valueOf((char)b)));
            jta.setCaretPosition(jta.getDocument().getLength());
        }
    }

    public static void main(String[] args){
        JFrame j = new JFrame("Robocode Evolutionary Algorithm");
        j.setContentPane(new GUI().panel1);
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        j.pack();
        j.setVisible(true);
    }

    /**
     * Sets up the environment and starts the main evolutionary thread
     */
    private void run(){
        setupEnvironment();
        testEA.start();//start thread
    }

    /**
     * Terminates the run after the generation is finished
     */
    private void stop(){
        System.out.println("\nStopping after run...");
        if(testEA != null){
            testEA.forceTerminate();
        }else{
            System.out.println("\nNull Instance");
        }
    }

    /**
     * Prints the global bes solution over the run
     */
    private void printTotalBest(){
        if(testEA != null){
            Individual i = testEA.getTotalBest();
            if(i != null) {
                System.out.println("\nGlobal Best: " + i.fitness + "\n");
                System.out.println(testEA.printGenome(i));
            }
        }else{
            System.out.println("\nNull Instance");
        }
    }

    /**
     * Prints the current fittest individual
     */
    private void printCurrentBest(){
        Individual i = testEA.getBest();
        if(i != null) {
            System.out.println("\nBest: " + i.fitness + "\n");
            System.out.println(testEA.printGenome());
        }else{
            System.out.println("\nNull individual");
        }
    }

    /**
     * Opens graph view with log, can only be launched once per app launch
     */
    private void openGraphView(){
        if(testEA != null) {
            Logger log = testEA.getLogger();
            if (log != null) {
                new GraphView(log).launch();
            } else {
                System.out.println("\nNull log");
            }
        }else{
            System.out.println("\nNull Instance");
        }
    }

    /**
     * Calls the save to file dialog (writes generations and fitness per generation
     */
    private void writeToFile(){
        testEA.getLogger().writeToFile();
    }

    /**
     * Writes the best individual to robot file
     */
    private void writeTotalBestToFile(){
        if(testEA != null){
            Individual totalBest = testEA.getTotalBest();
            if(totalBest != null){
                gen.writeIndividualToFile(totalBest);//use code gen to write individual to file
            }
        }else{
            System.out.println("\nNull Instance");
        }
    }

    /**
     * Writes current best to file (opens file save dialog)
     */
    private void writeBestIndividual() {
        if(testEA != null){
            Individual best = testEA.getBest();
            if(best != null){
                gen.writeIndividualToFile(best);
            }else{
                System.out.println("\nNull Individual");
            }
        }else{
            System.out.println("\nNull Instance");
        }
    }

    /**
     * Sets up values based on GUI options, initialises evolutionary algorithm
     */
    private void setupEnvironment(){
        int crossoverRate = sliderCrossoverRate.getValue();
        int generationSize = generationSlider.getValue();
        int populationSize = popSize.getValue();

        Double mutationRateValue;//default mutation rate
        String mutation = mutationRateBox.getText().trim();

        try{
            mutationRateValue = Double.parseDouble(mutation);
        }catch (Exception e){
            try{
                Integer intValue = Integer.parseInt(mutation);
                mutationRateValue = intValue.doubleValue();
            }catch(Exception e2){
                mutationRateValue = 3.0d;
            }
        }

        System.out.println("\nMutation rate: " + mutationRateValue);
        System.out.println("\nCrossover rate: " + crossoverRate);

        if(populationSize != 0) {
            testEA = new TestEA();
            testEA.setLogger(new Log());
            testEA.init(1000, false, elitismCheckBox.isSelected(), new NewPopulation(populationSize),
                    new CustomEvaluator(battleVisible.isSelected(), numHelpersSlider.getValue()), new RandomMutator(mutationRateValue),
                    new TournamentSelection(), new GreedySelection(), new UniformCrossover(crossoverRate));
            if(generationLimit.isSelected()){
                testEA.setNumGenerations(generationSize);
            }
        }
    }
}

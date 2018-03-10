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
        writeBest.addActionListener(e -> writeBestIndividual());
        printTotal.addActionListener(e -> printTotalBest());
        writeTotalBest.addActionListener(e -> writeTotalBestToFile());
        ((DefaultCaret) outputText.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        PrintStream out = new PrintStream(new outputPrintStream(outputText));
        System.setOut(out);
        System.setErr(out);
    }

    private class outputPrintStream extends OutputStream  {
        private final JTextArea jta;
        outputPrintStream(JTextArea j){
            this.jta = j;
        }
        @Override
        public void write(int b) {
            jta.append((String.valueOf((char)b)));
        }
    }

    public static void main(String[] args){
        JFrame j = new JFrame("Robocode Evolutionary Algorithm");
        j.setContentPane(new GUI().panel1);
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        j.pack();
        j.setVisible(true);
    }

    private void run(){
        System.out.println("Running...\n");
        setupEnvironment();
        testEA.start();
    }

    private void stop(){
        System.out.println("Stopping after run...\n");
        if(testEA != null){
            testEA.forceTerminate();
        }else{
            System.out.println("Null Instance\n");
        }
    }

    private void printTotalBest(){
        if(testEA != null){
            System.out.println(testEA.printGenome(testEA.getTotalBest()));
        }else{
            System.out.println("Null Instance\n");
        }
    }

    private void printCurrentBest(){
        Individual i = testEA.getBest();
        if(i != null) {
            System.out.println("Best: " + i.fitness + "\n");
            System.out.println(testEA.printGenome());
        }else{
            System.out.println("Null individual\n");
        }
    }

    private void openGraphView(){
        if(testEA != null) {
            Logger log = testEA.getLogger();
            if (log != null) {
                new GraphView(log).launch();
            } else {
                System.out.println("Null log\n");
            }
        }else{
            System.out.println("Null Instance\n");
        }
    }

    private void writeToFile(){
        testEA.getLogger().writeToFile();
    }

    private void writeTotalBestToFile(){
        if(testEA != null){
            Individual totalBest = testEA.getTotalBest();
            if(totalBest != null){
                gen.writeIndividualToFile(totalBest);
            }
        }else{
            System.out.println("Null Instance\n");
        }
    }

    private void writeBestIndividual() {
        if(testEA != null){
            Individual best = testEA.getBest();
            if(best != null){
                gen.writeIndividualToFile(best);
            }else{
                System.out.println("Null Individual\n");
            }
        }else{
            System.out.println("Null Instance\n");
        }
    }

    private void setupEnvironment(){
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
                mutationRateValue = 5.0d;
            }
        }

        System.out.println("Mutation rate: " + mutationRateValue);

        if(populationSize != 0) {
            testEA = null;
            testEA = new TestEA();
            testEA.setLogger(new Log());
            testEA.init(1000, false, new NewPopulation(populationSize),
                    new CustomEvaluator(), new RandomMutator(mutationRateValue), new TournamentSelection(), new GreedySelection(), new UniformCrossover());//UniformCrossover()
            if(generationLimit.isSelected()){
                testEA.setNumGenerations(generationSize);
            }
        }
    }
}

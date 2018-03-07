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
    private TestEA testEA;
    private final CodeGen gen;
    private boolean hasStopped = false;

    private GUI(){
        runButton.addActionListener(e -> run());
        stopButton.addActionListener(e -> stop());
        openGraphButton.addActionListener(e -> openGraphView());
        write.addActionListener(e -> writeToFile());
        printBest.addActionListener(e -> printBest());
        ((DefaultCaret) outputText.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        gen = new CodeGen();
        PrintStream out = new PrintStream(new outputPrintStream(outputText));
        System.setOut(out);
        System.setErr(out);
        popSize.addChangeListener(e -> populationLabel.setText("Population Size: " + popSize.getValue()));
        generationSlider.addChangeListener(e -> genLimit.setText("Generation Limit: " + generationSlider.getValue()));
        writeBest.addActionListener(e -> writeBestIndividual());
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
        if(testEA == null){
            hasStopped = false;
            setupEnvironment();
        }
        if(!hasStopped) {
            testEA.start();//run once
        }else{
            System.out.println("Thread has stopped, restarting\n");
            testEA = null;
            run();
        }
    }

    @SuppressWarnings("deprecation")
    private void stop(){
        System.out.println("Stopping...\n");
        testEA.stop();
        hasStopped = true;
    }

    private void printBest(){
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
            Logger l = testEA.getLogger();
            if (l != null) {
                System.out.println("Launching...\n");
                new GraphView(l).launch();
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
        if(populationSize != 0) {
            testEA = new TestEA();
            testEA.setLogger(new Log());
            testEA.init(1000, false, new NewPopulation(populationSize),
                    new CustomEvaluator(), new RandomMutator(1), new TournamentSelection(), new GreedySelection(), new UniformCrossover());//UniformCrossover()
            if(generationLimit.isSelected()){
                testEA.setNumGenerations(generationSize);
            }
        }
    }
}

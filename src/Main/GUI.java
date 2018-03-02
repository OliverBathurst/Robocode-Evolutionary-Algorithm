package Main;
import Framework.Logger;
import javax.swing.*;
import java.io.IOException;
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
    private TestEA testEA;
    private boolean hasStopped = false;
    private int launchCounter = 0;

    GUI(){
        runButton.addActionListener(e -> run());
        stopButton.addActionListener(e -> stop());
        openGraphButton.addActionListener(e -> openGraphView());
        write.addActionListener(e -> writeToFile());
        printBest.addActionListener(e -> printBest());
        PrintStream out = new PrintStream(new outputPrintStream(outputText) );
        System.setOut(out);
        System.setErr(out);
    }
    private class outputPrintStream extends OutputStream  {
        private final JTextArea jta;

        outputPrintStream(JTextArea j){
            this.jta = j;
        }
        @Override
        public void write(int b) throws IOException {
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
                if(launchCounter != 1) {
                    launchCounter++;
                    System.out.println("Launching...\n");
                    new GraphView(l).launch();
                }else{
                    JOptionPane.showMessageDialog(null, "Cannot call launch more than once");
                }
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

    private void setupEnvironment(){
        testEA = new TestEA();
        testEA.setLogger(new Log());
        testEA.init(10,false, new NewPopulation(10),
                new CustomEvaluator(), new RandomMutator(50), new TournamentSelection(), new GreedySelection(), new UniformCrossover());
    }
}

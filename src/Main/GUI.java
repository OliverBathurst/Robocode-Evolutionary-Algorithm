package Main;
import javax.swing.*;

public class GUI {
    private JPanel panel1;
    private JButton runButton;
    private JButton stopButton;
    private JButton openGraphButton;
    private JTextArea outputText;
    private JButton write;
    private JButton printBest;
    private TestEA testEA;
    private boolean hasStopped = false;

    GUI(){
        runButton.addActionListener(e -> run());
        stopButton.addActionListener(e -> stop());
        openGraphButton.addActionListener(e -> openGraphView());
        write.addActionListener(e -> writeToFile());
        printBest.addActionListener(e -> printBest());
    }

    public static void main(String[] args){
        JFrame j = new JFrame("Robocode Evolutionary Algorithm");
        j.setContentPane(new GUI().panel1);
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        j.pack();
        j.setVisible(true);
    }

    private void run(){
        if(testEA == null){
            setupEnvironment();
        }
        if(!hasStopped) {
            testEA.start();//run once
        }else{
            outputText.append("Thread has stopped, restarting\n");
            hasStopped = false;
            testEA = null;
            run();
        }
    }

    private void stop(){
        testEA.stop();
        hasStopped = true;
    }

    private void printBest(){
        Individual i = testEA.getBest();
        if(i != null) {
            outputText.append("Best: " + i.fitness + "\n");
            outputText.append(testEA.printGenome());
        }else{
            outputText.append("Null individual\n");
        }
    }

    private void openGraphView(){

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

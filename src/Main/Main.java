package Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class Main extends Application{
    private XYChart.Series series;

    public static void main(String[] args) {
        launch(args);
    }

    public Main(){}

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Robocode Evolutionary Algorithm");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Generations");
        yAxis.setLabel("Fitness");
        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setTitle("Fitness over Generations");
        series = new XYChart.Series();
        series.setName("Fitness");
        lineChart.getData().add(series);
        Scene scene = new Scene(lineChart,800,600);
        scene.setOnMouseClicked(event -> run());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void run(){
        TestEA testEA = new TestEA();
        testEA.init(10,false, new NewPopulation(10),
                new CustomEvaluator(), new RandomMutator(50), new TournamentSelection(), new GreedySelection(), new UniformCrossover());
        testEA.setNumGenerations(1);//set no gens to 1, so can call a new generation on demand

        Platform.runLater(() -> {
            for(int i = 0; i < 20; i++) {
                Individual individual = testEA.run();//run once
                series.getData().add(new XYChart.Data(i, individual.getFitness()));
            }
        });
    }
}

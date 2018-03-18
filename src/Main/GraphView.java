package Main;
import Framework.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.Map;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 * Displays fitness over generations on a graph
 */
class GraphView {
    private static Logger log;

    @SuppressWarnings("WeakerAccess")
    public static class GUIView extends Application {
        private XYChart.Series<Number, Number> series;

        public static void main(String[] args) {
            launch();
        }

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Robocode Evolutionary Algorithm");//title
            final NumberAxis xAxis = new NumberAxis();//axes
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Generations");
            yAxis.setLabel("Fitness");//set labels
            final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);//init line chart
            lineChart.setTitle("Fitness over Generations");
            series = new XYChart.Series<>();
            series.setName("Fitness");

            int counter = 0;
            for (Map.Entry me : log.getLog().entrySet()) {//add data points from the log
                series.getData().add(new XYChart.Data<>(counter, (float) me.getValue()));
                counter++;
            }

            lineChart.getData().add(series);//add the series
            primaryStage.setScene(new Scene(lineChart, 800, 600));//set scene
            primaryStage.show();//show
        }
    }

    /**
     * Initialise with log of (generation number, fitness)
     */

    GraphView(Logger l){
        log = l;
    }


    /**
     * Launch graph
     */
    void launch(){
        new GUIView();
        GUIView.main(null);
    }
}

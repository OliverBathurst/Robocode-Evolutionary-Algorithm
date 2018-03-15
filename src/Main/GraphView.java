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
            primaryStage.setTitle("Robocode Evolutionary Algorithm");
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Generations");
            yAxis.setLabel("Fitness");
            final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Fitness over Generations");
            series = new XYChart.Series<>();
            series.setName("Fitness");

            int counter = 0;
            for (Map.Entry me : log.getLog().entrySet()) {
                series.getData().add(new XYChart.Data<>(counter, (float) me.getValue()));
                counter++;
            }

            lineChart.getData().add(series);
            primaryStage.setScene(new Scene(lineChart, 800, 600));
            primaryStage.show();
        }
    }

    /**
     * Initialise with log of (generations, fitness)
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

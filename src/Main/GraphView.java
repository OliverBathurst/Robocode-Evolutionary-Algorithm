/**
 * Created by Oliver on 02/03/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */
package Main;
import Framework.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oliver on 29/01/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

class GraphView {
    private static Logger log;

    public static class GUIView extends Application {
        private XYChart.Series<Number, Number> series;

        public static void main(String[] args) {
            launch();
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            primaryStage.setTitle("Robocode Evolutionary Algorithm");
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Generations");
            yAxis.setLabel("Fitness");
            final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Fitness over Generations");
            series = new XYChart.Series<>();
            series.setName("Fitness");

            HashMap<Object, Object> map = log.getLog();
            int counter = 1;
            for (Map.Entry me : map.entrySet()) {
                series.getData().add(new XYChart.Data<>(counter, (float) me.getValue()));
                counter++;
            }
            lineChart.getData().add(series);
            Scene scene = new Scene(lineChart, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    GraphView(Logger l){
        log = l;
    }

    void launch(){
        new GUIView().main(null);
    }
}
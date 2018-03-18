package Main;
import Framework.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oliver on 06/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class Log implements Logger {
    private final HashMap<Object, Object> log = new HashMap<>();//for storing run info like avg fitnesses and best fitness over gens

    Log(){}

    /**
     * Return map
     */
    @Override
    public HashMap<Object, Object> getLog() {
        return log;
    }

    /**
     * Puts key in map for later writing
     */
    @Override
    public void log(Object key, Object value) {
        log.put(key, value);
    }

    /**
     * Writes all values in map to file
     */
    @Override
    public void writeToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File", ".csv"));

        if (fileChooser.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
            try {
                PrintWriter pw = new PrintWriter(new File(fileChooser.getSelectedFile().getAbsolutePath() + ".csv"));//write to CSV file
                StringBuilder sb = new StringBuilder();

                for (Map.Entry<Object, Object> pair : log.entrySet()) {
                    sb.append(pair.getKey().toString()).append(",").append(pair.getValue().toString())
                            .append("\n");
                }
                pw.write(sb.toString());
                pw.close();
            }catch (Exception ignored){}
        }
    }
}

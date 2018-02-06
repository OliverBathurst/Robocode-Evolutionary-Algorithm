package Main;
import Framework.Logger;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oliver on 06/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public class Log implements Logger {
    private final HashMap<Object, Object> log = new HashMap<>();

    Log(){}

    @Override
    public Object getLog() {
        return log;
    }

    @Override
    public void log(Object key, Object value) {
        log.put(key, value);
    }

    @Override
    public void writeToFile() {
        try {
            PrintWriter pw = new PrintWriter(new File("output.csv"));
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<Object, Object> pair : log.entrySet()) {
                sb.append(pair.getKey().toString()).append(",").append(pair.getValue().toString())
                        .append("\n");
            }
            pw.write(sb.toString());
            pw.close();
        }catch (Exception e){
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

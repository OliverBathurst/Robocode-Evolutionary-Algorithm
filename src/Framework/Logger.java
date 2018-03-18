package Framework;
import java.util.HashMap;

/**
 * Simple logging/reporting interface for (generations,fitness) CSV exporting/graphing
 */
public interface Logger {
    HashMap<Object, Object> getLog();
    void log(Object key, Object value);
    void writeToFile();
}

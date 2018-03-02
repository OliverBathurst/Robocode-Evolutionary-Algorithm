package Framework;

import java.util.HashMap;

/**
 * Created by Oliver on 06/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public interface Logger {
    HashMap<Object, Object> getLog();
    void log(Object key, Object value);
    void writeToFile();
}

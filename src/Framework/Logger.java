package Framework;

/**
 * Created by Oliver on 06/02/2018.
 * Written by Oliver Bathurst <oliverbathurst12345@gmail.com>
 */

public interface Logger {
    Object getLog();
    void log(Object key, Object value);
    void writeToFile();
}

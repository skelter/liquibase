package prototype;

import java.util.ArrayList;
import java.util.List;
import liquibase.logging.Logger;
import liquibase.logging.core.AbstractLogger;

/**
 * Created with IntelliJ IDEA.
 * User: suehs
 * Date: 1/3/13
 * Time: 4:19 PM
 */
public class SimpleMemLogger extends AbstractLogger implements Logger {
    private List<String> entries = new ArrayList<String>() ;
    public List<String> getEntries() {
        return entries;
    }

    public void setName(String name) {
    }

    public void setLogLevel(String logLevel, String logFile) {
    }

    public void severe(String message) {
        entries.add(message);
    }

    public void severe(String message, Throwable e) {
        entries.add(message);
    }

    public void warning(String message) {
        entries.add(message);
    }

    public void warning(String message, Throwable e) {
        entries.add(message);
    }

    public void info(String message) {
        entries.add(message);
    }

    public void info(String message, Throwable e) {
        entries.add(message);
    }

    public void debug(String message) {
        entries.add(message);
    }

    public void debug(String message, Throwable e) {
        entries.add(message);
    }

    public int getPriority() {
        return 99;
    }
}

package prototype;

import java.util.ArrayList;
import java.util.List;

import liquibase.logging.LogLevel;
import liquibase.logging.Logger;
import liquibase.logging.core.AbstractLogger;

/**
 * Created with IntelliJ IDEA.
 * User: suehs
 * Date: 1/3/13
 * Time: 4:19 PM
 */
public class SimpleMemLogger extends AbstractLogger implements Logger {

    public SimpleMemLogger() {
        super.setLogLevel(LogLevel.DEBUG);
    }

    private List<String> entries = new ArrayList<String>() ;
    public List<String> getEntries() {
        return entries;
    }

    public void setName(String name) {
    }

    public void setLogLevel(String logLevel, String logFile) {
        super.setLogLevel(logLevel);
    }

    public void severe(String message) {
        print(message);
        entries.add(message);
    }

    public void severe(String message, Throwable e) {
        print(message);
        entries.add(message);
    }

    public void warning(String message) {
        print(message);
        entries.add(message);
    }

    public void warning(String message, Throwable e) {
        print(message);
        entries.add(message);
    }

    public void info(String message) {
        print(message);
        entries.add(message);
    }

    public void info(String message, Throwable e) {
        print(message);
        entries.add(message);
    }

    public void debug(String message) {
        print(message);
        entries.add(message);
    }

    public void debug(String message, Throwable e) {
        print(message);
        entries.add(message);
    }

    public int getPriority() {
        return 99;
    }

    private void print(String message) {
        System.err.println("SIMPLEMEMLOGGER: " + message);
    }
}

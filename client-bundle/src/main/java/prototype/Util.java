package prototype;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.logging.LogFactory;
import liquibase.resource.FileSystemResourceAccessor;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: skelter
 * Date: 1/4/13
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util implements Callable {

    public Object call() {
        System.out.println("Util.run():");
        SimpleMemLogger logger = new SimpleMemLogger();
        try {
            LogFactory.setLogger("liquibase",logger);
            System.out.println("client bundle's Util: LogFactory.getLogger returns " + LogFactory.getLogger());
            File changelogfile = new File(System.getProperty("liquibase.root.dir"),"liquibase-integration-tests/src/test/resources/changelogs/common/autoincrement.tests.changelog.xml");
            System.out.println("File exists? " + changelogfile.exists());
            Liquibase lb = new Liquibase(changelogfile.getAbsolutePath(), new FileSystemResourceAccessor(), (Database) null, logger) ; // should cause lots of errors to be logged
            lb.validate();
            lb.update("");
        }
        catch (Throwable t) {
            t.printStackTrace();
            //eat it, we know the above code is catastrophic
        }
        return logger.getEntries();
    }
}

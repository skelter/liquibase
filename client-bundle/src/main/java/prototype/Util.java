package prototype;

import liquibase.Liquibase;
import liquibase.database.Database;

/**
 * Created with IntelliJ IDEA.
 * User: skelter
 * Date: 1/4/13
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util implements Runnable {

    public void run() {
        System.out.println("Util.run():");
        SimpleMemLogger logger = new SimpleMemLogger();
        try {
            Liquibase lb = new Liquibase((String) null, null, (Database) null) ; // should cause lots of errors to be logged
        }
        catch (Throwable t) {
            //eat it, we know the above code is catastrophic
        }
        System.out.println("Logger class is " + LogFactory.getLogger().getClass());
    }
}

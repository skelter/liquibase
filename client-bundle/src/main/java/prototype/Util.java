package prototype;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.logging.LogFactory;
import liquibase.servicelocator.ServiceLocator;
import prototype.service.ServiceLocatorFromClient;

/**
 * Created with IntelliJ IDEA.
 * User: suehs
 * Date: 1/4/13
 * Time: 3:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util implements Runnable {

    public void run() {
        System.out.println("Util.run():");
        SimpleMemLogger logger = new SimpleMemLogger();
        ServiceLocator.setInstance(new ServiceLocatorFromClient());
        try {
            Liquibase lb = new Liquibase((String) null, null, (Database) null) ; // should cause lots of errors to be logged
        }
        catch (Throwable t) {
            //eat it, we know the above code is catastrophic
        }
        System.out.println("Logger class is " + LogFactory.getLogger().getClass());
    }
}

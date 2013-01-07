package prototypeclient;

import liquibase.Liquibase;
import liquibase.database.Database;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import prototype.SimpleMemLogger;

/**
 * Created with IntelliJ IDEA.
 * User: suehs
 * Date: 1/3/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class OsgiLbClientBundleActivator implements BundleActivator {


    public void start(BundleContext bundleContext) throws Exception {
        SimpleMemLogger logger = new SimpleMemLogger();
        try {
          Liquibase lb = new Liquibase((String) null, null, (Database) null) ; // should cause lots of errors to be logged
        }
        catch (Throwable t) {
            //eat it, we know the above code is catastrophic
        }
        System.out.println("Logger class is " + LogFactory.getLogger().getClass());
    }

    public void stop(BundleContext bundleContext) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

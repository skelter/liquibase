package prototypeutil;

import org.apache.felix.framework.Felix;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: suehs
 * Date: 1/3/13
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Session {

    private Framework framework;

    public Session() throws BundleException {
        this.framework = initFramework();
        printBundles(framework);
    }

    private Framework initFramework() throws BundleException {
        Framework framework = createFramework();
        framework.start();
        List<Bundle> installed = installBundles(framework);
        startBundles(framework, installed);
        return framework;
    }

    private void startBundles(Framework framework, List<Bundle> installed) throws BundleException {
        for(Bundle b : installed) {
            System.out.println("Starting " + b);
            try {
                b.start();
            } catch (BundleException be) {
                System.err.println("Exception starting " + b + " " + b.getSymbolicName());
                be.printStackTrace();
                throw be;
            }
        }
    }

    private List<Bundle> installBundles(Framework framework) throws BundleException {
        File curDir = new File(System.getProperty("liquibase.root.dir", "."));
        File m2repo = new File(System.getProperty("user.home"), ".m2/repository");
        File[] jarsToLoad = {
                // madness this way-> new File(m2repo, "org/osgi/org.osgi.core/4.1.0/org.osgi.core-4.1.0.jar"),//
                //new File(m2repo, "org/apache/felix/org.osgi.compendium/1.2.0/org.osgi.compendium-1.2.0.jar"),//
                //new File(m2repo, "org/apache/felix/org.osgi.foundation/1.2.0/org.osgi.foundation-1.2.0.jar"),//
                //new File(m2repo, "org/apache/felix/org.apache.felix.framework/1.4.0/org.apache.felix.framework-1.4.0.jar"),
                new File(curDir,"liquibase-osgi/target/liquibase-osgi-3.0.0-SNAPSHOT.jar"), //
                new File(curDir,"client-bundle/target/liquibase-config-prototype-client-3.0.0-SNAPSHOT.jar"), //
        }        ;
        List<Bundle> installed = new ArrayList<Bundle>();
        for(File jarToLoad : jarsToLoad) {
            System.out.println("bundle expected at " + jarToLoad.getAbsolutePath());
            if (! jarToLoad.exists()) {
                throw new BundleException("Can not load client bundle", new FileNotFoundException(jarToLoad.getAbsolutePath()));
            }
            BundleContext bundleContext = framework.getBundleContext();
            String bundleLoc = jarToLoad.toURI().toString();
            Bundle installedBundle = bundleContext.installBundle(bundleLoc);
            installed.add(installedBundle);
            System.out.println("installed " + installedBundle);
        }

        return installed;
    }

    private Framework createFramework() throws BundleException {
//        FrameworkFactory frameworkFactory;
//        ServiceLoader<FrameworkFactory> loader = ServiceLoader.load(
//                FrameworkFactory.class);
//        frameworkFactory = loader.iterator().next();
        Map<String, String> config = new HashMap<String, String>();
        //ya know, some days, type systems are a PitA
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Map<String,String> sysProps = new HashMap(System.getProperties());
        config.putAll(sysProps);
        config.put("org.osgi.framework.storage.clean","onFirstInit");
//        Framework framework = frameworkFactory.newFramework(config);
        Framework framework = new Felix(config);
        return framework;
    }

    private void printBundles(Framework framework) {
        Bundle[] bundles = framework.getBundleContext().getBundles();
        System.out.println("There are " + bundles.length + " bundles!");
        for (Bundle b : bundles) {
            System.out.println("Bundle " + b);
        }
    }

    public void close() throws Exception {
        framework.stop();
    }

    public List<String> logResults() {
        return null;
    }

    private Bundle findOurBundle() throws BundleException {
        for(Bundle b : framework.getBundleContext().getBundles()) {
            System.out.println("Checking " + b.getSymbolicName());
            if (b.getSymbolicName() != null && b.getSymbolicName().contains("prototype")) {
                return b;
            }
        }
        throw new BundleException("Could not find our bundle");
    }
    public void run() {
//        BundleContext bundleContext = framework.getBundleContext();
//        ServiceReference ref = bundleContext.getServiceReference("liquibase.Liquibase");
//        Liquibase lb = (Liquibase) bundleContext.getService(ref);
        try {
            Class cls = findOurBundle().loadClass("prototype.Util");
            Runnable r = (Runnable) cls.newInstance();
            r.run();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

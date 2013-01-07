package prototype.service;

import liquibase.servicelocator.ServiceLocator;
import prototype.SimpleMemLogger;

/**
 * Created with IntelliJ IDEA.
 * User: suehs
 * Date: 1/7/13
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceLocatorFromClient extends ServiceLocator {
  public ServiceLocatorFromClient() {
      addPackageToScan("prototype"); //probably can't see this
  }
}

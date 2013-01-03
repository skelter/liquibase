package prototypetest;

import org.junit.*;
import prototype.OsgiTestBundle;
import prototype.Session;
import prototype.SimpleMemLogger;

import static junit.framework.Assert.*;

public class BundleWithLoggingTest {
    SimpleMemLogger logger = new SimpleMemLogger();
    private Session session;

    public BundleWithLoggingTest() {

    }

    @Before
    public void setUp() {
      logger = new SimpleMemLogger();
      session = OsgiTestBundle.create();
    }

    @After
    public void tearDown() {
        logger = null;
        session.close();
    }

    @Test
    public void ourLogNotEmpty() {
        assertFalse(logger.getEntries().isEmpty());
    }

}
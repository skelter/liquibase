package prototypetest;

import org.apache.felix.framework.Felix;
import org.junit.*;
import prototypeutil.Session;

import static junit.framework.Assert.*;

public class BundleWithLoggingTest {
    private Session session;

    public BundleWithLoggingTest() {
       Class felixName = Felix.class;
    }

    @Before
    public void setUp() throws Exception {
      session = new Session();
      session.run();
    }

    @After
    public void tearDown() throws Exception {
        session.close();
    }

    @Test
    public void ourLogNotEmpty() {
        assertFalse(session.logResults().isEmpty());
    }

}
package prototypetest;

import org.apache.felix.framework.Felix;
import org.junit.*;
import prototypeutil.Session;

import java.util.List;

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
        List<String> logs = session.logResults();
        assertNotNull("No result was returned.", logs);
        assertFalse("Logs should not be empty", logs.isEmpty());
        for(String s : logs) {
            System.out.println(s);
        }
    }

}
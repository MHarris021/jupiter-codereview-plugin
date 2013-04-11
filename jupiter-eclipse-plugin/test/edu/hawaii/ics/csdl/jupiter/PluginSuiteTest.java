package edu.hawaii.ics.csdl.jupiter;

import junit.framework.Test;
import junit.framework.TestSuite;
import edu.hawaii.ics.csdl.jupiter.event.CodeReviewModelEventTest;
import edu.hawaii.ics.csdl.jupiter.test.ProjectMockupTest;

/**
 * Provides a suite to collect all tests that require plugin instance
 * for test purpose.
 *
 * @author Hongbing Kou
 * @version $Id: PluginSuiteTest.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class PluginSuiteTest extends TestSuite {
  /**
   * Returns the suite for test.
   *
   * @return Test suite.
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(ProjectMockupTest.class);
    suite.addTestSuite(CodeReviewModelEventTest.class);
    return suite;
  }
}

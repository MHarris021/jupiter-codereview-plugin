package edu.hawaii.ics.csdl.jupiter.event;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;
import edu.hawaii.ics.csdl.jupiter.ReviewPlugin;
import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;
import edu.hawaii.ics.csdl.jupiter.ReviewPluginConfiguration;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewModel;

/**
 * Provides test cases for <code>CodeReviewModeEvent</code>.
 * @author Takuya Yamashita
 * @version $Id: CodeReviewModelEventTest.java 40 2007-05-30 00:24:50Z hongbing $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ReviewPluginConfiguration.class)
public class CodeReviewModelEventTest extends TestCase {
  /**
   * Initializes plugin for test. 
   */
  protected void setUp() {
    ReviewPlugin plugin = new ReviewPluginImpl();
  }
  
  /**
   * Tests the <code>getEventType</code> method.
   */
  public void testGetEventType() {
    int mergeType = ReviewIssueModelEvent.MERGE;
    int addType = ReviewIssueModelEvent.ADD;
    int deleteType = ReviewIssueModelEvent.DELETE;    
    
    ReviewModel reviewModel;
	ReviewIssueModelEvent event = new ReviewIssueModelEvent(null, mergeType | addType, reviewModel);
    int eventType = event.getEventType();
    assertEquals("Checking merge type. ", true, (eventType & mergeType) != 0);
    assertEquals("Checking addType type. ", true, (eventType & addType) != 0);
    assertEquals("Checking deleteType type. ", false, (eventType & deleteType) != 0);    
    assertEquals("Checking mergeType and addType type. ", true, 
                (eventType & (mergeType | addType)) != 0);
  }
}

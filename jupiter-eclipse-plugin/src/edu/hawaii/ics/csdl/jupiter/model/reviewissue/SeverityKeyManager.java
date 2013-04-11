package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

import org.eclipse.core.resources.IProject;

import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;

/**
 * Provides the keys for the <code>Severity</code>.
 *
 * @author Takuya Yamashita
 * @version $Id: SeverityKeyManager.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class SeverityKeyManager extends KeyManager<Severity> {
  /** The singleton instance. */
  private static KeyManager<Severity> theInstance;

  /**
   * Instantiates supper class and fill key severity item into this instance from XML file.
   */
  private SeverityKeyManager() {
    super();
  }

  /**
   * Gets the <code>SeverityKeyManager</code> instance.
   * 
   * @param project the project.
   * @param reviewId the review id.
   * @return the <code>SeverityKeyManager</code> instance.
   */
  public static KeyManager<Severity> getInstance(IProject project, ReviewId reviewId) {
    if (theInstance == null) {
      theInstance = new SeverityKeyManager();
    }
    if (project != null && reviewId != null) {
      theInstance.loadKey(project, reviewId);
    }
    return theInstance;
  }

}

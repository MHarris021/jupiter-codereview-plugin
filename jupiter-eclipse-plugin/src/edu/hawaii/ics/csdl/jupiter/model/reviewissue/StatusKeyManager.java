package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

import org.eclipse.core.resources.IProject;

import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;

/**
 * Provides the keys for the <code>Status</code>.
 *
 * @author Takuya Yamashita
 * @version $Id: StatusKeyManager.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class StatusKeyManager extends KeyManager<Status> {
  /** The singleton instance. */
  private static StatusKeyManager theInstance;

  /**
   * Instantiates supper class and fill key status item into this instance from XML file.
   */
  private StatusKeyManager() {
    super();
  }

  /**
   * Gets the <code>StatusKeyManager</code> instance.
   * 
   * @param project the project.
   * @param reviewId the review id.
   * @return the <code>StatusKeyManager</code> instance.
   */
  public static KeyManager<Status> getInstance(IProject project, ReviewId reviewId) {
    if (theInstance == null) {
      theInstance = new StatusKeyManager();
    }
    if (project != null && reviewId != null) {
      theInstance.loadKey(project, reviewId);
    }
    return theInstance;
  }
}

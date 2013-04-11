package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

import org.eclipse.core.resources.IProject;

import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;

/**
 * Provides the keys for the <code>Resolution</code>.
 *
 * @author Takuya Yamashita
 * @version $Id: ResolutionKeyManager.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class ResolutionKeyManager extends KeyManager<Resolution> {
  /** The singleton instance. */
  private static ResolutionKeyManager theInstance;

  /**
   * Instantiates supper class and fill key resolution item into this instance from XML file.
   */
  private ResolutionKeyManager() {
    super();
  }

  /**
   * Gets the <code>ResolutionKeyManager</code> instance.
   * 
   * @param project the project.
   * @param reviewId the review id.
   * @return the <code>ResolutionKeyManager</code> instance.
   */
  public static ResolutionKeyManager getInstance(IProject project, ReviewId reviewId) {
    if (theInstance == null) {
      theInstance = new ResolutionKeyManager();
    }
    if (project != null && reviewId != null) {
      theInstance.loadKey(project, reviewId);
    }
    return theInstance;
  }

}

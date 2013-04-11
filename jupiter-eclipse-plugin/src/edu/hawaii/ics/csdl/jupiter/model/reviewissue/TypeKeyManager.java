package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

import org.eclipse.core.resources.IProject;

import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;

/**
 * Provides the keys for the <code>Type</code>.
 *
 * @author Takuya Yamashita
 * @version $Id: TypeKeyManager.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class TypeKeyManager extends KeyManager<Type> {
  /** The singleton instance. */
  private static TypeKeyManager theInstance;

  /**
   * Instantiates supper class and fill key type item into this instance from XML file.
   */
  private TypeKeyManager() {
    super();
  }

  /**
   * Gets the <code>TypeKeyManager</code> instance.
   * 
   * @param project the project.
   * @param reviewId the review id.
   * @return the <code>TypeKeyManager</code> instance.
   */
  public static TypeKeyManager getInstance(IProject project, ReviewId reviewId) {
    if (theInstance == null) {
      theInstance = new TypeKeyManager();
    }
    if (project != null && reviewId != null) {
      theInstance.loadKey(project, reviewId);
    }
    return theInstance;
  }

}

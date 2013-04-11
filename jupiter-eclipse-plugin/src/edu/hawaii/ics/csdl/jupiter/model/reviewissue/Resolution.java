package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

/**
 * Provides resolution information. Clients can instantiate this with a key and its ordinal number.
 * The ordinal number is used for the comparison of this instances.
 *
 * @author Takuya Yamashita
 * @version $Id: Resolution.java 82 2008-02-22 09:34:57Z jsakuda $
 */
public class Resolution extends Ordinal {

	/**
   * Constructor for the Resolution object. Sets the key string and its ordinal number.
   *
   * @param key the key of the code review.
   * @param ordinal the ordinal of the key.
   */
  public Resolution(String key, int ordinal) {
    super(key, ordinal);
  }

  protected Resolution() {
	  super();
  }
  
}

package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

/**
 * Provides type information. Clients can instantiate this with a key and its ordinal number.
 * The ordinal number is used for the comparison of this instances.
 *
 * @author Takuya Yamashita
 * @version $Id: Type.java 82 2008-02-22 09:34:57Z jsakuda $
 */
public class Type extends Ordinal {
  /**
   * Constructor for the Type object. Sets the Type key and its ordinal number.
   *
   * @param key the key of the code review.
   * @param ordinal the ordinal of the key.
   */
  public Type(String key, int ordinal) {
	  super(key, ordinal);
  }
  
  protected Type() {
	  super();
  }
  
}

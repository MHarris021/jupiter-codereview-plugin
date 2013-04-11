package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

/**
 * Provides severity information Clients can instantiate this with a key and its ordinal number.
 * The ordinal number is used for the comparison of this instances.
 *
 * @author Takuya Yamashita
 * @version $Id: Severity.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class Severity extends Ordinal{
	
	public Severity(String key, int ordinal) {
		super(key, ordinal);
	}
	
	protected Severity() {
		super();
	}
	
}

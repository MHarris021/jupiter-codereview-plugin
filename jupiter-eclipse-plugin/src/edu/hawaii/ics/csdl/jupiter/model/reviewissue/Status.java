package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

/**
 * Provides status information. Clients can instantiate this with a key and its ordinal number.
 * The ordinal number is used for the comparison of this instances.
 *
 * @author Takuya Yamashita
 * @version $Id: Status.java 82 2008-02-22 09:34:57Z jsakuda $
 */
public class Status extends Ordinal {
	
	public Status(String key, int ordinal) {
		super(key, ordinal);
	}
	
	protected Status() {
		super();
	}
}

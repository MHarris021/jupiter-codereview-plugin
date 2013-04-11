package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

public abstract class Ordinal implements Comparable<Ordinal>{

	/** The key of the type. */
	private String key;
	/** Assigns an ordinal to this key. */
	private int ordinal;

	public Ordinal(String key, int ordinal) {
		super();
		this.key = key;
		this.ordinal = ordinal;
	}
	
	protected Ordinal() {
		this("", Integer.MAX_VALUE);
	}

	/**
	   * Gets the key for the type value.
	   *
	   * @return the key for the type value.
	   */
	public String getKey() {
	    return this.key;
	  }

	/**
	   * Compare this object by the ordinal number.
	   *
	   * @see java.lang.Comparable#compareTo(java.lang.Object)
	   */
	
	public int compareTo(Ordinal o) {
		return ordinal - o.ordinal;
	}

}
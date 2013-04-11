package edu.hawaii.ics.csdl.jupiter.file.review;

import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * 
 */
public class ReviewIssueMeta {

	private ReviewDate creationDate;
	private ReviewDate lastModificationDate;

	public ReviewIssueMeta() {
	}

	/**
	 * Gets the value of the creationDate property.
	 * 
	 * @return possible object is {@link CreationDate }
	 * 
	 */
	@XmlElement(name = "CreationDate")
	public ReviewDate getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the value of the creationDate property.
	 * 
	 * @param value
	 *            allowed object is {@link CreationDate }
	 * 
	 */
	public void setCreationDate(ReviewDate value) {
		this.creationDate = value;
	}

	/**
	 * Gets the value of the lastModificationDate property.
	 * 
	 * @return possible object is {@link ReviewDate }
	 * 
	 */
	@XmlElement(name = "LastModificationDate")
	public ReviewDate getLastModificationDate() {
		return lastModificationDate;
	}

	/**
	 * Sets the value of the lastModificationDate property.
	 * 
	 * @param value
	 *            allowed object is {@link ReviewDate }
	 * 
	 */
	public void setLastModificationDate(ReviewDate value) {
		this.lastModificationDate = value;
	}

}

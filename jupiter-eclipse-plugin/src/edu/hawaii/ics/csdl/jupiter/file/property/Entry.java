package edu.hawaii.ics.csdl.jupiter.file.property;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 
 * @author Michael Harris, TETN
 * 
 */

public class Entry {

	private String name;

	private String id;

	public Entry() {
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	@XmlAttribute
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

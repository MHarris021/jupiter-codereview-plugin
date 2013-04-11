package edu.hawaii.ics.csdl.jupiter.file.review;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * 
 * 
 */
public class ReviewFile {

	private String value = "";
	private Integer line;

	public ReviewFile() {
	}

	/**
	 * Gets the value of the value property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlValue
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the value property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the value of the line property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	@XmlAttribute
	public Integer getLine() {
		return line;
	}

	/**
	 * Sets the value of the line property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setLine(Integer value) {
		this.line = value;
	}

}

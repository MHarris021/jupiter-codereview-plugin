package edu.hawaii.ics.csdl.jupiter.file.property;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 
 * 
 */
public class Filter {

	private Boolean enabled;
	private String name;
	private String value;

	public Filter() {
	}

	/**
	 * Gets the value of the enabled property.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	@XmlAttribute
	public Boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the value of the enabled property.
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setEnabled(Boolean value) {
		this.enabled = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
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

	/**
	 * Gets the value of the value property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlAttribute
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

}

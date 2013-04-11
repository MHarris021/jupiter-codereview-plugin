package edu.hawaii.ics.csdl.jupiter.file.property;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Michael Harris, TETN
 * 
 */

public class Phase {

	private List<Filter> filters;
	private Boolean enabled;
	private String name;

	public Phase() {
	}

	@XmlElement(name = "Filter")
	public List<Filter> getFilters() {
		return this.filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
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

}

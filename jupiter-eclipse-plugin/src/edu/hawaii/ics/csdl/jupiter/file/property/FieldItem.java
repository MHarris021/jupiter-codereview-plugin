package edu.hawaii.ics.csdl.jupiter.file.property;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Michael Harris, TETN
 * 
 */

public class FieldItem {

	private List<Entry> entry;
	private String _default;
	private String id;

	public FieldItem() {
	}

	@XmlElement(name = "Entry")
	public List<Entry> getEntry() {
		return this.entry;
	}

	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}

	/**
	 * Gets the value of the default property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlAttribute(name = "Default")
	public String getDefault() {
		return _default;
	}

	/**
	 * Sets the value of the default property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDefault(String value) {
		this._default = value;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlAttribute
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setId(String value) {
		this.id = value;
	}

}

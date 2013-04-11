package edu.hawaii.ics.csdl.jupiter.file.preference;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author Michael Harris, TETN
 * 
 */
public class Phase {

	protected List<ColumnEntry> columnEntry;
	protected String name;

	/**
	 * Gets the value of the columnEntry property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the columnEntry property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getColumnEntry().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ColumnEntry }
	 * 
	 * 
	 */
	public Phase() {
	}

	@XmlElement(name = "ColumnEntry")
	public List<ColumnEntry> getColumnEntry() {
		return this.columnEntry;
	}

	public void setColumnEntry(List<ColumnEntry> columnEntry) {
		this.columnEntry = columnEntry;
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

	@Override
	public String toString() {
		return "Phase:\n ["
				+ (columnEntry != null ? "columnEntry=" + columnEntry + "\n "
						: "") + (name != null ? "name=" + name : "") + "]";
	}

}

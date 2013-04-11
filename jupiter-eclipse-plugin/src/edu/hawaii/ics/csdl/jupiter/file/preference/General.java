package edu.hawaii.ics.csdl.jupiter.file.preference;

import javax.xml.bind.annotation.XmlElement;

/**
 * Java Class for Storing General Preferences
 * 
 * @author Michael Harris
 * 
 * 
 */

public class General {

	private String updateUrl;
	private boolean enableUpdate;
	private boolean enableFilter;

	public General() {
	}

	@Override
	public String toString() {
		return "General:\n ["
				+ (updateUrl != null ? "updateUrl=" + updateUrl + ",\n " : "")
				+ "enableUpdate=" + enableUpdate + ", enableFilter="
				+ enableFilter + "]";
	}

	/**
	 * Gets the value of the updateUrl property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "UpdateUrl")
	public String getUpdateUrl() {
		return updateUrl;
	}

	/**
	 * Sets the value of the updateUrl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUpdateUrl(String value) {
		this.updateUrl = value;
	}

	/**
	 * Gets the value of the enableUpdate property.
	 * 
	 */
	@XmlElement(name = "EnableUpdate")
	public boolean isEnableUpdate() {
		return enableUpdate;
	}

	/**
	 * Sets the value of the enableUpdate property.
	 * 
	 */
	public void setEnableUpdate(boolean value) {
		this.enableUpdate = value;
	}

	/**
	 * Gets the value of the enableFilter property.
	 * 
	 */
	@XmlElement(name = "EnableFilter")
	public boolean isEnableFilter() {
		return enableFilter;
	}

	/**
	 * Sets the value of the enableFilter property.
	 * 
	 */
	public void setEnableFilter(boolean value) {
		this.enableFilter = value;
	}

}

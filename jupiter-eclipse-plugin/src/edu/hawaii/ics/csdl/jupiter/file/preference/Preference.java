package edu.hawaii.ics.csdl.jupiter.file.preference;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * Java class for Storing Preferences related to Review Plugin.
 * 
 * @author Michael Harris
 * 
 */
@XmlRootElement(name = "Preference")
public class Preference {

	
	private General general;

	private View view;

	public Preference() {
	}

	/**
	 * Gets the value of the general property.
	 * 
	 * @return possible object is {@link General }
	 * 
	 */
	@XmlElement(name = "General")
	public General getGeneral() {
		return general;
	}

	/**
	 * Sets the value of the general property.
	 * 
	 * @param value
	 *            allowed object is {@link General }
	 * 
	 */
	public void setGeneral(General general) {
		this.general = general;
	}

	/**
	 * Gets the value of the view property.
	 * 
	 * @return possible object is {@link View }
	 * 
	 */
	@XmlElement(name = "View")
	public View getView() {
		return view;
	}

	/**
	 * Sets the value of the view property.
	 * 
	 * @param value
	 *            allowed object is {@link View }
	 * 
	 */
	public void setView(View view) {
		this.view = view;
	}

	@Override
	public String toString() {
		return "Preference: \n ["
				+ (general != null ? "general=" + general + "\n " : "")
				+ (view != null ? "view=" + view : "") + "]";
	}

}

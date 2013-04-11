package edu.hawaii.ics.csdl.jupiter.file.preference;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


/**
 * 
 * @author Michael Harris, TETN
 * 
 */
public class View {

    private List<Phase> phase;
    private String _default;

    public View() {
	}
    /**
     * Gets the value of the phase property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phase property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Phase }
     * 
     * 
     */
    @XmlElement(name = "Phase")
    public List<Phase> getPhase() {
        return phase;
    }
    
    public void setPhase(List<Phase> phase) {
		this.phase = phase;
	}

    /**
     * Gets the value of the default property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @XmlAttribute(name = "default")
    public String getDefault() {
        return _default;
    }

    /**
     * Sets the value of the default property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefault(String value) {
        this._default = value;
    }
	@Override
	public String toString() {
		return "View [" + (phase != null ? "phase=" + phase + ", " : "")
				+ (_default != null ? "_default=" + _default : "") + "]";
	}

}

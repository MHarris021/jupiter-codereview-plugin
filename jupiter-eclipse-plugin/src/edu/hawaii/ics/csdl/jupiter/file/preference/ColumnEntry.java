package edu.hawaii.ics.csdl.jupiter.file.preference;

import javax.xml.bind.annotation.XmlAttribute;



/**
 * @author Michael Harris, TETN
 * 
 */

public class ColumnEntry {

    private Boolean enable;
    private String name;
    private Boolean resizable;
    private Integer width;

    /**
     * Gets the value of the enable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public ColumnEntry() {
	}
    
    @XmlAttribute
    public Boolean isEnable() {
        return enable;
    }

    /**
     * Sets the value of the enable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEnable(Boolean value) {
        this.enable = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
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
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the resizable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    @XmlAttribute
    public Boolean isResizable() {
        return resizable;
    }

    /**
     * Sets the value of the resizable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResizable(Boolean value) {
        this.resizable = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    @XmlAttribute
    public Integer getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWidth(Integer value) {
        this.width = value;
    }

	@Override
	public String toString() {
		return "ColumnEntry:\n ["
				+ (enable != null ? "enable=" + enable + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (resizable != null ? "resizable=" + resizable + ", " : "")
				+ (width != null ? "width=" + width : "") + "]";
	}

}

package edu.hawaii.ics.csdl.jupiter.file.review;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 
 * 
 */
@XmlRootElement(name="Review")
public class Review {

    private List<ReviewIssue> reviewIssues;
    private String id;

    
    public Review() {
	}
    
    @XmlElement(name="ReviewIssue")
    public List<ReviewIssue> getReviewIssues() {
        return this.reviewIssues;
    }
    
    public void setReviewIssues(List<ReviewIssue> reviewIssues) {
		this.reviewIssues = reviewIssues;
	}

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
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
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}

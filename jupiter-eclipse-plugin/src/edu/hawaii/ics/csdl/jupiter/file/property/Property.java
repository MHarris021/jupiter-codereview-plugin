package edu.hawaii.ics.csdl.jupiter.file.property;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 
 */
@XmlRootElement(name = "Property")
public class Property {

	private List<Review> reviews;

	/**
	 * Gets the value of the review property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the review property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getReview().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Review }
	 * 
	 * 
	 */
	@XmlElement(name = "Review")
	public List<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(List<Review> review) {
		this.reviews = review;
	}

	@Override
	public String toString() {
		return "Property [" + (reviews != null ? "reviews=" + reviews : "") + "]";
	}

}

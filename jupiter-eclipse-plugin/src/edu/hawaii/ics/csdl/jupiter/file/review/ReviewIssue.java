package edu.hawaii.ics.csdl.jupiter.file.review;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * 
 */
public class ReviewIssue {

	private ReviewIssueMeta reviewIssueMeta;
	private String reviewerId = "";
	private String assignedTo = "";
	private ReviewFile file;
	private String type = "";
	private String severity = "";
	private String summary = "";
	private String description = "";
	private String annotation = "";
	private String revision = "";
	private String resolution = "";
	private String status = "";
	private String id = "";

	public ReviewIssue() {
	}

	/**
	 * Gets the value of the reviewIssueMeta property.
	 * 
	 * @return possible object is {@link ReviewIssueMeta }
	 * 
	 */
	@XmlElement(name = "ReviewIssueMeta")
	public ReviewIssueMeta getReviewIssueMeta() {
		return reviewIssueMeta;
	}

	/**
	 * Sets the value of the reviewIssueMeta property.
	 * 
	 * @param value
	 *            allowed object is {@link ReviewIssueMeta }
	 * 
	 */
	public void setReviewIssueMeta(ReviewIssueMeta value) {
		this.reviewIssueMeta = value;
	}

	/**
	 * Gets the value of the reviewerId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "ReviewerId")
	public String getReviewerId() {
		return reviewerId;
	}

	/**
	 * Sets the value of the reviewerId property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReviewerId(String value) {
		this.reviewerId = value;
	}

	/**
	 * Gets the value of the assignedTo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "AssignedTo")
	public String getAssignedTo() {
		return assignedTo;
	}

	/**
	 * Sets the value of the assignedTo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAssignedTo(String value) {
		this.assignedTo = value;
	}

	/**
	 * Gets the value of the file property.
	 * 
	 * @return possible object is {@link ReviewFile }
	 * 
	 */
	@XmlElement(name = "File")
	public ReviewFile getFile() {
		return file;
	}

	/**
	 * Sets the value of the file property.
	 * 
	 * @param value
	 *            allowed object is {@link ReviewFile }
	 * 
	 */
	public void setFile(ReviewFile value) {
		this.file = value;
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "Type")
	public String getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setType(String value) {
		this.type = value;
	}

	/**
	 * Gets the value of the severity property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "Severity")
	public String getSeverity() {
		return severity;
	}

	/**
	 * Sets the value of the severity property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSeverity(String value) {
		this.severity = value;
	}

	/**
	 * Gets the value of the summary property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "Summary")
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the value of the summary property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSummary(String value) {
		this.summary = value;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "Description")
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value) {
		this.description = value;
	}

	/**
	 * Gets the value of the annotation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "Annotation")
	public String getAnnotation() {
		return annotation;
	}

	/**
	 * Sets the value of the annotation property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAnnotation(String value) {
		this.annotation = value;
	}

	/**
	 * Gets the value of the revision property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "Revision")
	public String getRevision() {
		return revision;
	}

	/**
	 * Sets the value of the revision property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRevision(String value) {
		this.revision = value;
	}

	/**
	 * Gets the value of the resolution property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "Resolution")
	public String getResolution() {
		return resolution;
	}

	/**
	 * Sets the value of the resolution property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setResolution(String value) {
		this.resolution = value;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "Status")
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStatus(String value) {
		this.status = value;
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

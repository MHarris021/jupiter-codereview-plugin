package edu.hawaii.ics.csdl.jupiter.file.property;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author Michael Harris, TETN
 * 
 */
public class Review {

	private String description;
	private String author;
	private CreationDate creationDate;
	private String directory = "";
	private Reviewers reviewers;
	private Files files;
	private List<FieldItem> fieldItems;
	private Filters filters;
	private String id;

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
	 * Gets the value of the author property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "Author")
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the value of the author property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAuthor(String value) {
		this.author = value;
	}

	/**
	 * Gets the value of the creationDate property.
	 * 
	 * @return possible object is {@link CreationDate }
	 * 
	 */
	@XmlElement(name = "CreationDate")
	public CreationDate getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the value of the creationDate property.
	 * 
	 * @param value
	 *            allowed object is {@link CreationDate }
	 * 
	 */
	public void setCreationDate(CreationDate value) {
		this.creationDate = value;
	}

	/**
	 * Gets the value of the directory property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	@XmlElement(name = "Directory")
	public String getDirectory() {
		return directory;
	}

	/**
	 * Sets the value of the directory property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDirectory(String value) {
		this.directory = value;
	}

	/**
	 * Gets the value of the reviewers property.
	 * 
	 * @return possible object is {@link Reviewers }
	 * 
	 */
	@XmlElement(name = "Reviewers")
	public Reviewers getReviewers() {
		return reviewers;
	}

	/**
	 * Sets the value of the reviewers property.
	 * 
	 * @param value
	 *            allowed object is {@link Reviewers }
	 * 
	 */
	public void setReviewers(Reviewers value) {
		this.reviewers = value;
	}

	/**
	 * Gets the value of the files property.
	 * 
	 * @return possible object is {@link Files }
	 * 
	 */
	@XmlElement(name = "Files")
	public Files getFiles() {
		return files;
	}

	/**
	 * Sets the value of the files property.
	 * 
	 * @param value
	 *            allowed object is {@link Files }
	 * 
	 */
	public void setFiles(Files value) {
		this.files = value;
	}

	@XmlElementWrapper(name = "FieldItems")
	@XmlElement(name = "FieldItem")
	public List<FieldItem> getFieldItems() {
		return fieldItems;
	}

	public void setFieldItems(List<FieldItem> value) {
		this.fieldItems = value;
	}

	/**
	 * Gets the value of the filters property.
	 * 
	 * @return possible object is {@link Filters }
	 * 
	 */
	@XmlElement(name = "Filters")
	public Filters getFilters() {
		return filters;
	}

	/**
	 * Sets the value of the filters property.
	 * 
	 * @param value
	 *            allowed object is {@link Filters }
	 * 
	 */
	public void setFilters(Filters value) {
		this.filters = value;
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

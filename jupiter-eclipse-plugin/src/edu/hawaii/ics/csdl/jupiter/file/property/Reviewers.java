package edu.hawaii.ics.csdl.jupiter.file.property;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Michael Harris, TETN
 * 
 */
public class Reviewers {

	private List<Entry> entries;

	public Reviewers() {
	}

	@XmlElement(name = "Entry")
	public List<Entry> getEntries() {
		return this.entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

}

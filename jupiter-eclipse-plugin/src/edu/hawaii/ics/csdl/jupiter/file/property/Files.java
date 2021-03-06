package edu.hawaii.ics.csdl.jupiter.file.property;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Michael Harris, TETN
 * 
 */

public class Files {

	private List<Entry> entries;

	public Files() {
	}

	@XmlElement(name = "Entry")
	public List<Entry> getEntries() {
		return this.entries;
	}

	public void setEntries(List<Entry> entry) {
		this.entries = entry;
	}



}

package edu.hawaii.ics.csdl.jupiter.file.property;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Michael Harris, TETN
 * 
 */
public class Filters {

	private List<Phase> phases;

	public Filters() {
	}

	@XmlElement(name = "Phase")
	public List<Phase> getPhases() {
		return this.phases;
	}

	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}

}

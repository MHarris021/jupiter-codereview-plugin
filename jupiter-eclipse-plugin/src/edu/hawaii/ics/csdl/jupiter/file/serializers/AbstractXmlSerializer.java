package edu.hawaii.ics.csdl.jupiter.file.serializers;

import javax.xml.bind.JAXBContext;


public abstract class AbstractXmlSerializer<T> implements ISerializer<T> {

	public static final String JAXB_FORMATTED_OUTPUT = "jaxb.formatted.output";
	private JAXBContext jaxbContext;

	public AbstractXmlSerializer() {
		super();
	}

	public JAXBContext getJaxbContext() {
		return jaxbContext;
	}

	public void setJaxbContext(JAXBContext jaxbContext) {
		this.jaxbContext = jaxbContext;
	}

}
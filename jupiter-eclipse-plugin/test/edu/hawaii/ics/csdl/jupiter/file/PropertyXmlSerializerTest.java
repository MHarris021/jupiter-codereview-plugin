package edu.hawaii.ics.csdl.jupiter.file;

import static org.junit.Assert.*;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.hawaii.ics.csdl.jupiter.file.property.Property;
import edu.hawaii.ics.csdl.jupiter.file.serializers.AbstractXmlSerializer;

public class PropertyXmlSerializerTest {
	
	private JAXBContext jaxbContext;
	
	@Before
	public void setUp() throws Exception {
		jaxbContext = JAXBContext.newInstance(Property.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDeSerializeProperty() throws JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		File file = new File("property.xml");
		Property property = (Property) unmarshaller.unmarshal(file);
		assertNotNull(property);
		
	}

	@Test
	public void testSerializeProperty() throws JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		File file = new File("property.xml");
		Property property = (Property) unmarshaller.unmarshal(file);
		file = new File("propertyCycle.xml");
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(AbstractXmlSerializer.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(property, file);
		assertNotNull(property);
		
	}

}

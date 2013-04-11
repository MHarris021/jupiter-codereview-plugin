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

import edu.hawaii.ics.csdl.jupiter.file.preference.Preference;
import edu.hawaii.ics.csdl.jupiter.file.serializers.AbstractXmlSerializer;

/**
 * 
 * @author Michael Harris, TETN
 * 
 */

public class PrefXmlSerializerTest {

	private JAXBContext jaxbContext;

	@Before
	public void setUp() throws Exception {
		jaxbContext = JAXBContext.newInstance(Preference.class);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadPreference() throws JAXBException {
		File file = new File("preference.xml");
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Preference preference = (Preference) unmarshaller.unmarshal(file);
		assertNotNull(preference);
		System.out.println(preference.toString());
	}

	@Test
	public void testSerializePreference() throws JAXBException {
		Preference preference = new Preference();
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(AbstractXmlSerializer.JAXB_FORMATTED_OUTPUT,
				Boolean.TRUE);
		File file = new File("preferencetest.xml");
		marshaller.marshal(preference, file);
	}

	@Test
	public void testPreferenceSerializationCycle() throws JAXBException {
		File file = new File("preference.xml");
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Preference preference = (Preference) unmarshaller.unmarshal(file);
		assertNotNull(preference);

		file = new File("preferenceCycle.xml");
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(AbstractXmlSerializer.JAXB_FORMATTED_OUTPUT,
				Boolean.TRUE);
		marshaller.marshal(preference, file);
		System.out.println(preference.toString());
	}

}

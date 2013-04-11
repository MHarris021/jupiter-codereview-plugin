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

import edu.hawaii.ics.csdl.jupiter.file.review.Review;
import edu.hawaii.ics.csdl.jupiter.file.serializers.AbstractXmlSerializer;

public class ReviewXmlSerializerTest {
	
	private JAXBContext jaxbContext;
	
	@Before
	public void setUp() throws Exception {
		jaxbContext = JAXBContext.newInstance(Review.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDeSerializeProperty() throws JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		File file = new File("web 1-tetn.review");
		Review review = (Review) unmarshaller.unmarshal(file);
		assertNotNull(review);
		
	}

	@Test
	public void testSerializeProperty() throws JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		File file = new File("web 1-tetn.review");
		Review review = (Review) unmarshaller.unmarshal(file);
		file = new File("web 1-tetnCycle.review");
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(AbstractXmlSerializer.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(review, file);
		assertNotNull(review);
		
	}

}

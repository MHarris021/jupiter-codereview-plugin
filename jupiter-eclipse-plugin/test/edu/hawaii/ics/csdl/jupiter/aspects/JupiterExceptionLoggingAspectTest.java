package edu.hawaii.ics.csdl.jupiter.aspects;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.hawaii.ics.csdl.jupiter.ReviewPluginConfiguration;
import edu.hawaii.ics.csdl.jupiter.file.property.Property;
import edu.hawaii.ics.csdl.jupiter.file.serializers.PropertySerializer;
import edu.hawaii.ics.csdl.jupiter.file.serializers.SerializerException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ReviewPluginConfiguration.class,
		JupiterExeceptionLoggingAspectConfiguration.class })
public class JupiterExceptionLoggingAspectTest {

	@Autowired
	private JupiterExceptionLoggingAspect aspect;

	@Autowired
	private PropertySerializer propertySerializer;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = SerializerException.class)
	public void test() throws SerializerException {
		assertNotNull(aspect);
		assertNotNull(propertySerializer);

		Property property = propertySerializer.deserialize(new File("f"));
		assertNull(property);

	}

}

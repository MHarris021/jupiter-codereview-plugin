package edu.hawaii.ics.csdl.jupiter;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReviewPluginTest {

	
	private ReviewPluginImpl plugin;

	@Before
	public void setUp() throws Exception {
		plugin = ReviewPluginImpl.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		plugin = null;
	}

	@Test
	public void test() {
		
		assertNotNull(plugin.getVersionIdentifier());
		System.err.println("Version:" + plugin.getVersionIdentifier().toString());
		assertNotNull(plugin);
	}

}

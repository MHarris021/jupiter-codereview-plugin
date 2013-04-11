package edu.hawaii.ics.csdl.jupiter.configuration;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.hawaii.ics.csdl.jupiter.ReviewPluginConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ReviewPluginConfiguration.class)
public class JupiterConfigurationTest implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		assertNotNull(beanNames);
		for(String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}

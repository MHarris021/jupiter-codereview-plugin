package edu.hawaii.ics.csdl.jupiter.configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import edu.hawaii.ics.csdl.jupiter.file.preference.Preference;
import edu.hawaii.ics.csdl.jupiter.file.property.Property;
import edu.hawaii.ics.csdl.jupiter.file.review.Review;
import edu.hawaii.ics.csdl.jupiter.file.serializers.ISerializer;
import edu.hawaii.ics.csdl.jupiter.file.serializers.XmlSerializer;

@Configuration
@ComponentScan(basePackages="edu.hawaii.ics.csdl.jupiter.file.serializers")
public class SerializationConfiguration implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

	}

	@Bean(name="iPreferenceSerializer")
	public ISerializer<Preference> xmlPreferenceSerializer() {
		JAXBContext context = applicationContext.getBean("preferenceContext",
				JAXBContext.class);
		XmlSerializer<Preference> xmlSerializer = null;
		try {
			xmlSerializer = new XmlSerializer<Preference>(context);
		} catch (JAXBException e) {
			throw new BeanCreationException(
					"Could not instantiate XmlPreferenceSerializer", e);
		}
		return xmlSerializer;
	}

	@Bean(name="iPropertySerializer")
	public ISerializer<Property> xmlPropertySerializer() {
		JAXBContext context = applicationContext.getBean("propertyContext",
				JAXBContext.class);
		XmlSerializer<Property> xmlSerializer = null;
		try {
			xmlSerializer = new XmlSerializer<Property>(context);
		} catch (JAXBException e) {
			throw new BeanCreationException(
					"Could not instantiate XmlPropertySerializer", e);
		}
		return xmlSerializer;
	}

	@Bean(name="iReviewSerializer")
	public ISerializer<Review> xmlReviewSerializer() {
		JAXBContext context = applicationContext.getBean("reviewContext",
				JAXBContext.class);
		XmlSerializer<Review> xmlSerializer = null;
		try {
			xmlSerializer = new XmlSerializer<Review>(context);
		} catch (JAXBException e) {
			throw new BeanCreationException(
					"Could not instantiate XmlReviewSerializer", e);
		}
		return xmlSerializer;
	}

}

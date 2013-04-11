package edu.hawaii.ics.csdl.jupiter.configuration.factorybeans;

import javax.xml.bind.JAXBContext;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import edu.hawaii.ics.csdl.jupiter.file.review.Review;

@Component(value="reviewContext")
public class ReviewContextFactoryBean implements FactoryBean<JAXBContext> {

	public JAXBContext getObject() throws Exception {
		return JAXBContext.newInstance(Review.class);
	}

	public Class<?> getObjectType() {
		return JAXBContext.class;
	}

	public boolean isSingleton() {
		return true;
	}

}

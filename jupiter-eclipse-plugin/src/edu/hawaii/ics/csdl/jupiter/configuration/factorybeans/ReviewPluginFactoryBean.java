package edu.hawaii.ics.csdl.jupiter.configuration.factorybeans;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;

@Component
public class ReviewPluginFactoryBean implements FactoryBean<ReviewPluginImpl> {

	public ReviewPluginImpl getObject() throws Exception {
		return ReviewPluginImpl.getInstance();
	}

	public Class<?> getObjectType() {
		return ReviewPluginImpl.class;
	}

	public boolean isSingleton() {
		return true;
	}

}

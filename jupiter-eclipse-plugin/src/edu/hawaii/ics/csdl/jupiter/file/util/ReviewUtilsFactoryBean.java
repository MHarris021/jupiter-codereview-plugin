package edu.hawaii.ics.csdl.jupiter.file.util;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ReviewUtilsFactoryBean implements FactoryBean<ReviewUtils> {

	public ReviewUtils getObject() throws Exception {
		
		return ReviewUtils.getInstance();
	}

	public Class<?> getObjectType() {
		return ReviewUtils.class;
	}

	public boolean isSingleton() {
		return true;
	}

}

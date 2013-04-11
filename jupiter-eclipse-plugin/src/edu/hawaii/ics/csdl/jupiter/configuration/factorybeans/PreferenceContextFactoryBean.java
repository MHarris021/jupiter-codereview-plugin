package edu.hawaii.ics.csdl.jupiter.configuration.factorybeans;

import javax.xml.bind.JAXBContext;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import edu.hawaii.ics.csdl.jupiter.file.preference.Preference;

@Component(value="preferenceContext")
public class PreferenceContextFactoryBean implements FactoryBean<JAXBContext> {

	public JAXBContext getObject() throws Exception {
		return JAXBContext.newInstance(Preference.class);
	}

	public Class<?> getObjectType() {
		return JAXBContext.class;
	}

	public boolean isSingleton() {
		return true;
	}

}

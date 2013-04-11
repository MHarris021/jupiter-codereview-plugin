package edu.hawaii.ics.csdl.jupiter.file.serializers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XmlSerializer<T> extends AbstractXmlSerializer<T> implements
		ISerializer<T> {

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	public XmlSerializer(JAXBContext context) throws JAXBException {
		setJaxbContext(context);
		createMarshaller(new HashMap<String, Object>());
		createUnmarshaller(new HashMap<String, Object>());
	}

	protected void createUnmarshaller(Map<String, Object> map)
			throws JAXBException {
		unmarshaller = getJaxbContext().createUnmarshaller();
		Set<Entry<String, Object>> propertieSet = map.entrySet();
		for (Entry<String, Object> propertyEntry : propertieSet) {
			unmarshaller.setProperty(propertyEntry.getKey(),
					propertyEntry.getValue());
		}

	}

	protected void createMarshaller(Map<String, Object> map)
			throws JAXBException {
		marshaller = getJaxbContext().createMarshaller();
		Set<Entry<String, Object>> propertieSet = map.entrySet();
		for (Entry<String, Object> propertyEntry : propertieSet) {
			marshaller.setProperty(propertyEntry.getKey(),
					propertyEntry.getValue());
		}

	}

	@SuppressWarnings("unchecked")
	public T deserialize(File file) throws SerializerException {
		T item = null;
		try {
			item = (T) unmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			throw new SerializerException(e);
		}
		return item;
	}

	public void serialize(T item, File file) throws SerializerException {

		try {
			marshaller.marshal(item, file);
		} catch (JAXBException e) {
			throw new SerializerException(e);
		}
	}

}

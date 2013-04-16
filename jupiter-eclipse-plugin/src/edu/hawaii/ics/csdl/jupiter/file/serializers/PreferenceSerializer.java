package edu.hawaii.ics.csdl.jupiter.file.serializers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.FileLocator;
import org.springframework.stereotype.Component;

import edu.hawaii.ics.csdl.jupiter.file.preference.Preference;

/**
 * Provides the function to read and write information in the config.xml file.
 * 
 * @author Takuya Yamashita
 * @version $Id: PrefXmlSerializer.java 144 2008-10-19 22:49:03Z jsakuda $
 */
@Component
public class PreferenceSerializer implements ISerializer<Preference> {

	/** The preference file. */
	private static final String PREFERENCE_XML_FILE = "preference.xml";

	@Resource
	private ISerializer<Preference> iPreferenceSerializer;

	@Resource
	private File stateLocationXmlFile;

	@Resource
	private URL pluginUrl;

	/**
	 * Prohibits the instantiation from clients.
	 * 
	 * @throws JAXBException
	 */

	public PreferenceSerializer() {

	}

	public PreferenceSerializer(ISerializer<Preference> serializer) {
		this.iPreferenceSerializer = serializer;
	}

	/**
	 * Loads <code>Preference</code> from the PREFERENCE_XML_FILE file in the
	 * plug-in directory.
	 * 
	 * @return Returns the <code>Preference</code> or null if it could not be
	 *         loaded from file.
	 * @throws SerializerException
	 */
	public Preference loadPreference() throws SerializerException {
		if (!stateLocationXmlFile.exists()) {

			try {
				downloadDefaultPreferences(stateLocationXmlFile);
			} catch (Exception e) {
				throw new SerializerException(e);
			}
		}
		Preference preference = deserialize(stateLocationXmlFile);
		return preference;
	}

	protected void downloadDefaultPreferences(File stateLocationXmlFile)
			throws IOException {
		URL xmlUrl = FileLocator.toFileURL(new URL(pluginUrl,
				PREFERENCE_XML_FILE));
		FileUtils.copyURLToFile(xmlUrl, stateLocationXmlFile);
	}

	public void serialize(Preference preference) throws SerializerException {
		serialize(preference, stateLocationXmlFile);

	}

	public Preference deserialize(File file) throws SerializerException {
		Preference preference = iPreferenceSerializer.deserialize(file);
		return preference;
	}

	public void serialize(Preference preference, File file)
			throws SerializerException {
		iPreferenceSerializer.serialize(preference, file);
	}
}

package edu.hawaii.ics.csdl.jupiter.file.serializers;

import java.io.File;
import java.net.URL;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.FileLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.hawaii.ics.csdl.jupiter.file.FileResource;
import edu.hawaii.ics.csdl.jupiter.file.property.Property;
import edu.hawaii.ics.csdl.jupiter.file.property.Review;

/**
 * Provides an utility for property config XML.
 * 
 * @author Takuya Yamashita
 * @version $Id: PropertyXmlSerializer.java 179 2010-07-01 09:54:42Z jsakuda $
 */
@Component
public class PropertySerializer implements ISerializer<Property> {
	private static final String DEFAULT_PROPERTY_XML_FILE = "property.xml";
	/** The property XML file name. */
	public static final String PROPERTY_XML_FILE = ".jupiter";

	@Resource
	private ISerializer<Property> iPropertySerializer;

	@Resource
	private URL pluginInstallUrl;
	
	@Autowired
	private FileResource fileResource;

	public PropertySerializer() {
	}

	/**
	 * Prohibits instantiation.
	 * 
	 */
	public PropertySerializer(ISerializer<Property> serializer) {

		this.iPropertySerializer = serializer;
	}

	/**
	 * Creates the new <code>Property</code> config instance in the
	 * <code>IProject</code>.
	 * 
	 * @param project
	 *            the project
	 * @return the new <code>Property</code> instance.
	 * @throws SerializerException
	 */
	public Property newProperty(IProject project) throws SerializerException {
		IFile jupiterConfigIFile = project.getFile(PROPERTY_XML_FILE);
		File jupiterConfigFile = jupiterConfigIFile.getLocation().toFile();
		Property property = null;
		if (!jupiterConfigFile.exists()) {
			if (fileResource.getActiveProject().getName()
					.equals(project.getName())) {
				try {
					jupiterConfigFile = copyDefaultConfigFileTo(jupiterConfigFile);
					jupiterConfigIFile.refreshLocal(IResource.DEPTH_ONE, null);
				} catch (Exception e) {
					throw new SerializerException(
							"Could not create Property Object: ", e);

				}
			}
		}
		property = deserialize(jupiterConfigFile);

		return property;
	}

	/**
	 * Serializes a <code>Property</code> to the jupiter config.
	 * 
	 * @param property
	 *            The properties to save.
	 * @param project
	 *            The project that the property is for.
	 * @throws SerializerException
	 */
	public void serialize(Property property, File file)
			throws SerializerException {
		iPropertySerializer.serialize(property, file);
	}

	public Property deserialize(File file) throws SerializerException {
		Property property = iPropertySerializer.deserialize(file);
		return property;

	}

	/**
	 * Copies default config file in the <code>Project</code>. Leave the current
	 * config file in the project if the file already exists.
	 * 
	 * @param outputPropertyFile
	 *            the output property file.
	 * @return the config file <code>File</code> instance.
	 */
	public File copyDefaultConfigFileTo(File outputPropertyFile)
			throws SerializerException {
		URL pluginUrl = pluginInstallUrl;
		URL xmlUrl;
		try {
			xmlUrl = FileLocator.toFileURL(new URL(pluginUrl,
					DEFAULT_PROPERTY_XML_FILE));
			File sourceXmlFile = FileUtils.toFile(xmlUrl);
			FileUtils.copyFile(sourceXmlFile, outputPropertyFile);
		} catch (Exception e) {
			throw new SerializerException(
					"Could not copy the default configuration", e);
		}

		return outputPropertyFile;
	}

	/**
	 * Loads the default review from property.xml.
	 * 
	 * @return Returns the <code>Review</code> object or null.
	 * @throws SerializerException
	 */
	public Review cloneDefaultReview() throws SerializerException {
		URL pluginUrl = pluginInstallUrl;

		Review review = null;
		URL xmlUrl = null;
		try {
			xmlUrl = FileLocator.toFileURL(new URL(pluginUrl,
					DEFAULT_PROPERTY_XML_FILE));
		} catch (Exception e) {
			throw new SerializerException(
					"Could not copy the default review: ", e);
		}

		File file = FileUtils.toFile(xmlUrl);
		Property property = deserialize(file);
		review = property.getReviews().get(0);
		return review;
	}
}

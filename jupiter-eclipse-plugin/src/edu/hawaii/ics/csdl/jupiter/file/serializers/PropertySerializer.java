package edu.hawaii.ics.csdl.jupiter.file.serializers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.hawaii.ics.csdl.jupiter.ReviewException;
import edu.hawaii.ics.csdl.jupiter.ReviewPlugin;
import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;
import edu.hawaii.ics.csdl.jupiter.file.FileResource;
import edu.hawaii.ics.csdl.jupiter.file.property.Property;
import edu.hawaii.ics.csdl.jupiter.file.property.Review;
import edu.hawaii.ics.csdl.jupiter.util.JupiterLogger;

/**
 * Provides an utility for property config XML.
 * 
 * @author Takuya Yamashita
 * @version $Id: PropertyXmlSerializer.java 179 2010-07-01 09:54:42Z jsakuda $
 */
@Component
public class PropertySerializer implements ISerializer<Property> {
	/** Jupiter logger */
	private static JupiterLogger log = JupiterLogger.getLogger();

	private static final String DEFAULT_PROPERTY_XML_FILE = "property.xml";
	/** The property XML file name. */
	public static final String PROPERTY_XML_FILE = ".jupiter";

	@Resource
	private ISerializer<Property> iPropertySerializer;
	
	@Autowired
	private ReviewPlugin plugin;

	public PropertySerializer() {
	}
	
	/**
	 * Prohibits instantiation.
	 * 
	 * @throws JAXBException
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
	 * @throws ReviewException
	 *             if an error occurs during the new document creation.
	 * @throws SerializerException
	 */
	public Property newProperty(IProject project) throws ReviewException,
			SerializerException {
		IFile jupiterConfigIFile = project.getFile(PROPERTY_XML_FILE);
		File jupiterConfigFile = jupiterConfigIFile.getLocation().toFile();
		Property property = null;
		if (!jupiterConfigFile.exists()) {
			if (FileResource.getActiveProject().getName()
					.equals(project.getName())) {
				try {
					jupiterConfigFile = copyDefaultConfigFileTo(jupiterConfigFile);
					jupiterConfigIFile.refreshLocal(IResource.DEPTH_ONE, null);
				} catch (CoreException e) {
					log.error(e);
				} catch (IOException e) {
					log.error(e);
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
	 * @throws IOException
	 *             if problems occur.
	 * @throws CoreException
	 *             if problems occur.
	 */
	public File copyDefaultConfigFileTo(File outputPropertyFile)
			throws IOException, CoreException {
		URL pluginUrl = plugin.getInstallURL();
		URL xmlUrl = FileLocator.toFileURL(new URL(pluginUrl,
				DEFAULT_PROPERTY_XML_FILE));

		File sourceXmlFile = new File(xmlUrl.getFile());
		FileUtils.copyFile(sourceXmlFile, outputPropertyFile);
		return outputPropertyFile;
	}

	/**
	 * Loads the default review from property.xml.
	 * 
	 * @return Returns the <code>Review</code> object or null.
	 * @throws SerializerException
	 */
	public Review cloneDefaultReview() throws SerializerException {
		URL pluginUrl = ReviewPluginImpl.getInstance().getInstallURL();

		Review review = null;
		try {
			URL xmlUrl = FileLocator.toFileURL(new URL(pluginUrl,
					DEFAULT_PROPERTY_XML_FILE));

			File file = new File(xmlUrl.toURI());
			Property property = deserialize(file);
			// there should only be the default review in the list
			review = property.getReviews().get(0);
		} catch (IOException e) {
			log.error(e);
		} catch (URISyntaxException e) {
			log.error(e);
		}
		return review;
	}
}

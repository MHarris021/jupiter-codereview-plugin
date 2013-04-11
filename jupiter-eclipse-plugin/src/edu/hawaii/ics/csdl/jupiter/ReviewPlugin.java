package edu.hawaii.ics.csdl.jupiter;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

public interface ReviewPlugin {

	/**
	 * Gets the plugin's install URL.
	 * 
	 * @return the <code>URL</code> instance.
	 */
	public URL getInstallURL();

	/**
	 * Gets the <code>Bundle</code> of this plug-in.
	 * 
	 * @return the <code>Bundle</code> of this plug-in.
	 */
	public Bundle getReviewBundle();

	/**
	 * Gets the <code>PluginVersionIdentifier</code> instance.
	 * 
	 * @return the <code>PluginVersionIdentifier</code> instance.
	 */
	public Version getVersionIdentifier();

	/**
	 * Notifies the listeners who implement the <code>IReviewListener</code>.
	 * This method can be invoked anytime, but it is designed for the function
	 * to let listeners to know the review event is invoked.
	 * 
	 * @param type
	 *            the type of the review event.
	 * @param kind
	 *            the kind of the type.
	 */
	public void notifyListeners(int type, int kind);

}
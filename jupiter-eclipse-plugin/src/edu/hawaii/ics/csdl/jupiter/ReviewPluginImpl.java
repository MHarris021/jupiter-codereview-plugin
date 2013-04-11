package edu.hawaii.ics.csdl.jupiter;

import java.net.URL;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.hawaii.ics.csdl.jupiter.event.IReviewListener;
import edu.hawaii.ics.csdl.jupiter.event.ReviewEvent;
import edu.hawaii.ics.csdl.jupiter.event.ReviewIssueModelListenerAdapter;
import edu.hawaii.ics.csdl.jupiter.event.ReviewSelectionListener;
import edu.hawaii.ics.csdl.jupiter.event.WindowListenerAdapter;
import edu.hawaii.ics.csdl.jupiter.file.PreferenceResource;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModel;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModelManager;
import edu.hawaii.ics.csdl.jupiter.ui.marker.MarkerResourceChangeListener;
import edu.hawaii.ics.csdl.jupiter.ui.marker.MarkerTextPartListener;
import edu.hawaii.ics.csdl.jupiter.ui.menu.UndoReviewIssueManager;
import edu.hawaii.ics.csdl.jupiter.ui.preference.FilterPreferencePage;
import edu.hawaii.ics.csdl.jupiter.ui.preference.GeneralPreferencePage;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorView;
import edu.hawaii.ics.csdl.jupiter.util.JupiterLogger;

/**
 * Provides one time Code ReviewIssue plug-in instantiation (singleton).
 * Contains the <code>ReviewIssueModel</code> model instance.
 * 
 * @author Takuya Yamashita
 * @version $Id: ReviewPluginImpl.java 184 2012-07-08 01:31:50Z jsakuda $
 */
public class ReviewPluginImpl extends AbstractUIPlugin implements IStartup,
		ReviewPlugin {

	public static final String PLUGIN_ID = "edu.hawaii.ics.csdl.jupiter";

	/** The log instance to record log */
	private static JupiterLogger log = JupiterLogger.getLogger();
	/** The <code>ReviewPluginImpl</code> singleton instance. */
	private static ReviewPluginImpl plugin;

	@Autowired
	private ListenerList listenerList;

	private ApplicationContext applicationContext;

	@Autowired
	private ReviewIssueModelManager reviewIssueModelManager;

	@Autowired
	private IWorkspace workspace;

	/**
	 * Instantiates <code>ReviewPluginImpl</code> itself and initializes
	 * resource bundle. Called by Eclipse platform. Clients should not
	 * instantiate this.
	 */
	public ReviewPluginImpl() {
		super();
		plugin = this;
	}

	/**
	 * Provides this <code>ReviewPluginImpl</code> instance.
	 * 
	 * @return The <code>ReviewPluginImpl</code> instance.
	 */
	public static ReviewPluginImpl getInstance() {
		return plugin;
	}

	/**
	 * Initializes a preference store with default preference values for this
	 * plug-in. This method could be overridden due to AbstractUIPlugin abstract
	 * class. It's because it is empty protected method in the AbstractUIPlugin.
	 * 
	 * @param store
	 *            the preference store to fill
	 * 
	 * @see AbstractUIPlugin
	 */
	@Override
	protected void initializeDefaultPreferences(IPreferenceStore store) {
		// Stores the preference page default values.
		PreferenceResource preferenceResource = applicationContext
				.getBean(PreferenceResource.class);
		String enableUpdateStoreKey = GeneralPreferencePage.ENABLE_UPDATE_KEY;
		store.setDefault(enableUpdateStoreKey,
				preferenceResource.getEnableUpdate());
		String updateUrlStoreKey = GeneralPreferencePage.UPDATE_URL_KEY;
		store.setDefault(updateUrlStoreKey, preferenceResource.getUpdateUrl());
		store.setDefault(FilterPreferencePage.ENABLE_FILTER_STORE_KEY,
				preferenceResource.getEnableFilter());
	}

	/**
	 * Check if new update plug-in is available on the net.
	 * 
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */

	public void earlyStartup() {
		ReviewEditorView.setViewEnable(false);

		initializeListeners();
		log.info("Jupiter Review Plugin is up.");
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		applicationContext = new AnnotationConfigApplicationContext(
				ReviewPluginConfiguration.class);

	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * Initializes listeners.
	 * 
	 */
	private void initializeListeners() {
		workspace.addResourceChangeListener(new MarkerResourceChangeListener(),
				IResourceChangeEvent.POST_CHANGE);
		IWorkbench workbench = getWorkbench();
		workbench.addWindowListener(new WindowListenerAdapter());
		IWorkbenchWindow[] workbenchWindows = workbench.getWorkbenchWindows();
		for (IWorkbenchWindow workbenchWindow : workbenchWindows) {
			IWorkbenchPage page = workbenchWindow.getActivePage();
			page.addSelectionListener(new ReviewSelectionListener());
			page.addPartListener(new MarkerTextPartListener());

		}
		ReviewIssueModel model = reviewIssueModelManager.getCurrentModel();
		model.addListener(new ReviewIssueModelListenerAdapter(null, null));
		model.addListener(new UndoReviewIssueManager());
	}

	/**
	 * Creates a image descriptor form the given path. The path should be the
	 * relative path from a project root.
	 * 
	 * @param path
	 *            the relative path from a project root.
	 * 
	 * @return the newly created image descriptor.
	 */
	public static ImageDescriptor createImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.hawaii.ics.csdl.jupiter.ReviewPlugin#getInstallURL()
	 */
	public URL getInstallURL() {
		return plugin.getBundle().getEntry("/");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.hawaii.ics.csdl.jupiter.ReviewPlugin#getReviewBundle()
	 */
	public Bundle getReviewBundle() {
		return plugin.getBundle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.hawaii.ics.csdl.jupiter.ReviewPlugin#getVersionIdentifier()
	 */
	public Version getVersionIdentifier() {
		String bundleVersionKey = org.osgi.framework.Constants.BUNDLE_VERSION;
		String version = (String) plugin.getBundle().getHeaders()
				.get(bundleVersionKey);
		return new Version(version);
	}

	/**
	 * Adds the IReviewListener's implementing class to be notified when This
	 * model is changed.
	 * 
	 * @param listener
	 *            Description of the Parameter
	 */
	public void addListener(IReviewListener listener) {
		listenerList.add(listener);
	}

	/**
	 * Remove the IReviewListener's implementing class from this listener list.
	 * 
	 * @param listener
	 *            Description of the Parameter
	 */
	public void removeListener(IReviewListener listener) {
		listenerList.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.hawaii.ics.csdl.jupiter.ReviewPlugin#notifyListeners(int, int)
	 */
	public void notifyListeners(int type, int kind) {
		log.debug("review event type: " + type);
		log.debug("review event kind: " + kind);
		ReviewEvent event = new ReviewEvent(type, kind);
		Object[] listeners = listenerList.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			((IReviewListener) listeners[i]).reviewInvoked(event);
		}
	}
}

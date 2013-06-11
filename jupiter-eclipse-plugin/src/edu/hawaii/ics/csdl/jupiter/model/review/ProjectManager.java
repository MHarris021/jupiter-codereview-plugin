package edu.hawaii.ics.csdl.jupiter.model.review;

import org.eclipse.core.resources.IProject;

import edu.hawaii.ics.csdl.jupiter.file.FileResource;

/**
 * Provides the singleton project manager.
 * 
 * @author Takuya Yamashita
 * @version $Id: ProjectManager.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class ProjectManager implements IReviewModelElementChangeListener {

	private IProject project;

	/**
	 * Sets default project Prohibits clients from instantiating this. This
	 * project instance could be <code>null</code> if an active editor does not
	 * exist or <code>IFileEditorInput</code> instance does not exist (meaning
	 * the case that welcome page is opened, for example).
	 */

	public ProjectManager(FileResource fileResource) {
		setProject(fileResource.getActiveProject());
	}

	/**
	 * Sets the IProject if the notified object is the instance of the IProject.
	 * 
	 * @param object
	 *            The object to be notified.
	 */
	public void elementChanged(Object object) {
		if (object instanceof IProject) {
			setProject((IProject) object);
		}
	}

	/**
	 * Sets the <code>IProject</code> instance.
	 * 
	 * @param project
	 *            the project.
	 */
	private void setProject(IProject project) {
		this.project = project;
	}

	/**
	 * Gets the <code>IProject</code> instance. Clients should check
	 * <code>null</code> before using the <code>IProject</code> instance.
	 * 
	 * @return the project.
	 */
	public IProject getProject() {
		return this.project;
	}
}

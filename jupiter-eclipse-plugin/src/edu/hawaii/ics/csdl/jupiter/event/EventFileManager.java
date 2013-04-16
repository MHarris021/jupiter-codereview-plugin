package edu.hawaii.ics.csdl.jupiter.event;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.springframework.beans.factory.annotation.Autowired;

import edu.hawaii.ics.csdl.jupiter.file.FileResource;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewModel;

/**
 * Provides file event manager. Clients can get the latest event associated
 * file.
 * 
 * @author Takuya Yamashita
 * @version $Id: EventFileManager.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class EventFileManager {

	private String filePath;

	@Autowired
	private ReviewModel reviewModel;

	@Autowired
	private FileResource fileResource;

	/**
	 * Prohibits clients from instantiating this.
	 */
	public EventFileManager(ReviewModel reviewModel) {
		this.filePath = getSelectedFile();
		this.reviewModel = reviewModel;
	}

	/**
	 * Gets the selected file path. Returns empty string if there are not an
	 * applicable path.
	 * 
	 * @return The selected file path.
	 */
	private String getSelectedFile() {
		String file = "";
		IFile selectedFile = fileResource.getSelectedIFile();
		if (selectedFile != null) {
			IPath selectedPath = selectedFile.getLocation();
			if (selectedPath != null) {
				file = selectedPath.toFile().toString();
			}
		}
		return file;
	}

	/**
	 * Sets the event associated file path.
	 * 
	 * @param filePath
	 *            the fully qualified file path.
	 */
	public void setEventFilePath(String filePath) {
		IProject project = reviewModel.getProjectManager().getProject();
		if (filePath != null && project != null) {
			filePath = (!filePath.equals("")) ? filePath : File.separator;
			this.filePath = new File(project.getLocation().toString(), filePath)
					.toString();
		} else {
			this.filePath = getSelectedFile();
		}
	}

	/**
	 * Gets the event associated file path. Returns empty string if there is no
	 * associated file.
	 * 
	 * @return the fully qualified event associated file path. Returns empty
	 *         string if there is no associated file.
	 */
	public String getEventFilePath() {
		return this.filePath;
	}
}

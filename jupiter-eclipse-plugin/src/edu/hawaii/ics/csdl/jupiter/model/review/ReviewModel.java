package edu.hawaii.ics.csdl.jupiter.model.review;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides review model. Clients may get the singleton instance. All get's
 * methods are guaranteed to return non-null and updated information. This may
 * used to retrieved the review issue model through ReviewIssueManager, the
 * column data model through ColumnDataModelManager.
 * 
 * @author Takuya Yamashita
 * @version $Id: ReviewModel.java 81 2008-02-17 08:06:25Z jsakuda $
 */
public class ReviewModel {
	private PhaseManager phaseManager;
	private ReviewIdManager reviewIdManager;
	private ReviewerIdManager reviewerIdManager;
	private ProjectManager projectManager;
	private List<IReviewModelElementChangeListener> listeners;

	/**
	 * Prohibits clients from instantiating this.
	 */
	public ReviewModel(PhaseManager phaseManager,
			ProjectManager projectManager, ReviewIdManager reviewIdManager,
			ReviewerIdManager reviewerIdManager) {
		this.listeners = new ArrayList<IReviewModelElementChangeListener>();
		this.phaseManager = phaseManager;
		this.projectManager = projectManager;
		this.reviewIdManager = reviewIdManager;
		this.reviewerIdManager = reviewerIdManager;
		addIReviewModelElementListener(phaseManager);
		addIReviewModelElementListener(reviewIdManager);
		addIReviewModelElementListener(reviewerIdManager);
		addIReviewModelElementListener(projectManager);
	}

	public ReviewModel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the <code>ProjectManager</code> instance.
	 * 
	 * @return the <code>ProjectManager</code> instance.
	 */
	public ProjectManager getProjectManager() {
		return projectManager;
	}

	/**
	 * Gets the <code>ReviewIdManager</code> instance.
	 * 
	 * @return the <code>ReviewIdManager</code> instance.
	 */
	public ReviewIdManager getReviewIdManager() {
		return reviewIdManager;
	}

	/**
	 * Gets the <code>PhaseManager</code> instance.
	 * 
	 * @return the <code>PhaseManager</code> instance.
	 */
	public PhaseManager getPhaseManager() {
		return phaseManager;
	}

	/**
	 * Gets the <code>ReviewerIdManager</code> instance.
	 * 
	 * @return the <code>ReviewerIdManager</code> instance.
	 */
	public ReviewerIdManager getReviewerIdManager() {
		return reviewerIdManager;
	}

	/**
	 * Adds the IReviewModelElementChangeListener implementing listener.
	 * 
	 * @param listener
	 *            The IReviewModelElementChangeListener implementing listener.
	 */
	void addIReviewModelElementListener(
			IReviewModelElementChangeListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * Notifies IReviewModelElementChangeListener implementing listeners.
	 * 
	 * @param object
	 *            The object to be notified.
	 */
	public void notifyListeners(Object object) {
		for (Iterator<IReviewModelElementChangeListener> i = this.listeners
				.iterator(); i.hasNext();) {
			i.next().elementChanged(object);
		}
	}

}

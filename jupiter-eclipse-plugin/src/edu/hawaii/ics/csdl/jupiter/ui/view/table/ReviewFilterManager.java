package edu.hawaii.ics.csdl.jupiter.ui.view.table;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;

import edu.hawaii.ics.csdl.jupiter.ReviewI18n;
import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;
import edu.hawaii.ics.csdl.jupiter.file.PropertyConstraints;
import edu.hawaii.ics.csdl.jupiter.file.PropertyResource;
import edu.hawaii.ics.csdl.jupiter.file.ReviewResource;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewModel;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewerId;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.KeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Resolution;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ResolutionKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Severity;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.SeverityKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Status;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.StatusKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Type;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.TypeKeyManager;
import edu.hawaii.ics.csdl.jupiter.ui.preference.FilterPreferencePage;

/**
 * Provides the manager for the <code>ReviewFilter</code> instance. Clients can
 * check the filter status (if the filter instance is created and added to the
 * view or not), and/or add/remove the filter instance to/from the
 * <code>ReviewTableView</code> view.
 * 
 * @author Takuya Yamashita
 * @version $Id: ReviewFilterManager.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class ReviewFilterManager {

	/** Singleton instance. */
	private static ReviewFilterManager theInstance = new ReviewFilterManager();

	private static ReviewModel reviewModel;

	private static PropertyResource propertyResource;

	/** The <code>ViewerFilter</code> instance. */
	private ViewerFilter viewerFilter;

	private StructuredViewer viewer;

	/**
	 * Creates <code>ReviewFilterManager</code> instance. The field instance is
	 * set as null for <code>viewerFilter</code> and false for
	 * <code>isCreated</code>
	 */
	private ReviewFilterManager() {
		viewerFilter = new ReviewFilter();
	}

	/**
	 * Gets the <code>ReviewFilterManager</code> singleton instance.
	 * 
	 * @param structuredViewer
	 *            the structure viewer.
	 * @param viewerFilter
	 *            the <code>ViewerFilter</code> instance.
	 * @return the the <code>ReviewFilterManager</code> singleton instance.
	 */
	public static ReviewFilterManager getInstance(
			StructuredViewer structuredViewer, ViewerFilter viewerFilter) {
		theInstance.viewer = structuredViewer;
		theInstance.setFilter(viewerFilter);
		return theInstance;
	}

	/**
	 * Gets the <code>ReviewFilterManager</code> singleton instance.
	 * 
	 * @param structuredViewer
	 *            the structure viewer.
	 * @return the the <code>ReviewFilterManager</code> singleton instance.
	 */
	public static ReviewFilterManager getInstance(
			StructuredViewer structuredViewer) {
		theInstance.viewer = structuredViewer;
		return theInstance;
	}

	/**
	 * Sets the <code>ViewerFilter</code> instance.
	 * 
	 * @param viewerFilter
	 *            the <code>ViewerFilter</code> instance.
	 */
	private void setFilter(ViewerFilter viewerFilter) {
		if (this.viewerFilter != viewerFilter) {
			this.viewer.removeFilter(this.viewerFilter);
		}
		this.viewerFilter = viewerFilter;
	}

	/**
	 * Adds filter.
	 */
	public void addFilter() {
		this.viewer.addFilter(this.viewerFilter);
	}

	/**
	 * Removes filter.
	 */
	public void removeFilter() {
		this.viewer.removeFilter(this.viewerFilter);
	}

	/**
	 * Gets the new <code>ReviewFilter</code> instance, whose filter is
	 * retrieved from preference setting.
	 * 
	 * @return the new <code>ReviewFilter</code> instance.
	 */
	public static ViewerFilter createFilterFromPreference() {
		ReviewFilter filter = new ReviewFilter();
		IPreferenceStore store = ReviewPluginImpl.getInstance()
				.getPreferenceStore();
		boolean isFilterEanbled = store
				.getBoolean(FilterPreferencePage.ENABLE_FILTER_STORE_KEY);
		if (isFilterEanbled) {
			IProject project = reviewModel.getProjectManager().getProject();
			ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
			if (store
					.getBoolean(FilterPreferencePage.ENABLE_FILTER_SEVERITY_STORE_KEY)) {
				String severityKey = store
						.getString(FilterPreferencePage.FILTER_SEVERITY_COMBO_KEY);
				KeyManager<Severity> severityKeyManager = SeverityKeyManager
						.getInstance(project, reviewId);
				Severity severity = severityKeyManager
						.getItemObject(severityKey);
				filter.setSeverityFilter(severity);
			}
			if (store
					.getBoolean(FilterPreferencePage.ENABLE_FILTER_STATUS_STORE_KEY)) {
				String statusKey = store
						.getString(FilterPreferencePage.FILTER_STATUS_COMBO_KEY);
				KeyManager<Status> statusKeyManager = StatusKeyManager
						.getInstance(project, reviewId);
				Status status = statusKeyManager.getItemObject(statusKey);
				filter.setStatusFilter(status);
			}
			if (store
					.getBoolean(FilterPreferencePage.ENABLE_FILTER_RESOLUTION_STORE_KEY)) {
				String resolutionKey = store
						.getString(FilterPreferencePage.FILTER_RESOLUTION_COMBO_KEY);
				ResolutionKeyManager resolutionKeyManager = ResolutionKeyManager
						.getInstance(project, reviewId);
				Resolution resolution = resolutionKeyManager.getItemObject(resolutionKey);
				filter.setResolutionFilter(resolution);
			}
			if (store
					.getBoolean(FilterPreferencePage.ENABLE_FILTER_TYPE_STORE_KEY)) {
				String typeKey = store
						.getString(FilterPreferencePage.FILTER_TYPE_COMBO_KEY);
				TypeKeyManager typeKeyManager = TypeKeyManager.getInstance(
						project, reviewId);
				Type type = typeKeyManager.getItemObject(typeKey);
				filter.setTypeFilter(type);
			}
			if (store
					.getBoolean(FilterPreferencePage.ENABLE_FILTER_ASSIGNED_TO_STORE_KEY)) {
				String assignedTo = store
						.getString(FilterPreferencePage.FILTER_ASSIGNED_TO_COMBO_KEY);
				filter.setAssignedToFilter(assignedTo);
			}
			if (store
					.getBoolean(FilterPreferencePage.ENABLE_FILTER_REVIEWER_STORE_KEY)) {
				String reviewer = store
						.getString(FilterPreferencePage.FILTER_REVIEWER_COMBO_KEY);
				filter.setReviewerFilter(reviewer);
			}
			if (store
					.getBoolean(FilterPreferencePage.ENABLE_FILTER_FILE_STORE_KEY)) {
				String file = store
						.getString(FilterPreferencePage.FILTER_FILE_COMBO_KEY);
				filter.setFileFilter(file);
			}
			if (store
					.getBoolean(FilterPreferencePage.ENABLE_FILTER_INTERVAL_STORE_KEY)) {
				String intervalString = store
						.getString(FilterPreferencePage.FILTER_INTERVAL_TEXT_KEY);
				int interval = 0;
				try {
					interval = Integer.parseInt(intervalString);
					interval = (interval >= 0) ? interval : 0;
				} catch (NumberFormatException e) {
					// use interval = 0.
				}
				filter.setIntervalFilter(interval);
			}
		}
		return filter;
	}

	/**
	 * Creates the <code>ViewerFilter</code> from property setting.
	 * 
	 * @param project
	 *            the project.
	 * @param reviewId
	 *            the review id.
	 * @param phaseNameKey
	 *            the phase name key. e.g. phase.individual, etc.
	 * @return the <code>ViewerFilter</code> from property setting.
	 */
	public static ViewerFilter createFilterFromProperty(IProject project,
			ReviewId reviewId, String phaseNameKey) {
		ReviewFilter filter = new ReviewFilter();
		String reviewIdString = reviewId.getReviewId();
		ReviewResource reviewResource = propertyResource.getReviewResource(
				reviewIdString, true);
		FilterPhase filterPhase = reviewResource.getFilterPhase(phaseNameKey);
		if (reviewResource != null && filterPhase.isEnabled()) {
			String intervalName = PropertyConstraints.ATTRIBUTE_VALUE_INTERVAL;
			FilterEntry entry = filterPhase.getFilterEntry(intervalName);
			if (entry != null && entry.isEnabled()) {
				int interval = 0;
				try {
					interval = Integer.parseInt(entry.getValueKey());
					interval = (interval >= 0) ? interval : 0;
				} catch (NumberFormatException e) {
					// use interval = 0;
				}
				filter.setIntervalFilter(interval);
			}
			entry = filterPhase
					.getFilterEntry(PropertyConstraints.ATTRIBUTE_VALUE_REVIEWER);
			if (entry != null && entry.isEnabled()) {
				String reviewer = entry.getValueKey();
				if (reviewer.equals(ReviewerId.AUTOMATIC_KEY)) {
					ReviewerId reviewerId = reviewModel.getReviewerIdManager()
							.getReviewerId();
					reviewer = reviewerId.getReviewerId();
				}
				filter.setReviewerFilter(reviewer);
			}
			entry = filterPhase
					.getFilterEntry(PropertyConstraints.ATTRIBUTE_VALUE_TYPE);
			if (entry != null && entry.isEnabled()) {
				KeyManager<Type> manager = TypeKeyManager.getInstance(project,
						reviewId);
				Type type = manager.getItemObject(entry
						.getValueKey());
				filter.setTypeFilter(type);
			}
			entry = filterPhase
					.getFilterEntry(PropertyConstraints.ATTRIBUTE_VALUE_SEVERITY);
			if (entry != null && entry.isEnabled()) {
				KeyManager<Severity> manager = SeverityKeyManager.getInstance(
						project, reviewId);
				Severity severity = (Severity) manager.getItemObject(entry
						.getValueKey());
				filter.setSeverityFilter(severity);
			}
			entry = filterPhase
					.getFilterEntry(PropertyConstraints.ATTRIBUTE_VALUE_ASSIGNED_TO);
			if (entry != null && entry.isEnabled()) {
				String assignedTo = entry.getValueKey();
				if (assignedTo.equals(ReviewerId.AUTOMATIC_KEY)) {
					ReviewerId reviewerId = reviewModel.getReviewerIdManager()
							.getReviewerId();
					assignedTo = reviewerId.getReviewerId();
				}
				filter.setAssignedToFilter(assignedTo);
			}
			entry = filterPhase
					.getFilterEntry(PropertyConstraints.ATTRIBUTE_VALUE_RESOLUTION);
			if (entry != null && entry.isEnabled()) {
				ResolutionKeyManager manager = ResolutionKeyManager
						.getInstance(project, reviewId);
				Resolution resolution = (Resolution) manager
						.getItemObject(entry.getValueKey());
				filter.setResolutionFilter(resolution);
			}
			entry = filterPhase
					.getFilterEntry(PropertyConstraints.ATTRIBUTE_VALUE_STATUS);
			if (entry != null && entry.isEnabled()) {
				KeyManager<Status> manager = StatusKeyManager.getInstance(
						project, reviewId);
				Status status = (Status) manager.getItemObject(entry
						.getValueKey());
				filter.setStatusFilter(status);
			}
			entry = filterPhase
					.getFilterEntry(PropertyConstraints.ATTRIBUTE_VALUE_FILE);
			if (entry != null && entry.isEnabled()) {
				filter.setFileFilter(ReviewI18n.getString(entry.getValueKey()));
			}
		}
		return filter;
	}
}

package edu.hawaii.ics.csdl.jupiter.file.util;

import org.eclipse.core.resources.IProject;

import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewModel;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.KeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Resolution;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ResolutionKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Severity;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.SeverityKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Status;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.StatusKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Type;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.TypeKeyManager;
import edu.hawaii.ics.csdl.jupiter.util.ResourceBundleKey;

public class ReviewModelResolver {

	private ReviewModel reviewModel;

	public ReviewModelResolver(ReviewModel reviewModel) {
		this.reviewModel = reviewModel;
	}

	/**
	 * Gets the <code>Type</code> instance from the type string.
	 * 
	 * @param typeText
	 *            The type key string. Note that this supports the 1.4.212
	 *            version or below to convert old text to a key.
	 * 
	 * @return Returns the <code>Type</code> instance.
	 */
	public Type getType(String typeText) {
		String typeKey = "";
		if (typeText.equals("Defect")) {
			typeKey = ResourceBundleKey.ITEM_KEY_TYPE_DEFECT;
		} else if (typeText.equals("External_Issue")) {
			typeKey = ResourceBundleKey.ITEM_KEY_TYPE_EXTERNAL_ISSUE;
		} else if (typeText.equals("Question")) {
			typeKey = ResourceBundleKey.ITEM_KEY_TYPE_QUESTION;
		} else if (typeText.equals("Praise")) {
			typeKey = ResourceBundleKey.ITEM_KEY_TYPE_PRAISE;
		} else {
			typeKey = typeText;
		}
		IProject project = reviewModel.getProjectManager().getProject();
		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		KeyManager<Type> manager = TypeKeyManager
				.getInstance(project, reviewId);
		return manager.getItemObject(typeKey);
	}

	/**
	 * Gets the <code>Severity</code> instance from the severity string.
	 * 
	 * @param severityText
	 *            The severity key string. Note that this supports the 1.4.212
	 *            version or below to convert old text to a key.
	 * 
	 * @return Returns the <code>Severity</code> instance.
	 */
	public Severity getSeverity(String severityText) {
		String severityKey = "";
		if (severityText.equals("Critical")) {
			severityKey = ResourceBundleKey.ITEM_KEY_SEVERITY_CRITICAL;
		} else if (severityText.equals("Major")) {
			severityKey = ResourceBundleKey.ITEM_KEY_SEVERITY_MAJOR;
		} else if (severityText.equals("Normal")) {
			severityKey = ResourceBundleKey.ITEM_KEY_SEVERITY_NORMAL;
		} else if (severityText.equals("Minor")) {
			severityKey = ResourceBundleKey.ITEM_KEY_SEVERITY_MINOR;
		} else if (severityText.equals("Trivial")) {
			severityKey = ResourceBundleKey.ITEM_KEY_SEVERITY_TRIVIAL;
		} else {
			severityKey = severityText;
		}
		IProject project = reviewModel.getProjectManager().getProject();
		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		KeyManager<Severity> manager = SeverityKeyManager.getInstance(project,
				reviewId);
		return manager.getItemObject(severityKey);
	}

	/**
	 * Gets the <code>Resolution</code> instance from the resolution key string.
	 * 
	 * @param resolutionText
	 *            The resolution key string. Note that this supports the 1.4.212
	 *            version or below to convert old text to a key.
	 * 
	 * @return Returns the <code>Resolution</code> instance.
	 */
	public Resolution getResolution(String resolutionText) {
		String resolutionKey = "";
		if (resolutionText.equals("Valid-Needsfixing")) {
			resolutionKey = ResourceBundleKey.ITEM_KEY_RESOLUTION_VALID_NEEDSFIXING;
		} else if (resolutionText.equals("Valid-Wontfix")) {
			resolutionKey = ResourceBundleKey.ITEM_KEY_RESOLUTION_VALID_WONTFIX;
		} else if (resolutionText.equals("Valid-Duplicate")) {
			resolutionKey = ResourceBundleKey.ITEM_KEY_RESOLUTION_VALID_DUPLICATE;
		} else if (resolutionText.equals("Valid-Fixlater")) {
			resolutionKey = ResourceBundleKey.ITEM_KEY_RESOLUTION_VALID_FIXLATER;
		} else if (resolutionText.equals("Invalid-Wontfix")) {
			resolutionKey = ResourceBundleKey.ITEM_KEY_RESOLUTION_INVALID_WONTFIX;
		} else if (resolutionText.equals("Unsure-Validity")) {
			resolutionKey = ResourceBundleKey.ITEM_KEY_RESOLUTION_UNSURE_VALIDITY;
		} else if (resolutionText.equals("Unset")) {
			resolutionKey = ResourceBundleKey.ITEM_KEY_UNSET;
		} else {
			resolutionKey = resolutionText;
		}
		IProject project = reviewModel.getProjectManager().getProject();
		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		ResolutionKeyManager manager = ResolutionKeyManager.getInstance(
				project, reviewId);
		return manager.getItemObject(resolutionKey);
	}

	/**
	 * Gets the <code>Status</code> instance from the status key string.
	 * 
	 * @param statusText
	 *            The status key string. Note that this supports the 1.4.212
	 *            version or below to convert old text to a key.
	 * 
	 * @return Returns the <code>Status</code> instance.
	 */
	public Status getStatus(String statusText) {
		String statusKey = "";
		if (statusText.equals("Unresolved")) {
			statusKey = ResourceBundleKey.ITEM_KEY_STATUS_UNRESOLVED;
		} else if (statusText.equals("Resolved")) {
			statusKey = ResourceBundleKey.ITEM_KEY_STATUS_RESOLVED;
		} else {
			statusKey = statusText;
		}
		IProject project = reviewModel.getProjectManager().getProject();
		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		KeyManager<Status> manager = StatusKeyManager.getInstance(project,
				reviewId);
		return manager.getItemObject(statusKey);
	}

}

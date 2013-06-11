package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import edu.hawaii.ics.csdl.jupiter.file.FileResource;
import edu.hawaii.ics.csdl.jupiter.file.serializers.ReviewSerializer;
import edu.hawaii.ics.csdl.jupiter.file.serializers.SerializerException;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;

/**
 * Provides review issue model manager.
 * 
 * @author Takuya Yamashita
 * @version $Id: ReviewIssueModelManager.java 84 2008-03-07 10:11:27Z jsakuda $
 */
public class ReviewIssueModelManager {

	private ReviewIssueModel model;
	private FileResource fileResource;
	private ReviewSerializer reviewSerializer;

	/**
	 * Prohibits clients from instantiating this.
	 */
	public ReviewIssueModelManager(FileResource fileResource,
			ReviewSerializer reviewSerializer) {
		this.fileResource = fileResource;
		this.reviewSerializer = reviewSerializer;
		this.model = new ReviewIssueModel();
	}

	/**
	 * Gets the <code>ReviewIssueModel</code> instance. Note that
	 * <code>IllegalArgumentException</code> will be thrown if either
	 * <code>IProject</code> or <code>ReviewId</code> were null.
	 * 
	 * @param project
	 *            the project.
	 * @param reviewId
	 *            the review id.
	 * @return the <code>ReviewIssueModel</code> instance.
	 * @throws SerializerException 
	 */
	public ReviewIssueModel createReviewIssueModel(IProject project, ReviewId reviewId) throws SerializerException {
		if (project == null) {
			throw new IllegalArgumentException("project is null");
		}
		if (reviewId == null) {
			throw new IllegalArgumentException("reviewId is null");
		}
		this.model.clear();
		IFile[] reviewIFiles = fileResource.getReviewIFiles(project, reviewId);
		reviewSerializer.read(reviewId, this.model, reviewIFiles);

		this.model.sortByPreviousComparator();
		this.model.clearComparator();
		return this.model;
	}

	/**
	 * Gets the current model.
	 * 
	 * @return the current model.
	 */
	public ReviewIssueModel getCurrentModel() {
		return this.model;
	}
}

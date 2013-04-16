package edu.hawaii.ics.csdl.jupiter.file.serializers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.springframework.stereotype.Component;

import edu.hawaii.ics.csdl.jupiter.ReviewException;
import edu.hawaii.ics.csdl.jupiter.file.review.Review;
import edu.hawaii.ics.csdl.jupiter.file.util.ReviewFileStructureUtils;
import edu.hawaii.ics.csdl.jupiter.file.util.ReviewBuilder;
import edu.hawaii.ics.csdl.jupiter.file.util.ReviewIssueBuilder;
import edu.hawaii.ics.csdl.jupiter.file.util.ReviewModelResolver;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewModel;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModel;

/**
 * @author Takuya Yamashita
 * @version $Id: ReviewIssueXmlSerializer.java 155 2009-01-02 07:03:38Z jsakuda
 *          $
 */
@Component
public class ReviewSerializer implements ISerializer<Review> {

	/** The date format pattern string. */
	public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd :: HH:mm:ss:SSS z";
	/** The date formatter that uses the given date format string. */
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			DATE_FORMAT_PATTERN);

	@Resource
	private ISerializer<Review> iReviewSerializer;

	public ReviewSerializer() {
	}

	public ReviewSerializer(ISerializer<Review> serializer) {
		this.iReviewSerializer = serializer;
	}

	/**
	 * Writes the content of all the code review data stored in the model into
	 * the XML file. Note that clients does not need to check if the specified
	 * file path (either directory or file) exists. The IFile parameter allows
	 * the file to be refreshed in Eclipse.
	 * <p>
	 * Clients should check if the passing <code>File</code> or/and
	 * <code>ReviewIssueModel</code> instance is not null. Otherwise the
	 * <code>IllegalArgumentException</code> is thrown.
	 * 
	 * @param reviewId
	 *            the review ID.
	 * @param model
	 *            The model which contains the <code>ReviewIssue</code>
	 *            instances.
	 * @param xmlFile
	 *            The output XML file with absolute path.
	 * @param iFile
	 *            The iFile instance for the review file.
	 * @throws SerializerException
	 * 
	 */
	public void write(ReviewId reviewId, ReviewIssueModel model, File xmlFile,
			IFile iFile) throws SerializerException {
		write(reviewId, model, xmlFile);
		try {
			iFile.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (Exception e) {
			throw new SerializerException("Could not write review: ", e);
		}
	}

	/**
	 * Writes the content of all the code review data stored in the model into
	 * the XML file. Note that clients does not need to check if the specified
	 * file path (either directory or file) exists.
	 * <p>
	 * Clients should check if the passing <code>File</code> or/and
	 * <code>ReviewIssueModel</code> instance is not null. Otherwise the
	 * <code>IllegalArgumentException</code> is thrown.
	 * 
	 * @param reviewId
	 *            the review ID.
	 * @param model
	 *            The model which contains the <code>ReviewIssue</code>
	 *            instances.
	 * @param xmlFile
	 *            The output XML file with absolute path.
	 * 
	 */
	public void write(ReviewId reviewId, ReviewIssueModel model, File xmlFile)
			throws SerializerException {
		if (model == null) {
			throw new IllegalArgumentException(
					"ReviewIssueModel instance is null.");
		}
		if (!ReviewFileStructureUtils.verify(xmlFile)) {
			try {
				ReviewFileStructureUtils.create(xmlFile, reviewId);
			} catch (IOException e) {
				throw new SerializerException("Could not create review file: "
						+ xmlFile.getPath(), e);
			}
		}

		Review review = ReviewBuilder.createReviewFromModel(reviewId, model,
				xmlFile);

		iReviewSerializer.serialize(review, xmlFile);

	}

	/**
	 * Writes empty code review XML file.
	 * 
	 * @param xmlFile
	 *            the <code>File</code> XML file.
	 * @throws ReviewException
	 *             if problems occur during writing process.
	 * @throws IOException
	 */
	public void writeEmptyCodeReview(File xmlFile) throws SerializerException {
		Review review = new Review();
		ReviewModel reviewModel = new ReviewModel();
		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		try {
			ReviewFileStructureUtils.create(xmlFile, reviewId);
		} catch (IOException e) {
			throw new SerializerException("Could not create review file: "
					+ xmlFile.getPath(), e);
		}

		review.setId(reviewId.getReviewId());
		iReviewSerializer.serialize(review, xmlFile);
	}

	/**
	 * Reads the xmlFile of the <code>IFile</code> instance to create
	 * <code>ReviewIssue</code> instances in the <code>ReviewIssueModel</code>
	 * instance. Note that the review file will be skipped if the problems occur
	 * on the reading process (e.g. XML file is not valid due to error in XML
	 * structure).
	 * 
	 * @param reviewId
	 *            the review id.
	 * @param model
	 *            The <code>ReviewIssueModel</code> instance to hold
	 *            <code>ReviewIssue</code> instances.
	 * @param iFiles
	 *            The array of IFile implementing class instance to hold file
	 *            path.
	 * @return <code>true</code> if all files are successfully read.
	 *         <code>false</code> if there is a file to be unread.
	 * @throws SerializerException
	 */
	public boolean read(ReviewId reviewId, ReviewIssueModel model,
			IFile[] iFiles) throws SerializerException {
		if (reviewId == null || model == null || iFiles == null) {
			throw new IllegalArgumentException(
					"ReviewId, ReviewIssueModel, or IFile[] is null");
		}

		boolean isSuccessIterationForAll = false;
		ReviewModel reviewModel = new ReviewModel();
		ReviewModelResolver reviewModelResolver = new ReviewModelResolver(
				reviewModel);

		for (IFile iFile : iFiles) {
			File xmlFile = new File(iFile.getLocation().toString());
			Review review = null;
			review = deserialize(xmlFile);
			String reviewIdName = review.getId();
			if (reviewIdName != null
					&& reviewIdName.equals(reviewId.getReviewId())) {

				List<ReviewIssue> tempCodeReviewList = createReviewIssues(
						review, iFile, reviewModelResolver);
				if (!tempCodeReviewList.isEmpty()) {
					model.addAll(tempCodeReviewList);
				}
			}
		}
		isSuccessIterationForAll = true;
		return isSuccessIterationForAll;
	}

	private List<ReviewIssue> createReviewIssues(
			edu.hawaii.ics.csdl.jupiter.file.review.Review reviewFile,
			IFile iFile, ReviewModelResolver reviewModelResolver) {
		List<edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue> xmlReviewIssues = reviewFile
				.getReviewIssues();

		List<ReviewIssue> reviewIssues = new ArrayList<ReviewIssue>(
				xmlReviewIssues.size());
		for (edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue xmlReviewIssue : xmlReviewIssues) {
			try {
				ReviewIssue reviewIssue = ReviewIssueBuilder.createReviewIssue(
						xmlReviewIssue, iFile, reviewModelResolver);
				reviewIssues.add(reviewIssue);
			} catch (ReviewException e) {
				reviewIssues.clear();
				break;
			}
		}

		return reviewIssues;
	}

	/**
	 * Removes all <code>IFile</code> instances.
	 * 
	 * @param iFiles
	 *            the array of the <code>IFile</code> instances.
	 * @throws ReviewException
	 *             if problems occur during the file deletion.
	 */
	public static void remove(IFile[] iFiles) throws ReviewException {
		for (int i = 0; i < iFiles.length; i++) {
			try {
				iFiles[i].delete(true, false, null);
			} catch (CoreException e) {
				throw new ReviewException(e.getMessage());
			}
		}
	}

	public Review deserialize(File file) throws SerializerException {
		Review review = iReviewSerializer.deserialize(file);
		return review;
	}

	public void serialize(Review object, File file) throws SerializerException {
		iReviewSerializer.serialize(object, file);
	}

}

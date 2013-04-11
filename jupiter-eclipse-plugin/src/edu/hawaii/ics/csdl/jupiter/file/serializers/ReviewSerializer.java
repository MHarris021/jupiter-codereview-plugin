package edu.hawaii.ics.csdl.jupiter.file.serializers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.springframework.stereotype.Component;

import edu.hawaii.ics.csdl.jupiter.ReviewException;
import edu.hawaii.ics.csdl.jupiter.file.review.Review;
import edu.hawaii.ics.csdl.jupiter.file.util.ReviewBuilder;
import edu.hawaii.ics.csdl.jupiter.file.util.ReviewIssueBuilder;
import edu.hawaii.ics.csdl.jupiter.file.util.ReviewModelResolver;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewModel;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModel;
import edu.hawaii.ics.csdl.jupiter.util.JupiterLogger;

/**
 * @author Takuya Yamashita
 * @version $Id: ReviewIssueXmlSerializer.java 155 2009-01-02 07:03:38Z jsakuda
 *          $
 */
@Component
public class ReviewSerializer implements ISerializer<Review> {
	/** Jupiter logger */
	public static JupiterLogger log = JupiterLogger.getLogger();

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
	 * 
	 * @throws IOException
	 *             if problems occur.
	 * @throws XMLStreamException
	 *             Thrown if there are errors writing the review to file.
	 */
	public void write(ReviewId reviewId, ReviewIssueModel model, File xmlFile,
			IFile iFile) throws IOException, XMLStreamException {
		write(reviewId, model, xmlFile);
		try {
			iFile.refreshLocal(IResource.DEPTH_ZERO, null);
		} catch (CoreException e) {
			log.debug("Cannot refresh review file " + xmlFile.getAbsolutePath());
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
	 * @throws IOException
	 *             if problems occur.
	 * @throws XMLStreamException
	 *             Thrown if there are errors writing the review to file.
	 */
	public void write(ReviewId reviewId, ReviewIssueModel model, File xmlFile)
			throws IOException, XMLStreamException {
		if (model == null) {
			log.debug("Model is null.");
			throw new IllegalArgumentException(
					"ReviewIssueModel instance is null.");
		}
		verifyFileStructure(reviewId, xmlFile);

		Review review = ReviewBuilder.createReviewFromModel(reviewId, model,
				xmlFile);

		log.debug("writing " + xmlFile + " ...");
		try {
			iReviewSerializer.serialize(review, xmlFile);
		} catch (SerializerException e) {
			log.error(e);
		}
	}

	public void verifyFileStructure(ReviewId reviewId, File xmlFile)
			throws IOException {
		if (xmlFile == null) {
			log.debug("XML file instance is null.");
			throw new IllegalArgumentException("File instance is null.");
		}

		// if outputFile does not exit, create them.
		if (!xmlFile.getParentFile().exists()) {
			try {
				xmlFile.getParentFile().mkdirs();
			} catch (SecurityException e) {
				// show can-not-create directory message.
				log.debug(e.getMessage());
				throw new SecurityException(e.getMessage());
			}
		}
		if (!xmlFile.exists()) {
			try {
				xmlFile.createNewFile();
			} catch (SecurityException e) {
				log.debug(e.getMessage());
				throw new SecurityException(e.getMessage());
			}
		}
		if (!xmlFile.canWrite()) {
			// show can-not-write message in a file.
			log.debug(xmlFile + " can not be written");
			throw new RuntimeException(xmlFile
					+ " is not writable to record review issue. "
					+ "Please make it writable before proceed. Issue from "
					+ reviewId.getAuthor() + reviewId.getAuthor()
					+ " with message \"" + reviewId.getDescription()
					+ "\" is not saved.");
		}
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
	public void writeEmptyCodeReview(File xmlFile) throws ReviewException,
			IOException {
		verifyFileStructure(null, xmlFile);
		Review review = new Review();
		ReviewModel reviewModel = new ReviewModel();
		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		review.setId(reviewId.getReviewId());

		try {
			iReviewSerializer.serialize(review, xmlFile);
		} catch (SerializerException e) {
			throw new ReviewException("IOException: " + e.getMessage());
		}
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
	 */
	public boolean read(ReviewId reviewId, ReviewIssueModel model,
			IFile[] iFiles) {
		if (reviewId == null || model == null || iFiles == null) {
			throw new IllegalArgumentException(
					"ReviewId, ReviewIssueModel, or IFile[] is null");
		}

		boolean isSuccessIterationForAll = true;
		ReviewModel reviewModel = new ReviewModel();
		ReviewModelResolver reviewModelResolver = new ReviewModelResolver(
				reviewModel);

		for (int i = 0; i < iFiles.length; i++) {
			log.debug("reading " + iFiles[i].getLocation() + " ...");
			File xmlFile = new File(iFiles[i].getLocation().toString());
			Review review = null;
			try {
				review = deserialize(xmlFile);
			} catch (Exception e) {
				log.error(e);
				isSuccessIterationForAll = false;
				continue;
			}

			String reviewIdName = review.getId();
			if (reviewIdName != null
					&& reviewIdName.equals(reviewId.getReviewId())) {
				boolean isSuccessIterationForFile = true;

				List<edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue> xmlReviewIssues = review
						.getReviewIssues();
				List<ReviewIssue> tempCodeReviewList = new ArrayList<ReviewIssue>();
				for (edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue xmlReviewIssue : xmlReviewIssues) {
					try {
						ReviewIssue reviewIssue = ReviewIssueBuilder
								.createReviewIssue(xmlReviewIssue, iFiles[i],
										reviewModelResolver);
						tempCodeReviewList.add(reviewIssue);
					} catch (ReviewException e) {
						log.error(e);
						isSuccessIterationForFile = false;
						isSuccessIterationForAll = false;
						break;
					}
				}
				// adds the list to the model only when the iteration of the
				// file succeed.
				if (isSuccessIterationForFile) {
					model.addAll(tempCodeReviewList);
				}
			}
		}
		return isSuccessIterationForAll;
	}

	/**
	 * Checks if the passing <code>File</code> instance is associated with
	 * <code>String</code> review ID.
	 * 
	 * @param reviewId
	 *            the review ID.
	 * @param reviewFile
	 *            the review file to be checked.
	 * @return <code>true</code> if the code>File</code> instance is associated
	 *         with <code>String</code>.
	 * @throws ReviewException
	 *             if problems occur during file reading process.
	 */
	public boolean isReviewIdAssociatedFile(String reviewId, File reviewFile)
			throws ReviewException {
		Review review;
		try {
			review = iReviewSerializer.deserialize(reviewFile);
		} catch (SerializerException e) {
			throw new ReviewException(e.getMessage(), e);
		}

		String reviewIdName = review.getId();
		return (reviewIdName != null && reviewIdName.equals(reviewId));
	}

	/**
	 * Removes all <code>IFile</code> instances.
	 * 
	 * @param iFiles
	 *            the array of the <code>IFile</code> instances.
	 * @throws ReviewException
	 *             if problems occur during the file deletion.
	 */
	public void remove(IFile[] iFiles) throws ReviewException {
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

package edu.hawaii.ics.csdl.jupiter.file.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.resources.IFile;

import edu.hawaii.ics.csdl.jupiter.ReviewException;
import edu.hawaii.ics.csdl.jupiter.file.review.ReviewDate;
import edu.hawaii.ics.csdl.jupiter.file.review.ReviewFile;
import edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssueMeta;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;

public class ReviewIssueBuilder {

	/**
	 * Creates a Jupiter <code>ReviewIssue</code>.
	 * 
	 * @param reviewIssue
	 *            The Jupiter review issue to create an object of.
	 * @param dateFormat
	 *            TODO
	 * @param simpleDateFormat
	 *            TODO
	 * @return Returns the <code>ReviewIssue</code> to write to file.
	 */
	public static edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue createXmlReviewIssue(
			ReviewIssue reviewIssue, String dateFormat,
			SimpleDateFormat simpleDateFormat) {
		if (reviewIssue == null) {
			throw new IllegalArgumentException("ReviewIssue instance is null.");
		}
		edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue xmlReviewIssue = new edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue();
		xmlReviewIssue.setId(reviewIssue.getIssueId());
		ReviewDate creationDate = ReviewDateBuilder.createReviewDate(
				reviewIssue.getCreationDate(), dateFormat, simpleDateFormat);
		ReviewDate lastModDate = ReviewDateBuilder
				.createReviewDate(reviewIssue.getModificationDate(),
						dateFormat, simpleDateFormat);
		ReviewIssueMeta meta = new ReviewIssueMeta();
		meta.setCreationDate(creationDate);
		meta.setLastModificationDate(lastModDate);
		xmlReviewIssue.setReviewIssueMeta(meta);
		xmlReviewIssue.setReviewerId(reviewIssue.getReviewer());
		xmlReviewIssue.setAssignedTo(reviewIssue.getAssignedTo());
		ReviewFile reviewFile = new ReviewFile();
		String reviewLineString = reviewIssue.getLine();
		if (!"".equals(reviewLineString)) {
			reviewFile.setLine(Integer.parseInt(reviewLineString));
		}
		reviewFile.setValue(reviewIssue.getTargetFile());
		xmlReviewIssue.setFile(reviewFile);
		xmlReviewIssue.setType(reviewIssue.getType().getKey());
		xmlReviewIssue.setSeverity(reviewIssue.getSeverity().getKey());
		xmlReviewIssue.setSummary(reviewIssue.getSummary());
		xmlReviewIssue.setDescription(reviewIssue.getDescription());
		xmlReviewIssue.setAnnotation(reviewIssue.getAnnotation());
		xmlReviewIssue.setRevision(reviewIssue.getRevision());
		xmlReviewIssue.setResolution(reviewIssue.getResolution().getKey());
		xmlReviewIssue.setStatus(reviewIssue.getStatus().getKey());
		return xmlReviewIssue;
	}

	/**
	 * Converts a JAXB ReviewIssue object into a Jupiter model
	 * <code>ReviewIssue</code>.
	 * 
	 * @param xmlReviewIssue
	 *            The JAXB review issue object.
	 * @param reviewIFile
	 *            the review <code>iFile</code> instance to be stored in the
	 *            <code>ReviewIssue</code> instance.
	 * 
	 * @return The filled <code>ReviewIssue</code> instance.
	 * 
	 * @throws ReviewException
	 *             Thrown if a problem occurs when <code>ReviewIssue</code> is
	 *             being created.
	 */
	public static ReviewIssue createReviewIssue(
			edu.hawaii.ics.csdl.jupiter.file.review.ReviewIssue xmlReviewIssue,
			IFile reviewIFile, ReviewModelResolver reviewModelResolver)
			throws ReviewException {
		try {
			ReviewIssueMeta reviewIssueMeta = xmlReviewIssue
					.getReviewIssueMeta();
			ReviewDate creationDate = reviewIssueMeta.getCreationDate();
			Date createDate = ReviewDateBuilder.createDate(creationDate);
			ReviewDate lastModificationDate = reviewIssueMeta
					.getLastModificationDate();
			Date lastModDate = ReviewDateBuilder
					.createDate(lastModificationDate);

			String reviewerId = xmlReviewIssue.getReviewerId();
			String assignedTo = xmlReviewIssue.getAssignedTo();
			String summary = xmlReviewIssue.getSummary();
			String description = xmlReviewIssue.getDescription();
			String annotation = xmlReviewIssue.getAnnotation();
			String revision = xmlReviewIssue.getRevision();
			// String id = xmlReviewIssue.getId();

			String lineString = "";
			Integer line = xmlReviewIssue.getFile().getLine();
			if (line != null) {
				lineString = String.valueOf(line);
			}
			String targetFile = xmlReviewIssue.getFile().getValue();

			String typeString = xmlReviewIssue.getType();
			String severityString = xmlReviewIssue.getSeverity();
			String resolutionString = xmlReviewIssue.getResolution();
			String statusString = xmlReviewIssue.getStatus();

			return new ReviewIssue(createDate, lastModDate, reviewerId,
					assignedTo, targetFile, lineString,
					reviewModelResolver.getType(typeString),
					reviewModelResolver.getSeverity(severityString), summary,
					description, annotation, revision,
					reviewModelResolver.getResolution(resolutionString),
					reviewModelResolver.getStatus(statusString), reviewIFile);
		} catch (NullPointerException e) {
			throw new ReviewException(e.getMessage());
		} catch (ParseException e) {
			throw new ReviewException(e.getMessage());
		}
	}

}

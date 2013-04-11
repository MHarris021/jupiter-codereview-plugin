/**
 * 
 */
package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.core.resources.IFile;

/**
 * @author tetn
 * 
 */
public class ReviewIssueBuilder {

	private ReviewIssue reviewIssue;

	private static ReviewIssueBuilder builder;

	protected ReviewIssueBuilder() {

	}

	public static ReviewIssueBuilder getInstance() {
		if (builder == null) {
			builder = new ReviewIssueBuilder();
		}

		return builder;
	}

	public ReviewIssueBuilder createReviewIssue(String reviewer,
			String targetFile, String line, Type type, Severity severity,
			String summary, Status status, Resolution resolution,
			IFile reviewIFile) {
		Date creationDate = Calendar.getInstance().getTime();
		Date modificationDate = Calendar.getInstance().getTime();
		reviewIssue = new ReviewIssue(creationDate, modificationDate, reviewer,
				"", targetFile, line, type, severity, summary,
				"", "", "", resolution, status,
				reviewIFile);
		return this;
	}
	
	public ReviewIssueBuilder setReviewIssue(ReviewIssue issue) {
		reviewIssue.setReviewIssue(issue);
		return this;
	}

	public ReviewIssue build() {
		return reviewIssue;
	}

}

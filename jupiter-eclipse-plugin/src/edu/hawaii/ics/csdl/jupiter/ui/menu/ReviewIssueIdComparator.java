package edu.hawaii.ics.csdl.jupiter.ui.menu;

import java.util.Comparator;

import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModel;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModelManager;

public class ReviewIssueIdComparator implements Comparator<String> {

	private ReviewIssueModelManager reviewIssueModelManager;

	public ReviewIssueIdComparator(
			ReviewIssueModelManager reviewIssueModelManager) {
		this.reviewIssueModelManager = reviewIssueModelManager;
	}

	public int compare(String o1, String o2) {
		String reviewIssueId1 = o1;
		String reviewIssueId2 = o2;
		ReviewIssueModel model = reviewIssueModelManager.getCurrentModel();
		ReviewIssue reviewIssue1 = model.get(reviewIssueId1);
		ReviewIssue reviewIssue2 = model.get(reviewIssueId2);
		// Don't compare when there are null objects.
		if (reviewIssue1 == null || reviewIssue2 == null) {
			return -1;
		}
		return reviewIssue2.getModificationDate().compareTo(
				reviewIssue1.getModificationDate());
	}

}

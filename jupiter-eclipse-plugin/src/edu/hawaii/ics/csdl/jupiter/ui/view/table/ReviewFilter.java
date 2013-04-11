package edu.hawaii.ics.csdl.jupiter.ui.view.table;

import java.util.Calendar;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Ordinal;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Resolution;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Severity;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Status;

/**
 * Provides the filter function for the table view.
 * 
 * @author Takuya Yamashita
 * @version $Id: ReviewFilter.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class ReviewFilter extends ViewerFilter {
	/** The project name by which model is filtered. */
	private String project;
	/** The project name by which model is filtered. */
	private Severity severity;
	/** The status by which model is filtered. */
	private Status status;
	/** The type by which model is filtered. */
	private Ordinal type;
	/** The resolution by which model is filtered. */
	private Resolution resolution;
	/** The assignedTo by which model is fitlered. */
	private String assignedTo;
	/** The reviewer by which model is filtered. */
	private String reviewer;
	/** The previous days until which model is filtered. */
	private Calendar previousDays;
	/** The target file by which model is filtered. */
	private String file;

	/**
	 * Returns whether the given element makes it through this filter.
	 * 
	 * @param viewer
	 *            the viewer
	 * @param parentElement
	 *            the parent element
	 * @param element
	 *            the element
	 * 
	 * @return <code>true</code> if element is included in the filtered set, and
	 *         false if excluded
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		boolean selected = true;
		if (element instanceof ReviewIssue) {
			ReviewIssue reviewIssue = (ReviewIssue) element;
			selected = (isProjectMatched(reviewIssue)
					&& isSeverityMatched(reviewIssue)
					&& isStatusMatched(reviewIssue)
					&& isTypeMatched(reviewIssue)
					&& isResolutionMatched(reviewIssue)
					&& isAssignedToMatched(reviewIssue)
					&& isReviewerMatched(reviewIssue) && isDateMatched(reviewIssue))
					&& isFileMatched(reviewIssue);
		}
		return selected;
	}

	/**
	 * Returns <code>true</code> if the filtering target file is matched.
	 * Otherwise, returns <code>false</code>.
	 * 
	 * @param reviewIssue
	 *            the <code>ReviewIssue</code> instance to be checked.
	 * @return <code>true</code> if the filtering target file is matched.
	 */
	private boolean isFileMatched(ReviewIssue reviewIssue) {
		String targetFile = reviewIssue.getTargetFile();
		boolean isFileMatched = false;
		if(((this.file == null) || (targetFile == null)) || ((this.file != null) && (targetFile
				.startsWith(this.file)))){
			isFileMatched = true;
		}
		return isFileMatched;
	}

	/**
	 * Returns <code>true</code> if the filtering project is matched to the code
	 * review project name. Otherwise, returns <code>false</code>.
	 * 
	 * @param reviewIssue
	 *            the <code>ReviewIssue</code> instance to be checked.
	 * 
	 * @return <code>true</code> if the filtering project is matched to the code
	 *         review project. Otherwise, returns <code>false</code>.
	 */
	private boolean isProjectMatched(ReviewIssue reviewIssue) {
		boolean isProjectMatched = false;
		if((this.project == null) || ((this.project != null) && this.project
				.equals(reviewIssue.getReviewIFile().getProject().getName()))) {
			isProjectMatched = true;
		}
		return isProjectMatched;
	}

	/**
	 * Returns <code>true</code> if the severity is matched to the condtion that
	 * code review severity is greater than or equail to the filter-assigned
	 * severity. Otherwise, returns <code>false</code>.
	 * 
	 * @param reviewIssue
	 *            the <code>ReviewIssue</code> instance to be checked.
	 * 
	 * @return <code>true</code> if the severity is matched to the condtion that
	 *         code review severity is greater than or equail to the
	 *         filter-assigned severity. Otherwise, returns <code>false</code>.
	 */
	private boolean isSeverityMatched(ReviewIssue reviewIssue) {
		boolean isSeverityMatched = false;
		if((this.severity == null) || ((this.severity != null) && (this.severity
				.compareTo(reviewIssue.getSeverity()) >= 0))){
			isSeverityMatched = true;
		}
		return isSeverityMatched;
	}

	/**
	 * Returns <code>true</code> if the filtering status is matched to the code
	 * review status. Otherwise, returns <code>false</code>.
	 * 
	 * @param reviewIssue
	 *            the <code>ReviewIssue</code> instance to be checked.
	 * 
	 * @return <code>true</code> if the filtering status is matched to the code
	 *         review status. Otherwise, returns <code>false</code>.
	 */
	private boolean isStatusMatched(ReviewIssue reviewIssue) {
		boolean isStatusMatched = false;
		if((this.status == null) || ((this.status != null) && (this.status
				.compareTo(reviewIssue.getStatus()) == 0))){
			isStatusMatched = true;
		}
		return isStatusMatched;
	}

	/**
	 * Returns <code>true</code> if the filtering type is matched to the code
	 * review type. Otherwise, returns <code>false</code>.
	 * 
	 * @param reviewIssue
	 *            the <code>ReviewIssue</code> instance to be checked.
	 * 
	 * @return <code>true</code> if the filtering type is matched to the code
	 *         review type. Otherwise, returns <code>false</code>.
	 */
	private boolean isTypeMatched(ReviewIssue reviewIssue) {
		boolean isTypeMatched = false;
		if((this.type == null) || ((this.type != null) && (this.type
				.compareTo(reviewIssue.getType()) == 0))){
			isTypeMatched = true;
		}
		return isTypeMatched;
	}

	/**
	 * Returns <code>true</code> if the filtering resolution is matched to the
	 * code review resolution. Otherwise, returns <code>false</code>.
	 * 
	 * @param reviewIssue
	 *            the <code>ReviewIssue</code> instance to be checked.
	 * 
	 * @return <code>true</code> if the filtering resolution is matched to the
	 *         code review resolution. Otherwise, returns <code>false</code>.
	 */
	private boolean isResolutionMatched(ReviewIssue reviewIssue) {
		boolean isResolutionMatched = false;
		if((this.resolution == null) || ((this.resolution != null) && (this.resolution
				.compareTo(reviewIssue.getResolution()) == 0))) {
			isResolutionMatched = true;
		}
		return isResolutionMatched;
	}

	/**
	 * Returns <code>true</code> if the filtering assigned to is matched to the
	 * code review assigned to. Otherwise, returns <code>false</code>.
	 * 
	 * @param reviewIssue
	 *            the <code>ReviewIssue</code> instance to be checked.
	 * 
	 * @return <code>true</code> if the filtering assigned to is matched to the
	 *         code review assigned to. Otherwise, returns <code>false</code>.
	 */
	private boolean isAssignedToMatched(ReviewIssue reviewIssue) {
		boolean isAssignedToMatched = false;
		if((this.assignedTo == null) || ((this.assignedTo != null) && (this.assignedTo
				.compareTo(reviewIssue.getAssignedTo()) == 0))){
			isAssignedToMatched = true;
		}
		return isAssignedToMatched;
	}

	/**
	 * Returns <code>true</code> if the filtering reviewer is matched to the
	 * code review reviewer. Otherwise, returns <code>false</code>.
	 * 
	 * @param reviewIssue
	 *            the <code>ReviewIssue</code> instance to be checked.
	 * 
	 * @return <code>true</code> if the filtering reviewer is matched to the
	 *         code review reviewer. Otherwise, returns <code>false</code>.
	 */
	private boolean isReviewerMatched(ReviewIssue reviewIssue) {
		boolean isReviewerMatched = false;
		if((this.reviewer == null) || ((this.reviewer != null) && (this.reviewer
				.compareTo(reviewIssue.getReviewer()) == 0))){
			isReviewerMatched = true;
		}
		return isReviewerMatched;
	}

	/**
	 * Returns <code>true</code> if the filtering date is before the code review
	 * date (modification date). Otherwise, returns <code>false</code>.
	 * 
	 * @param reviewIssue
	 *            the <code>ReviewIssue</code> instance to be checked.
	 * 
	 * @return <code>true</code> if the filtering date is before the code review
	 *         date (modification date). Otherwise, returns <code>false</code>.
	 */
	private boolean isDateMatched(ReviewIssue reviewIssue) {
		Calendar modificationDateCalendar = Calendar.getInstance();
		modificationDateCalendar.setTime(reviewIssue.getModificationDate());
		boolean isDataMatched = false;
		if ((this.previousDays == null)
				|| ((this.previousDays != null) && this.previousDays
						.before(modificationDateCalendar))) {
			isDataMatched = true;
		}
		return isDataMatched;
	}

	/**
	 * Filters model by the target file.
	 * 
	 * @param file
	 *            the target file.
	 */
	public void setFileFilter(String file) {
		this.file = file;
	}

	/**
	 * Filters model by the project name.
	 * 
	 * @param project
	 *            the project by which model is filtered.
	 */
	public void setProjectFilter(String project) {
		this.project = project;
	}

	/**
	 * Filters model by the severity name.
	 * 
	 * @param severity
	 *            the severity by which model is filtered.
	 */
	public void setSeverityFilter(Severity severity) {
		this.severity = severity;
	}

	/**
	 * Filters model by the status.
	 * 
	 * @param status
	 *            the status by which model is filtered.
	 */
	public void setStatusFilter(Status status) {
		this.status = status;
	}

	/**
	 * Filters model by the type.
	 * 
	 * @param type
	 *            the type by which model is filtered.
	 */
	public void setTypeFilter(Ordinal type) {
		this.type = type;
	}

	/**
	 * Filters model by the resolution.
	 * 
	 * @param resolution
	 *            the resolution by which model is filtered.
	 */
	public void setResolutionFilter(Resolution resolution) {
		this.resolution = resolution;
	}

	/**
	 * Filters model by the assigned to.
	 * 
	 * @param assignedTo
	 *            the assigned to by which model is filtered.
	 */
	public void setAssignedToFilter(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * Filters model by the reviewer.
	 * 
	 * @param reviewer
	 *            the reviewer to by which model is filtered.
	 */
	public void setReviewerFilter(String reviewer) {
		this.reviewer = reviewer;
	}

	/**
	 * Filters model by the previous days. The parameter should be non negative
	 * number. Otherwise, throws <code>IllegalArgumentException</code>. For
	 * example, to filter mode until 3 days ago, the <code>previousDays</code>
	 * will be 3. Note that to filter it today (or until yesterday, the number
	 * will be 1 while 0 stands for past all days (infinity).
	 * 
	 * @param previousDays
	 *            the previous int type days until which model is filtered.
	 */
	public void setIntervalFilter(int previousDays) {
		if (previousDays < 0) {
			throw new IllegalArgumentException(
					"previousDays should be non negative number or -1");
		} else if (previousDays > 0) {
			// change plus to minus.
			previousDays = previousDays - (2 * previousDays);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, previousDays);
			this.previousDays = calendar;
		} else {
			this.previousDays = null;
		}
	}

	/**
	 * Clears all filters, meaning no filters are set.
	 */
	public void clearAllFilters() {
		this.status = null;
		this.type = null;
		this.resolution = null;
		this.assignedTo = null;
		this.previousDays = null;
		this.file = null;
	}
}

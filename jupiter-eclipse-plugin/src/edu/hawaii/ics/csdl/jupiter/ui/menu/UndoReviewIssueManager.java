package edu.hawaii.ics.csdl.jupiter.ui.menu;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import edu.hawaii.ics.csdl.jupiter.event.IReviewIssueModelListener;
import edu.hawaii.ics.csdl.jupiter.event.ReviewIssueModelEvent;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModelManager;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorView;
import edu.hawaii.ics.csdl.jupiter.util.JupiterLogger;

/**
 * Provides the undo ReviewIssue manager.
 * 
 * @author Takuya Yamashita
 * @version $Id: UndoReviewIssueManager.java 81 2008-02-17 08:06:25Z jsakuda $
 */
public class UndoReviewIssueManager implements IReviewIssueModelListener {
	/** Jupiter logger */
	private static JupiterLogger log = JupiterLogger.getLogger();

	/** The Map of the ReviewIssue ID -> ReviewIssue. */
	private Map<String, ReviewIssue> undoReviewIssueMap;

	protected ReviewIssueModelManager reviewIssueModelManager;
	/** The number of the Undo review items shown in the list. */
	private static final int NUM_UNDO_ITEMS = 10;

	/**
	 * Prevents clients from instantiating this. Creates the Map whose
	 * comparator order is reverse modification date order.
	 */
	public UndoReviewIssueManager() {
		this.undoReviewIssueMap = new TreeMap<String, ReviewIssue>(
				new ReviewIssueIdComparator(reviewIssueModelManager));
	}

	/**
	 * Creates undo review issue selection pulldown menu.
	 * 
	 * @param menu
	 *            the menu of the parent.
	 * @return the menu of the pulldown menu.
	 */
	public Menu createPulldownMenu(Menu menu) {
		create(menu);
		return menu;
	}

	/**
	 * Creates target file selection menu.
	 * 
	 * @param menu
	 *            the menu of the parent.
	 */
	private void create(Menu menu) {
		int counter = NUM_UNDO_ITEMS;
		for (Iterator<ReviewIssue> i = this.undoReviewIssueMap.values()
				.iterator(); i.hasNext(); counter--) {
			ReviewIssue reviewIssue = (ReviewIssue) i.next();
			MenuItem menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setText(reviewIssue.getSummary());
			menuItem.setData(reviewIssue);
			menuItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					ReviewIssue targetReviewIssue = (ReviewIssue) event.widget
							.getData();
					ReviewEditorView view = ReviewEditorView.getActiveView();
					if (view != null) {
						view.setReviewIssue(targetReviewIssue);
					}
					log.debug("SUMMARY:: " + targetReviewIssue.getSummary());
				}
			});
		}
	}

	/**
	 * Adds and removes the ReviewIssue instance to the container, depending
	 * upon the event type. Adds it if the event type is ADD and EDIT. Removes
	 * it if the event type is DELETE.
	 * 
	 * @param event
	 *            the <code>ReviewIssueModelEvent</code> instance.
	 */
	public void reviewIssueModelChanged(ReviewIssueModelEvent event) {
		int add = ReviewIssueModelEvent.ADD;
		int delete = ReviewIssueModelEvent.DELETE;
		int edit = ReviewIssueModelEvent.EDIT;
		ReviewIssue reviewIssue = event.getReviewIssue();
		if ((event.getEventType() & (add | edit)) != 0) {
			log.debug("review issue was added...");
			this.undoReviewIssueMap.put(reviewIssue.getIssueId(), reviewIssue);
		} else if ((event.getEventType() & (delete)) != 0) {
			log.debug("review issue was deleted...");
			this.undoReviewIssueMap.remove(reviewIssue.getIssueId());
		}
	}

	/**
	 * Clears the undo container.
	 * 
	 */
	public void clear() {
		this.undoReviewIssueMap.clear();
		// this.previousReviewIssue = null;
	}

	/**
	 * Provides the size of the undo container.
	 * 
	 * @return the size of the undo container.
	 */
	public int size() {
		return this.undoReviewIssueMap.size();
	}
}

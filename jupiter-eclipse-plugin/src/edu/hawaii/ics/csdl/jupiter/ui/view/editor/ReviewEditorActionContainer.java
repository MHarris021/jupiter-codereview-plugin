package edu.hawaii.ics.csdl.jupiter.ui.view.editor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Table;

import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModelManager;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions.ReviewEditorClearAction;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions.ReviewEditorGotoAction;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions.ReviewEditorNextAction;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions.ReviewEditorPreviousAction;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions.ReviewEditorSaveAction;
import edu.hawaii.ics.csdl.jupiter.ui.view.table.ReviewTableView;

/**
 * Provides the action for the <code>ReviewEditorView</code>.
 * 
 * @author Takuya Yamashita
 * @version $Id: ReviewEditorActionContainer.java 177 2010-06-23 01:21:27Z
 *          jsakuda $
 */
public class ReviewEditorActionContainer {
	public static final String GOTO = "Goto";

	public static final ImageDescriptor SAVE_IMAGE_DESCRIPTOR = ReviewPluginImpl
			.createImageDescriptor("icons/save.gif");

	public static final String SAVE = "Save";

	public static final ImageDescriptor CLEAR_IMAGE_DESCRIPTOR = ReviewPluginImpl
			.createImageDescriptor("icons/clear.gif");

	public static final String CLEAR = "Clear";

	public static final ImageDescriptor NEXT_IMAGE_DESCRIPTOR = ReviewPluginImpl
			.createImageDescriptor("icons/down.gif");

	public static final ImageDescriptor PREVIOUS_IMAGE_DESCRIPTOR = ReviewPluginImpl
			.createImageDescriptor("icons/up.gif");

	public static final ImageDescriptor GOTO_IMAGE_DESCRIPTOR = ReviewPluginImpl
			.createImageDescriptor("icons/goto.gif");
	public static final String NEXT = "Next";

	public static final String PREVIOUS = "Previous";

	private ReviewIssueModelManager reviewIssueModelManager;

	/** The go to action to jump to the specified source code */
	private Action gotoAction;
	/** The next action to forward the list of the view table. */
	private Action nextAction;
	/** The previous action to backward the list of the view table. */
	private Action previousAction;
	/** The save action to save the current editing issue. */
	private Action saveAction;
	/** The clear action to clear all fields in the current editing issue. */
	private Action clearAction;

	public ReviewEditorActionContainer() {
		setGotoAction(new ReviewEditorGotoAction());
		setNextAction(new ReviewEditorNextAction());

		setPreviousAction(new ReviewEditorPreviousAction());

		setSaveAction(new ReviewEditorSaveAction());

		setClearAction(new ReviewEditorClearAction());
	}

	/**
	 * Updates the next and previous icon.
	 */
	public void updateIcons() {
		ReviewTableView tableView = ReviewTableView.getActiveView();
		if (tableView != null) {
			Table table = tableView.getTable();
			int index = table.getSelectionIndex();
			if (index != -1) {
				int length = table.getItemCount();
				getNextAction().setEnabled(index < length - 1);
				getPreviousAction().setEnabled(index > 0);
			}
		}
	}

	public Action getGotoAction() {
		return gotoAction;
	}

	public void setGotoAction(Action gotoAction) {
		this.gotoAction = gotoAction;
	}

	public Action getSaveAction() {
		return saveAction;
	}

	public void setSaveAction(Action saveAction) {
		this.saveAction = saveAction;
	}

	public Action getClearAction() {
		return clearAction;
	}

	public void setClearAction(Action clearAction) {
		this.clearAction = clearAction;
	}

	public Action getNextAction() {
		return nextAction;
	}

	public void setNextAction(Action nextAction) {
		this.nextAction = nextAction;
	}

	public Action getPreviousAction() {
		return previousAction;
	}

	public void setPreviousAction(Action previousAction) {
		this.previousAction = previousAction;
	}

	public ReviewIssueModelManager getReviewIssueModelManager() {
		return reviewIssueModelManager;
	}

	public void setReviewIssueModelManager(
			ReviewIssueModelManager reviewIssueModelManager) {
		this.reviewIssueModelManager = reviewIssueModelManager;
	}

	public void setEnabled(boolean isEnabled) {
		getSaveAction().setEnabled(isEnabled);
		getClearAction().setEnabled(isEnabled);
		getGotoAction().setEnabled(isEnabled);
		getNextAction().setEnabled(isEnabled);
		getPreviousAction().setEnabled(isEnabled);
	}
}
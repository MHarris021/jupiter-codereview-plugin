package edu.hawaii.ics.csdl.jupiter.ui.view.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import edu.hawaii.ics.csdl.jupiter.ReviewException;
import edu.hawaii.ics.csdl.jupiter.ReviewI18n;
import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;
import edu.hawaii.ics.csdl.jupiter.event.ReviewEvent;
import edu.hawaii.ics.csdl.jupiter.event.ReviewIssueModelEvent;
import edu.hawaii.ics.csdl.jupiter.file.FileResource;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModel;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModelManager;
import edu.hawaii.ics.csdl.jupiter.ui.menu.UndoReviewIssueManager;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions.ReviewEditorClearAction;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions.ReviewEditorGotoAction;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions.ReviewEditorNextAction;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions.ReviewEditorPreviousAction;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions.ReviewEditorSaveAction;
import edu.hawaii.ics.csdl.jupiter.ui.view.table.ReviewTableView;
import edu.hawaii.ics.csdl.jupiter.ui.view.table.ReviewTableViewAction;
import edu.hawaii.ics.csdl.jupiter.util.JupiterLogger;
import edu.hawaii.ics.csdl.jupiter.util.ResourceBundleKey;

/**
 * Provides the action for the <code>ReviewEditorView</code>.
 * 
 * @author Takuya Yamashita
 * @version $Id: ReviewEditorActionContainer.java 177 2010-06-23 01:21:27Z
 *          jsakuda $
 */
public class ReviewEditorActionContainer {

	private static ReviewIssueModelManager reviewIssueModelManager;

	/** The go to action to jump to the specified source code */
	public static Action gotoAction;
	/** The next action to forward the list of the view table. */
	public static Action nextAction;
	/** The previous action to backward the list of the view table. */
	public static Action previousAction;
	/** The save action to save the current editing issue. */
	private static Action saveAction;
	/** The clear action to clear all fields in the current editing issue. */
	public static Action clearAction;
	static {
		gotoAction = new ReviewEditorGotoAction();
		nextAction = new ReviewEditorNextAction();

		previousAction = new ReviewEditorPreviousAction();

		saveAction = new ReviewEditorSaveAction();

		clearAction = new ReviewEditorClearAction();
	}

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
				nextAction.setEnabled(index < length - 1);
				previousAction.setEnabled(index > 0);
			}
		}
	}
}
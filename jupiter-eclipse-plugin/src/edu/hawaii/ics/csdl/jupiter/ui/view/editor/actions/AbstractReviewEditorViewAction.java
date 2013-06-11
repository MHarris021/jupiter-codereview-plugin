package edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Table;

import edu.hawaii.ics.csdl.jupiter.ReviewI18n;
import edu.hawaii.ics.csdl.jupiter.ReviewPlugin;
import edu.hawaii.ics.csdl.jupiter.event.ReviewEvent;
import edu.hawaii.ics.csdl.jupiter.event.ReviewIssueModelEvent;
import edu.hawaii.ics.csdl.jupiter.event.ReviewIssueModelException;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModel;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorActionContainer;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorView;
import edu.hawaii.ics.csdl.jupiter.ui.view.table.ReviewTableView;

/**
 * @author tetn
 * 
 */
public abstract class AbstractReviewEditorViewAction extends Action {

	private int type = ReviewEvent.TYPE_COMMAND;
	private int kind;

	private ReviewEditorView reviewEditorView;

	private ReviewPlugin reviewPlugin;

	private ReviewIssueModel reviewIssueModel;

	private ReviewEditorActionContainer reviewEditorActionContainer;

	/**
	 * @param text
	 * @param image
	 */
	public AbstractReviewEditorViewAction(String text, ImageDescriptor image) {
		super(text, image);

	}

	@Override
	public void setText(String text) {
		String internationalizedString = ReviewI18n.getString(text);
		super.setText(internationalizedString);
	}

	@Override
	public void setToolTipText(String toolTipText) {
		String internationalizedString = ReviewI18n.getString(toolTipText);
		super.setToolTipText(internationalizedString);
	}

	@Override
	public void run() {
		try {
			processSave();
		} catch (ReviewIssueModelException e) {
			throw new RuntimeException(e);
		}
		runEditorAction();
		reviewPlugin.notifyListeners(type, kind);
	}

	protected abstract void runEditorAction();

	public ReviewEditorView getReviewEditorView() {
		return reviewEditorView;
	}

	public void setReviewEditorView(ReviewEditorView reviewEditorView) {
		this.reviewEditorView = reviewEditorView;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public ReviewPlugin getReviewPlugin() {
		return reviewPlugin;
	}

	public void setReviewPlugin(ReviewPlugin reviewPlugin) {
		this.reviewPlugin = reviewPlugin;
	}

	/**
	 * Processes saving an issue.
	 * 
	 * @return <code>true</code> if successfully saved. <code>false</code>
	 *         otherwise.
	 * @throws ReviewIssueModelException
	 */
	protected boolean processSave() throws ReviewIssueModelException {
		if (reviewEditorView == null) {
			return false;
		}
		ReviewIssue savingReviewIssue = reviewEditorView.getReviewIssue();
		if (savingReviewIssue == null) {
			return false;
		}

		// make sure the editingCodeReview is contained.
		if (reviewIssueModel.contains(savingReviewIssue.getIssueId())) {
			ReviewIssue originalReviewIssue = reviewIssueModel
					.get(savingReviewIssue.getIssueId());
			if (!originalReviewIssue.contentEquals(savingReviewIssue)) {
				originalReviewIssue.setReviewIssue(savingReviewIssue);
				reviewIssueModel.notifyListeners(ReviewIssueModelEvent.EDIT);
			}
		} else {
			reviewIssueModel.add(savingReviewIssue);
			ReviewTableView view = ReviewTableView.getActiveView();
			if (view == null) {
				view = ReviewTableView.bringViewToTop();
			}
			view.getViewer().refresh();
			Table table = view.getTable();
			table.select(0);
			reviewEditorActionContainer.updateIcons();
			reviewIssueModel.notifyListeners(ReviewIssueModelEvent.ADD);
		}
		return true;
	}

	public ReviewIssueModel getReviewIssueModel() {
		return reviewIssueModel;
	}

	public void setReviewIssueModel(ReviewIssueModel reviewIssueModel) {
		this.reviewIssueModel = reviewIssueModel;
	}

	public ReviewEditorActionContainer getReviewEditorViewAction() {
		return reviewEditorActionContainer;
	}

	public void setReviewEditorViewAction(
			ReviewEditorActionContainer reviewEditorActionContainer) {
		this.reviewEditorActionContainer = reviewEditorActionContainer;
	}

}

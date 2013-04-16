package edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Table;

import edu.hawaii.ics.csdl.jupiter.ui.view.table.ReviewTableView;

public abstract class AbstractNavigationReviewEditorAction extends
		AbstractReviewEditorViewAction {

	private ReviewTableView reviewTableView;

	private Table table;

	private int selection;
	
	private IAction notificationAction;

	public AbstractNavigationReviewEditorAction(String text,
			ImageDescriptor image) {
		super(text, image);
	}

	public AbstractNavigationReviewEditorAction(String text, int style) {
		super(text, style);
	}

	@Override
	public void run() {
		processSave();
		table = reviewTableView.getTable();
		int index = table.getSelectionIndex();
		int length = table.getItemCount();
		if (indexInBounds(index, length)) {
			runEditorAction();
			notificationAction.run();
			getReviewPlugin().notifyListeners(getType(), getKind());
		}
	}

	protected abstract boolean indexInBounds(int index, int length);

	public ReviewTableView getReviewTableView() {
		return reviewTableView;
	}

	public void setReviewTableView(ReviewTableView reviewTableView) {
		this.reviewTableView = reviewTableView;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
		table.setSelection(selection);
	}

	public IAction getNavigationAction() {
		return notificationAction;
	}

	public void setNavigationAction(IAction navigationAction) {
		this.notificationAction = navigationAction;
	}

}

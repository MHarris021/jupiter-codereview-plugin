package edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions;

import edu.hawaii.ics.csdl.jupiter.event.ReviewEvent;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorActionContainer;

public class ReviewEditorClearAction extends AbstractReviewEditorViewAction {

	public ReviewEditorClearAction() {
		super(ReviewEditorActionContainer.CLEAR, ReviewEditorActionContainer.CLEAR_IMAGE_DESCRIPTOR);
	}
	
	@Override
	protected void runEditorAction() {
		getReviewEditorView().clearAllFields();
		setKind(ReviewEvent.KIND_CLEAR);
	}

}

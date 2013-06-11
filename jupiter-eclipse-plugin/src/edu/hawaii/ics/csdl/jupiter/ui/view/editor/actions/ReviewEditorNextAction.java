package edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions;

import edu.hawaii.ics.csdl.jupiter.event.ReviewEvent;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorActionContainer;

public class ReviewEditorNextAction extends
		AbstractNavigationReviewEditorAction {

	public ReviewEditorNextAction() {
		super(ReviewEditorActionContainer.NEXT, ReviewEditorActionContainer.NEXT_IMAGE_DESCRIPTOR);
	}

	@Override
	protected boolean indexInBounds(int index, int length) {
		boolean result = false;
		if (index != -1 && index < length - 1) {
			result = true;
		}
		return result;
	}

	@Override
	protected void runEditorAction() {
		int index = getTable().getSelectionIndex();
		setSelection(index + 1);
		setKind(ReviewEvent.KIND_NEXT);

	}

}

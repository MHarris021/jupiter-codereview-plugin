/**
 * 
 */
package edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions;


import edu.hawaii.ics.csdl.jupiter.event.ReviewEvent;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorActionContainer;

/**
 * @author tetn
 *
 */
public class ReviewEditorSaveAction extends AbstractReviewEditorViewAction {

	public ReviewEditorSaveAction() {
		super(ReviewEditorActionContainer.SAVE, ReviewEditorActionContainer.SAVE_IMAGE_DESCRIPTOR);
	}
	
	@Override
	protected void runEditorAction() {
		setKind(ReviewEvent.KIND_SAVE);
		
	}

}

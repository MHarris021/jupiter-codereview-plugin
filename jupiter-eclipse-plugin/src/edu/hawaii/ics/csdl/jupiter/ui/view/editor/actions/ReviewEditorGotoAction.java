package edu.hawaii.ics.csdl.jupiter.ui.view.editor.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import edu.hawaii.ics.csdl.jupiter.event.ReviewEvent;
import edu.hawaii.ics.csdl.jupiter.file.FileResource;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorActionContainer;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorView;

public class ReviewEditorGotoAction extends AbstractReviewEditorViewAction {

	private FileResource fileResource;

	public ReviewEditorGotoAction() {
		super(ReviewEditorActionContainer.GOTO, ReviewEditorActionContainer.GOTO_IMAGE_DESCRIPTOR);
	}
	
	@Override
	protected void runEditorAction() {
		ReviewEditorView reviewEditorView = getReviewEditorView();
		if (reviewEditorView != null) {
			ReviewIssue reviewIssue = reviewEditorView.getReviewIssue();
			String targetFile = reviewIssue.getTargetFile();
			if (!targetFile.equals("")) {
				IProject project = FileResource.getProject(reviewIssue
						.getReviewIFile());
				IFile targetIFile = project.getFile(targetFile);
				String lineNumberString = reviewIssue.getLine();
				int lineNumber = 0;
				try{
					lineNumber = Integer.parseInt(lineNumberString);
				}
				catch(NumberFormatException e) {
					
				}
				fileResource.goToLine(targetIFile, lineNumber);
			}
		}
		setKind(ReviewEvent.KIND_GOTO);

	}

}

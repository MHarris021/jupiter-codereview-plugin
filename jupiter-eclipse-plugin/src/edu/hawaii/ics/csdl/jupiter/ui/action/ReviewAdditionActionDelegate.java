package edu.hawaii.ics.csdl.jupiter.ui.action;

import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import edu.hawaii.ics.csdl.jupiter.ReviewException;
import edu.hawaii.ics.csdl.jupiter.ReviewI18n;
import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;
import edu.hawaii.ics.csdl.jupiter.event.ReviewEvent;
import edu.hawaii.ics.csdl.jupiter.event.ReviewIssueModelEvent;
import edu.hawaii.ics.csdl.jupiter.file.FileResource;
import edu.hawaii.ics.csdl.jupiter.file.PreferenceResource;
import edu.hawaii.ics.csdl.jupiter.file.PropertyResource;
import edu.hawaii.ics.csdl.jupiter.file.ReviewResource;
import edu.hawaii.ics.csdl.jupiter.model.columndata.ColumnDataModel;
import edu.hawaii.ics.csdl.jupiter.model.columndata.ColumnDataModelManager;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewModel;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewerId;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Resolution;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ResolutionKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModel;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModelManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Severity;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.SeverityKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Status;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.StatusKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Type;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.TypeKeyManager;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorView;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorActionContainer;
import edu.hawaii.ics.csdl.jupiter.ui.view.table.FilterPhase;
import edu.hawaii.ics.csdl.jupiter.ui.view.table.ReviewTableView;
import edu.hawaii.ics.csdl.jupiter.util.ReviewDialog;

/**
 * Provides an action triggered when Code Review pop up menu on the Compilation
 * Unit is selected. Do the action that the code review editor is opened to edit
 * comments with class name, method name, and line number if applicable.
 * 
 * @author Takuya Yamashita
 * @version $Id: ReviewAdditionActionDelegate.java 170 2009-10-08 08:38:34Z
 *          jsakuda $
 */
public class ReviewAdditionActionDelegate implements IEditorActionDelegate,
		IWorkbenchWindowActionDelegate {

	/** The target file path from a project */
	private String targetFilePath = "";
	/** The line number to be recored in the opened editor. */
	private String lineNumber = "";
	/** The selectedText when code review action is invoked. */
	private String selectedText = "";
	
	@Autowired
	private FileResource fileResource;

	@Autowired
	private PreferenceResource preferenceResource;

	@Autowired
	private PropertyResource propertyResource;

	@Autowired
	private ReviewModel reviewModel;

	@Autowired
	private ReviewIssueModelManager reviewIssueModelManager;
	
	@Autowired
	private IWorkbench workbench;

	public ReviewAdditionActionDelegate() {
	}

	public ReviewAdditionActionDelegate(FileResource fileResource,
			PreferenceResource preferenceResource,
			PropertyResource propertyResource) {
		this.fileResource = fileResource;
		this.preferenceResource = preferenceResource;
		this.propertyResource = propertyResource;
	}

	/**
	 * Runs logic implementation when the menu, whose this implementing target
	 * is defined in the plugin.xml, is selected. See the plugin.xml file.
	 * Clients should not call this method.
	 * 
	 * @param action
	 *            The action proxy that handles the presentation portion of the
	 *            action
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		boolean isDetermined = determineProjectReviewIdReviewerId();
		if (!isDetermined) {
			return;
		}
		IFile selectedFile = fileResource.getSelectedIFile();
		this.targetFilePath = (selectedFile != null) ? selectedFile
				.getProjectRelativePath().toString() : "";

		// Creates ReviewIssue instance, and fill it into editor view.
		IProject project = reviewModel.getProjectManager().getProject();
		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		ReviewerId reviewerId = reviewModel.getReviewerIdManager()
				.getReviewerId();
		IFile iReviewFile = fileResource.getReviewFile(project, reviewId,
				reviewerId);
		if (iReviewFile == null) {
			String titleKey = "ReviewDialog.noReviewFileDetermined.simpleConfirm.messageDialog.title";
			String title = ReviewI18n.getString(titleKey);
			String messageKey = "ReviewDialog.noReviewFileDetermined.simpleConfirm.messageDialog.message";
			String message = ReviewI18n.getString(messageKey);
			ReviewDialog.openSimpleComfirmMessageDialog(title, message);
			return;
		}
		// check file written permission
		if (iReviewFile.isReadOnly()) {
			String message = "Review file " + iReviewFile
					+ " is readonly. Please make it writable to"
					+ " save your issues.";
			ReviewDialog.openSimpleComfirmMessageDialog("Review Management",
					message);
		}

		try {
			ReviewEditorView editorView = ReviewEditorView.bringViewToTop();
			editorView.setEnable(true);

			// you cannot just get the line number, verify that the active file
			// is the same as the target file
			// TODO Is it ok to assume that just because the files match the
			// line number should be used?
			// this may not be case if the file is active and the user
			// right-clicked on it from the package manager
			String line = "";
			if (selectedFile != null
					&& selectedFile.equals(FileResource.getActiveFile())) {
				line = getLineNumber();
			}

			if (!targetFilePath.equals("")) {
				ReviewIssue reviewIssue = new ReviewIssue(new Date(),
						new Date(), reviewerId.getReviewerId(), "",
						targetFilePath, line, (Type) TypeKeyManager
								.getInstance(project, reviewId).getDefault(),
						(Severity) SeverityKeyManager.getInstance(project,
								reviewId).getDefault(), "", this.selectedText,
						"", "", (Resolution) ResolutionKeyManager.getInstance(
								project, reviewId).getDefault(),
						(Status) StatusKeyManager
								.getInstance(project, reviewId).getDefault(),
						iReviewFile);
				reviewIssue.setLinked(true);
				ReviewEditorActionContainer.nextAction.setEnabled(false);
				ReviewEditorActionContainer.previousAction.setEnabled(false);
				editorView.setReviewIssue(reviewIssue);
			} else {
				editorView.setNewEmptyReviewIssue(iReviewFile);
			}
			editorView.setItemFields(project, reviewId);
			editorView.setFocus();
		} catch (ReviewException e) {
			e.printStackTrace();
		}
		int type = ReviewEvent.TYPE_COMMAND;
		int kind = ReviewEvent.KIND_ADD;
		ReviewPluginImpl.getInstance().notifyListeners(type, kind);
	}

	/**
	 * Processes opening review table and review editor. If the necessary value
	 * such as project, review id, reviewer id, phase name key are not set,
	 * prompt the proper dialog to let user specify these value before the open.
	 * 
	 * @return <code>true</code> if the project, review id, and reviewer id,
	 *         were determined.
	 */
	public boolean determineProjectReviewIdReviewerId() {
		boolean isReviewSelectionWizardInvoked = false;
		IProject project = fileResource.getActiveProject();
		// assertion project should not be null.
		if (project == null) {
			ReviewDialog.processNonProjectSelectionDialog();
			return false;
		}

		// assertion review id should not be null.
		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		String reviewPhaseNameKey = reviewModel.getPhaseManager()
				.getPhaseNameKey();
		IProject reviewModelProject = reviewModel.getProjectManager()
				.getProject();
		reviewModelProject = (reviewModelProject != null) ? reviewModelProject
				: project;
		if (reviewId == null
				|| (reviewId != null && !isActiveEditorInCurrentProject(reviewModelProject))) {
			int result = ReviewDialog.processReviewIdSelectionWizardDialog(
					reviewPhaseNameKey, project, false);
			if (result == WizardDialog.CANCEL) {
				return false;
			}
			isReviewSelectionWizardInvoked = true;
		}

		// update review Id.
		project = reviewModel.getProjectManager().getProject();
		reviewId = reviewModel.getReviewIdManager().getReviewId();
		ReviewerId reviewerId = reviewModel.getReviewerIdManager()
				.getReviewerId();
		// assertion review file should not be null.
		IFile iReviewFile = fileResource.getReviewFile(project, reviewId,
				reviewerId);
		if (iReviewFile == null) {
			String titleKey = "ReviewDialog.noReviewFileDetermined.simpleConfirm.messageDialog.title";
			String title = ReviewI18n.getString(titleKey);
			String messageKey = "ReviewDialog.noReviewFileDetermined.simpleConfirm.messageDialog.message";
			String message = ReviewI18n.getString(messageKey);
			ReviewDialog.openSimpleComfirmMessageDialog(title, message);
			return false;
		}

		if (isReviewSelectionWizardInvoked) {
			ReviewIssueModel reviewIssueModel = reviewIssueModelManager
					.createReviewIssueModel(project, reviewId);
			reviewIssueModel.notifyListeners(ReviewIssueModelEvent.MERGE);
			// fill the specified phase's column data into model
			ColumnDataModelManager columnDataModelManager = new ColumnDataModelManager(
					preferenceResource);
			ColumnDataModel columnDataModel = columnDataModelManager
					.getModel(reviewPhaseNameKey);

			ReviewTableView view = ReviewTableView.getActiveView();
			// set column data model into view.
			// null happens when the view is not opened yet after Eclipse
			// startup.
			if (view == null) {
				view = ReviewTableView.bringViewToTop();
			}
			view.createColumns(columnDataModel);
			String reviewIdString = reviewId.getReviewId();
			ReviewResource reviewResource = propertyResource.getReviewResource(
					reviewIdString, true);
			if (reviewResource != null) {
				FilterPhase filterPhase = reviewResource
						.getFilterPhase(reviewPhaseNameKey);
				view.setFilterStatus(filterPhase.isEnabled());
			}
		} else {
			reviewIssueModelManager.getCurrentModel();
		}
		return true;
	}

	/**
	 * Gets the line number of the active file if any.
	 * 
	 * @return the line number of the active file if any.
	 */
	public String getLineNumber() {
		IWorkbenchWindow workbenchWindow = workbench
				.getActiveWorkbenchWindow();
		if (workbenchWindow != null) {
			IWorkbenchPage page = workbenchWindow.getActivePage();
			IEditorPart activeEditorPart = page.getActiveEditor();
			if (activeEditorPart != null) {
				IEditorSite editorSite = page.getActiveEditor().getEditorSite();
				ISelectionProvider selectionProvider = editorSite
						.getSelectionProvider();
				if (selectionProvider != null) {
					ISelection selection = selectionProvider.getSelection();
					if (selection instanceof ITextSelection) {
						this.lineNumber = String
								.valueOf(((ITextSelection) selection)
										.getStartLine() + 1);
					}
				}
			}
		}
		return this.lineNumber;
	}

	/**
	 * Checks if the active editor belongs to the current project.
	 * 
	 * @param currentProject
	 *            the <code>IProject</code>.
	 * @return <code>true</code> if the active editor belongs to the current
	 *         project. <code>false</code> otherwise.
	 */
	private boolean isActiveEditorInCurrentProject(IProject currentProject) {
		return (currentProject.getName().equals(fileResource.getActiveProject()
				.getName()));
	}

	/**
	 * Notifies this action delegate that the selection in the workbench has
	 * changed. Gets a fully qualified class name, method name, line number if
	 * applicable. Clients should not call this method.
	 * 
	 * @see org.eclipse.ui.IActionDelegate
	 *      #selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof ITextSelection) {
			ITextSelection textSelection = (ITextSelection) selection;
			this.lineNumber = String.valueOf(textSelection.getStartLine() + 1);
			this.selectedText = textSelection.getText();
		}
		IFile selectedFile = fileResource.getSelectedIFile();
		this.targetFilePath = (selectedFile != null) ? selectedFile
				.getProjectRelativePath().toString() : "";
	}

	/**
	 * Sets the active editor for the delegate.
	 * 
	 * @see org.eclipse.ui.IEditorActionDelegate
	 *      #setActiveEditor(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		// log.debug("setActiveEditor was called.");
		// Do nothing so far.
	}

	/**
	 * Disposes this action delegate. The implementor should unhook any
	 * references to itself so that garbage collection can occur.
	 */
	public void dispose() {
		// do nothing
	}

	/**
	 * Initializes this action delegate with the workbench window it will work
	 * in.
	 * 
	 * @param window
	 *            the window that provides the context for this delegate
	 */
	public void init(IWorkbenchWindow window) {
		// do nothing
	}
}
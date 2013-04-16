package edu.hawaii.ics.csdl.jupiter.ui.menu;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableItem;
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
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModel;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssueModelManager;
import edu.hawaii.ics.csdl.jupiter.ui.perspective.ReviewPerspectiveFactory;
import edu.hawaii.ics.csdl.jupiter.ui.preference.FilterPreferencePage;
import edu.hawaii.ics.csdl.jupiter.ui.view.editor.ReviewEditorView;
import edu.hawaii.ics.csdl.jupiter.ui.view.table.FilterPhase;
import edu.hawaii.ics.csdl.jupiter.ui.view.table.ReviewTableView;
import edu.hawaii.ics.csdl.jupiter.ui.view.table.ReviewTableViewAction;
import edu.hawaii.ics.csdl.jupiter.util.JupiterLogger;
import edu.hawaii.ics.csdl.jupiter.util.ReviewDialog;

/**
 * Provides phase selection menu.
 * 
 * @author Takuya Yamashita
 * @version $Id: PhaseSelectionMenu.java 84 2008-03-07 10:11:27Z jsakuda $
 */
public class PhaseSelectionMenu {

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

	public PhaseSelectionMenu() {
	}

	public PhaseSelectionMenu(FileResource fileResource,
			PreferenceResource preferenceResource,
			PropertyResource propertyResource) {
		this.fileResource = fileResource;
		this.preferenceResource = preferenceResource;
		this.propertyResource = propertyResource;
	}

	/**
	 * Creates phase selection pulldown menu.
	 * 
	 * @param menu
	 *            the menu of the parent.
	 * @return the menu of the pulldown menu.
	 */
	public Menu createPhaseSelectionPullDownMenu(Menu menu) {
		createRefreshMenu(menu);
		createPhaseSelectionMenu(menu);
		return menu;
	}

	/**
	 * Creates menu separator.
	 * 
	 * @param menu
	 *            the menu.
	 */
	private static void createSeparator(Menu menu) {
		new MenuItem(menu, SWT.SEPARATOR);
	}

	/**
	 * Creates update menu.
	 * 
	 * @param menu
	 *            the menu.
	 */
	private void createRefreshMenu(Menu menu) {
		String reviewPhaseNameKey = reviewModel.getPhaseManager()
				.getPhaseNameKey();

		// Creates refresh item if the current review phase name key exists.
		if (reviewPhaseNameKey != null) {
			MenuItem lastInvokedItem = new MenuItem(menu, SWT.NONE);
			String beforeKey = "PhaseSelectionActionDelegate.actionSet.label.default.before";
			String afterKey = "PhaseSelectionActionDelegate.actionSet.label.default.after";
			String beforeText = ReviewI18n.getString(beforeKey);
			String afterText = ReviewI18n.getString(afterKey);
			String reviewerPhaseName = ReviewI18n.getString(reviewPhaseNameKey);
			String imagePath = "icons/refresh.gif";
			lastInvokedItem.setImage(ReviewPluginImpl.createImageDescriptor(
					imagePath).createImage());
			lastInvokedItem.setText(beforeText + reviewerPhaseName + afterText);
			lastInvokedItem.setData(reviewPhaseNameKey);
			lastInvokedItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					ReviewPerspectiveFactory.showPerspective();
					String reviewPhaseNameKey = (String) event.widget.getData();
					doUpdateMenuCommand(reviewPhaseNameKey);
					int type = ReviewEvent.TYPE_COMMAND;
					int kind = ReviewEvent.KIND_PHASE_SELECTION;
					ReviewPluginImpl.getInstance().notifyListeners(type, kind);
				}
			});
			// Separator.
			createSeparator(menu);
		}
	}

	/**
	 * Creates phase selection menu.
	 * 
	 * @param menu
	 *            the menu.
	 */
	private void createPhaseSelectionMenu(Menu menu) {
		// final ReviewPluginImpl plugin = ReviewPluginImpl.getInstance();
		// ReviewModel reviewModel = ReviewModel.getInstance();
		// String reviewPhaseNameKey =
		// reviewModel.getPhaseManager().getPhaseNameKey();
		// ColumnDataModelManager columnDataModelManager =
		// ColumnDataModelManager.getInstance();
		// final ColumnDataModel columnDataModel =
		// columnDataModelManager.getModel(reviewPhaseNameKey);

		String[] menuNameKeys = preferenceResource.getPhaseArray(true);
		for (int i = 0; i < menuNameKeys.length; i++) {
			MenuItem menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setText((i + 1) + " "
					+ ReviewI18n.getString(menuNameKeys[i]));
			menuItem.setData(menuNameKeys[i]);
			String gifFile = "icons/" + (i + 1) + ".gif";
			menuItem.setImage(ReviewPluginImpl.createImageDescriptor(gifFile)
					.createImage());
			menuItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					ReviewPerspectiveFactory.showPerspective();
					String reviewPhaseNameKey = (String) event.widget.getData();
					boolean isSuccess = doSelectionMenuCommand(reviewPhaseNameKey);
					if (!isSuccess) {
						return;
					}
					int type = ReviewEvent.TYPE_COMMAND;
					int kind = ReviewEvent.KIND_PHASE_SELECTION;
					ReviewPluginImpl.getInstance().notifyListeners(type, kind);
				}
			});
		}
	}

	/**
	 * Invokes the selection menu's command.
	 * 
	 * @param reviewPhaseNameKey
	 *            the review phase name key.
	 * @return <code>true</code> if selection process was completed.
	 */
	private boolean doSelectionMenuCommand(String reviewPhaseNameKey) {
		// set the selected review phase name key int review model.
		reviewModel.notifyListeners(reviewPhaseNameKey);

		IProject project = fileResource.getActiveProject();
		// assertion project should not be null.
		if (project == null) {
			String[] projects = fileResource
					.getOpenedAndReviewIdContainedProjects();
			if (projects.length <= 0) {
				String titleKey = "ReviewDialog.noProjectOpenedAndReviewIdNotification"
						+ ".simpleConfirm.messageDialog.title";
				String title = ReviewI18n.getString(titleKey);
				String messageKey = "ReviewDialog.noProjectOpenedAndReviewIdNotification"
						+ ".simpleConfirm.messageDialog.message";
				String message = ReviewI18n.getString(messageKey);
				ReviewDialog.openSimpleComfirmMessageDialog(title, message);
				return false;
			}
			String projectName = projects[0];
			project = FileResource.getProject(projectName);
		}

		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		reviewPhaseNameKey = reviewModel.getPhaseManager().getPhaseNameKey();
		// whatever review id is null or not,
		int result = ReviewDialog.processReviewIdSelectionWizardDialog(
				reviewPhaseNameKey, project, true);
		if (result == WizardDialog.CANCEL) {
			return false;
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
			String tkey = "ReviewDialog.noReviewFileDetermined.simpleConfirm.messageDialog.title";
			String title = ReviewI18n.getString(tkey);
			String mKey = "ReviewDialog.noReviewFileDetermined.simpleConfirm.messageDialog.message";
			String message = ReviewI18n.getString(mKey);
			ReviewDialog.openSimpleComfirmMessageDialog(title, message);
			return false;
		}

		// check file written permission
		if (iReviewFile.isReadOnly()) {
			String message = "Review file " + iReviewFile
					+ " is readonly. Please make it writable "
					+ "to save your issues.";
			ReviewDialog.openSimpleComfirmMessageDialog("Review Management",
					message);
		}

		ReviewTableView tableView = ReviewTableView.getActiveView();
		String selectedIssueId = "";
		if (tableView != null) {
			TableItem[] selecteItems = tableView.getTable().getSelection();
			if (selecteItems.length > 0) {
				selectedIssueId = ((ReviewIssue) selecteItems[0].getData())
						.getIssueId();
			}
		}

		ReviewIssueModel reviewIssueModel = reviewIssueModelManager
				.createReviewIssueModel(project, reviewId);
		reviewIssueModel.notifyListeners(ReviewIssueModelEvent.MERGE);

		ColumnDataModelManager columnDataModelManager = new ColumnDataModelManager(
				preferenceResource);
		ColumnDataModel columnDataModel = columnDataModelManager
				.getModel(reviewPhaseNameKey);

		project = reviewModel.getProjectManager().getProject();
		reviewId = reviewModel.getReviewIdManager().getReviewId();
		reviewerId = reviewModel.getReviewerIdManager().getReviewerId();
		iReviewFile = fileResource.getReviewFile(project, reviewId, reviewerId);
		// null happens when the view is not opened yet after Eclipse startup.
		if (tableView == null) {
			tableView = ReviewTableView.bringViewToTop();
		}
		tableView.createColumns(columnDataModel);
		String reviewIdString = reviewId.getReviewId();
		ReviewResource reviewResource = propertyResource.getReviewResource(
				reviewIdString, true);
		IPreferenceStore store = ReviewPluginImpl.getInstance()
				.getPreferenceStore();
		String prefFilterKey = FilterPreferencePage.ENABLE_FILTER_STORE_KEY;
		boolean isPrefFilterEnabled = store.getBoolean(prefFilterKey);
		if (isPrefFilterEnabled) {
			tableView.setFilterStatus(isPrefFilterEnabled);
		} else {
			if (reviewResource != null) {
				FilterPhase filterPhase = reviewResource
						.getFilterPhase(reviewPhaseNameKey);
				tableView.setFilterStatus(filterPhase.isEnabled());
			}
		}

		// Opens editor view.
		try {
			ReviewEditorView editorView = ReviewEditorView.bringViewToTop();
			TableItem[] tableItems = tableView.getTable().getItems();
			boolean isFound = false;
			// select the previous selected item.
			for (int i = 0; i < tableItems.length; i++) {
				String issueId = ((ReviewIssue) tableItems[i].getData())
						.getIssueId();
				if (issueId.equals(selectedIssueId)) {
					tableView.getTable().select(i);
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				if (tableView.getTable().getItemCount() > 0) {
					tableView.getTable().select(0);
				} else {
					editorView.clearAllFields();
					editorView.setEnable(false);
				}
			}
			ReviewTableViewAction.NOTIFY_EDITOR.run();
			editorView.bringTagToTop(reviewPhaseNameKey);
		} catch (ReviewException e) {
			return false;
		}
		return true;
	}

	/**
	 * Invokes the update menu's command.
	 * 
	 * @param reviewPhaseNameKey
	 *            the review phase name key.
	 * @return <code>true</code> if update process was completed.
	 */
	public boolean doUpdateMenuCommand(String reviewPhaseNameKey) {
		// set the selected review phase name key into review model.
		reviewModel.notifyListeners(reviewPhaseNameKey);
		IProject project = fileResource.getActiveProject();
		// assertion project should not be null.
		if (project == null) {
			String[] projects = fileResource
					.getOpenedAndReviewIdContainedProjects();
			if (projects.length <= 0) {
				String titleKey = "ReviewDialog.noProjectOpenedAndReviewIdNotification"
						+ ".simpleConfirm.messageDialog.title";
				String title = ReviewI18n.getString(titleKey);
				String messageKey = "ReviewDialog.noProjectOpenedAndReviewIdNotification"
						+ ".simpleConfirm.messageDialog.message";
				String message = ReviewI18n.getString(messageKey);
				ReviewDialog.openSimpleComfirmMessageDialog(title, message);
				return false;
			}
			String projectName = projects[0];
			project = FileResource.getProject(projectName);
		}
		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		reviewPhaseNameKey = reviewModel.getPhaseManager().getPhaseNameKey();
		// if review id is null,
		if (reviewId == null) {
			int result = ReviewDialog.processReviewIdSelectionWizardDialog(
					reviewPhaseNameKey, project, true);
			if (result == WizardDialog.CANCEL) {
				return false;
			}
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
		// check file written permission
		if (iReviewFile.isReadOnly()) {
			String message = "Review file " + iReviewFile
					+ " is readonly. Please make it writable "
					+ "to save your issues.";
			ReviewDialog.openSimpleComfirmMessageDialog("Review Management",
					message);
		}

		ReviewTableView view = ReviewTableView.getActiveView();
		int previousIndex = 0;
		if (view != null) {
			previousIndex = view.getTable().getSelectionIndex();
		}

		ReviewIssueModel reviewIssueModel = reviewIssueModelManager
				.createReviewIssueModel(project, reviewId);
		reviewIssueModel.notifyListeners(ReviewIssueModelEvent.MERGE);

		ColumnDataModelManager columnDataModelManager = new ColumnDataModelManager(
				preferenceResource);
		ColumnDataModel columnDataModel = columnDataModelManager
				.getModel(reviewPhaseNameKey);

		project = reviewModel.getProjectManager().getProject();
		reviewId = reviewModel.getReviewIdManager().getReviewId();
		reviewerId = reviewModel.getReviewerIdManager().getReviewerId();
		iReviewFile = fileResource.getReviewFile(project, reviewId, reviewerId);
		// null happens when the view is not opened yet after Eclipse startup.
		if (view == null) {
			view = ReviewTableView.bringViewToTop();
		}
		view.createColumns(columnDataModel);
		String reviewIdString = reviewId.getReviewId();
		ReviewResource reviewResource = propertyResource.getReviewResource(
				reviewIdString, true);
		IPreferenceStore store = ReviewPluginImpl.getInstance()
				.getPreferenceStore();
		String prefFilterKey = FilterPreferencePage.ENABLE_FILTER_STORE_KEY;
		boolean isPrefFilterEnabled = store.getBoolean(prefFilterKey);
		if (isPrefFilterEnabled) {
			view.setFilterStatus(isPrefFilterEnabled);
		} else {
			if (reviewResource != null) {
				FilterPhase filterPhase = reviewResource
						.getFilterPhase(reviewPhaseNameKey);
				view.setFilterStatus(filterPhase.isEnabled());
			}
		}

		// Opens editor view.
		try {
			ReviewEditorView editorView = ReviewEditorView.bringViewToTop();
			if (previousIndex != -1
					&& previousIndex < view.getTable().getItemCount()) {
				view.getTable().select(previousIndex);
				ReviewTableViewAction.NOTIFY_EDITOR.run();
			} else {
				editorView.setEnable(false);
			}
			editorView.bringTagToTop(reviewPhaseNameKey);
		} catch (ReviewException e) {
			return false;
		}
		return true;
	}
}

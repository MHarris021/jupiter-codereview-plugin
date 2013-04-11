package edu.hawaii.ics.csdl.jupiter.ui.view.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import edu.hawaii.ics.csdl.jupiter.ReviewI18n;
import edu.hawaii.ics.csdl.jupiter.util.ResourceBundleKey;
import edu.hawaii.ics.csdl.jupiter.util.TabFolderLayout;

class ReviewTabFolder extends TabFolder {

	private ReviewEditorView view;
	
	public ReviewTabFolder(Composite parent, ReviewEditorView view) {
		super(parent, SWT.BOTTOM);
		this.view = view;
		init();

	}

	private void init() {
		setLayout(new TabFolderLayout());
		FormData folderData = new FormData();
		folderData.top = new FormAttachment(0, -3);
		folderData.left = new FormAttachment(0, -2);
		folderData.right = new FormAttachment(100, +2);
		folderData.bottom = new FormAttachment(100, 0);
		setLayoutData(folderData);
		createIndividualPhaseTabContent();
		createTeamPhaseTabContent();
		createReworkPhaseTabContent();

	}
	
		private void createIndividualPhaseTabContent() {
		TabItem individualTabItem = new TabItem(this, SWT.NONE);
		String individualKey = ResourceBundleKey.PHASE_INFIVIDUAL;
		String individualLabel = ReviewI18n.getString(individualKey);
		individualTabItem.setText(individualLabel);
		Composite composite = view.createGeneralComposite(this, 5, 5);
		view.createTypeSeverityContent(composite);
		view.createSummaryContent(composite);
		view.createIndividaulDescriptionContent(composite);
		individualTabItem.setControl(composite);
	}

	/**
	 * Creates team phase tab content in the <code>TabFolder</code>.
	 */
	private void createTeamPhaseTabContent() {
		TabItem teamTabItem = new TabItem(this, SWT.NONE);
		String teamKey = ResourceBundleKey.PHASE_TEAM;
		String teamLabel = ReviewI18n.getString(teamKey);
		teamTabItem.setText(teamLabel);
		Composite composite = view.createGeneralComposite(this, 5, 5);
		view.createAssignedToResolutionContent(composite);
		view.createAnnotationContent(composite);
		view.createTeamDescriptionContent(composite);
		teamTabItem.setControl(composite);
	}

	/**
	 * Creates rework phase tab content in the <code>TabFolder</code>.
	 */
	private void createReworkPhaseTabContent() {
		TabItem reworkTabItem = new TabItem(this, SWT.NONE);
		String reworkKey = ResourceBundleKey.PHASE_REWORK;
		String reworkLabel = ReviewI18n.getString(reworkKey);
		reworkTabItem.setText(reworkLabel);
		Composite composite = view.createGeneralComposite(this, 5, 5);
		view.createStatusContent(composite);
		view.createRevisionContent(composite);
		view.createReworkDescriptionContent(composite);
		reworkTabItem.setControl(composite);
	}

}

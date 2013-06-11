package edu.hawaii.ics.csdl.jupiter.ui.action;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;
import org.eclipse.ui.actions.ActionDelegate;

import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;
import edu.hawaii.ics.csdl.jupiter.event.ReviewEvent;
import edu.hawaii.ics.csdl.jupiter.event.ReviewIssueModelException;
import edu.hawaii.ics.csdl.jupiter.file.serializers.SerializerException;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewModel;
import edu.hawaii.ics.csdl.jupiter.ui.menu.PhaseSelectionMenu;
import edu.hawaii.ics.csdl.jupiter.ui.perspective.ReviewPerspectiveFactory;

/**
 * Provides the action to correct all review files in the directory which has
 * been specified in the preference page.
 * 
 * @author Takuya Yamashita
 * @version $Id: PhaseSelectionActionDelegate.java 40 2007-05-30 00:24:50Z
 *          hongbing $
 */
public class PhaseSelectionActionDelegate extends ActionDelegate implements
		IWorkbenchWindowPulldownDelegate {

	private PhaseSelectionMenu phaseSelectionMenu;
	private ReviewModel reviewModel;

	/**
	 * Initializes this action delegate with the workbench window it will work
	 * in.
	 * 
	 * @param window
	 *            the window that provides the context for this delegate
	 */
	public void init(IWorkbenchWindow window) {
	}

	public PhaseSelectionActionDelegate(PhaseSelectionMenu phaseSelectionMenu
			, ReviewModel reviewModel) {
		this.phaseSelectionMenu = phaseSelectionMenu;
		this.reviewModel = reviewModel;
	}

	/**
	 * Collects all review files in the directory which has been specified in
	 * the preference page.
	 * 
	 * @param action
	 *            the action proxy that handles the presentation portion of the
	 *            action
	 */
	public void run(IAction action) {
		ReviewPerspectiveFactory.showPerspective();
		String reviewPhaseNameKey = reviewModel.getPhaseManager()
				.getPhaseNameKey();
		boolean isSuccess = false;
		try {
			isSuccess = phaseSelectionMenu
					.doUpdateMenuCommand(reviewPhaseNameKey);
		} catch (SerializerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReviewIssueModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (isSuccess) {
			int type = ReviewEvent.TYPE_COMMAND;
			int kind = ReviewEvent.KIND_PHASE_SELECTION;
			ReviewPluginImpl.getInstance().notifyListeners(type, kind);
		}
	}

	/**
	 * Returns the menu for this pull down action.
	 * 
	 * @param parent
	 *            the parent of <code>Control</code> instance.
	 * 
	 * @return the <code>Menu</code> instance to be filled with the sets of the
	 *         code review mode.
	 */
	public Menu getMenu(Control parent) {
		return phaseSelectionMenu.createPhaseSelectionPullDownMenu(new Menu(
				parent));
	}
}

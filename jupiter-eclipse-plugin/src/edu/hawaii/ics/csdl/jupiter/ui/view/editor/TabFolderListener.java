/**
 * 
 */
package edu.hawaii.ics.csdl.jupiter.ui.view.editor;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;

/**
 * @author TETN
 * 
 */
class TabFolderListener implements Listener {

	private ReviewEditorView view;

	public TabFolderListener(ReviewEditorView view) {
		this.view = view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.
	 * Event)
	 */
	public void handleEvent(Event event) {
		TabFolder folder = (TabFolder) event.widget.getData();
		view.handleTabChangeEvent(folder);
	}

}

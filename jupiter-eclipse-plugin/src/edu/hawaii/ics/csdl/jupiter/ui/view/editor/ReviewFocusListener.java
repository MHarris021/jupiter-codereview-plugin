package edu.hawaii.ics.csdl.jupiter.ui.view.editor;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;

import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;
import edu.hawaii.ics.csdl.jupiter.event.ReviewEvent;

class ReviewFocusListener implements FocusListener {

	public void focusGained(FocusEvent focusEvent) {
		int type = ReviewEvent.TYPE_FOCUS;
	    int kind = ReviewEvent.KIND_EDITOR;
	    ReviewPluginImpl.getInstance().notifyListeners(type, kind);
	}

	public void focusLost(FocusEvent arg0) {
		
	}

}

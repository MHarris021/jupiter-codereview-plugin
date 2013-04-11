package edu.hawaii.ics.csdl.jupiter.ui.property;

import java.util.Map;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import edu.hawaii.ics.csdl.jupiter.ReviewI18n;
import edu.hawaii.ics.csdl.jupiter.file.FieldItem;

public class ReviewComboListener implements Listener {

	private String fieldItemId;
	private Map<String, FieldItem> map;
	private Combo combo;
	
	public ReviewComboListener(Combo combo, String fieldItemId, Map<String, FieldItem> map) {
		this.combo = combo;
		this.fieldItemId = fieldItemId;
		this.map = map;
		
	}
	
	public void handleEvent(Event arg0) {
		FieldItem fieldItem = (FieldItem) map.get(fieldItemId);
        fieldItem.setDefaultKey(ReviewI18n.getKey(combo.getText()));
	}

}

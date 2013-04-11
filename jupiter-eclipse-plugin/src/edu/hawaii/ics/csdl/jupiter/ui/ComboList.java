package edu.hawaii.ics.csdl.jupiter.ui;

import java.util.List;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class ComboList extends Combo {

	public ComboList(Composite parent, int style) {
		super(parent, style);
		
	}
	
	public void setItems(List<String> items) {
		String[] itemArray = new String[items.size()]; 
		itemArray =	items.toArray(itemArray);
		super.setItems(itemArray);
	}

}

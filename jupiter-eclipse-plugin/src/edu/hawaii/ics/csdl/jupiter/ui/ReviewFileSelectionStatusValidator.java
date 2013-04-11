package edu.hawaii.ics.csdl.jupiter.ui;

import java.io.File;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;

/**
 * Validates that the selection of review files is valid.
 * 
 * @author jsakuda
 */
public class ReviewFileSelectionStatusValidator implements
		ISelectionStatusValidator {

	/**
	 * Validates an array of elements and returns the resulting status.
	 * 
	 * @param selection
	 *            The elements to validate.
	 * @return Returns error status if the selection contains directories,
	 *         otherwise returns ok status.
	 */
	public IStatus validate(Object[] selection) {

		for (Object selectionItem : selection) {
			if (selectionItem instanceof File) {
				File file = (File) selectionItem;
				if (file.isDirectory()) {
					return new Status(Status.ERROR, ReviewPluginImpl.PLUGIN_ID,
							"Please select only files.");
				}
			}
		}
		return Status.OK_STATUS;
	}
}
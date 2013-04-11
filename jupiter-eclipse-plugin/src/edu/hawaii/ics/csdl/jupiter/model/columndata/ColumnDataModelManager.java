package edu.hawaii.ics.csdl.jupiter.model.columndata;

import edu.hawaii.ics.csdl.jupiter.file.PreferenceResource;

/**
 * Provides the column data model manager.
 * 
 * @author Takuya Yamashita
 * @version $Id: ColumnDataModelManager.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class ColumnDataModelManager {
	private ColumnDataModel model;
	private PreferenceResource preferenceResource;

	/**
	 * Instantiates <code>ColumnDataModel</code> instance. Prohibits clients
	 * from instantiating this.
	 */
	public ColumnDataModelManager(PreferenceResource preferenceResource) {
		this.model = new ColumnDataModel();
		this.preferenceResource = preferenceResource;
	}

	/**
	 * Gets the <code>ColumnDataModel</code> instance.
	 * 
	 * @param reviewPhaseNameKey
	 *            the review phase name key.
	 * @return the <code>ColumnDataModel</code> instance.
	 */
	public ColumnDataModel getModel(String reviewPhaseNameKey) {
		this.model.clear();
		preferenceResource.loadColumnData(reviewPhaseNameKey, this.model);
		return this.model;
	}

	/**
	 * Gets the empty <code>ColumnDataModel</code> instance.
	 * 
	 * @return the empty <code>ColumnDataModel</code> instance.
	 */
	public ColumnDataModel getModel() {
		return this.model;
	}
}

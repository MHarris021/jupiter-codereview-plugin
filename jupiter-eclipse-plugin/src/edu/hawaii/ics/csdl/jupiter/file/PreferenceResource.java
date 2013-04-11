package edu.hawaii.ics.csdl.jupiter.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.hawaii.ics.csdl.jupiter.ReviewI18n;
import edu.hawaii.ics.csdl.jupiter.file.preference.ColumnEntry;
import edu.hawaii.ics.csdl.jupiter.file.preference.Phase;
import edu.hawaii.ics.csdl.jupiter.file.preference.Preference;
import edu.hawaii.ics.csdl.jupiter.file.serializers.PreferenceSerializer;
import edu.hawaii.ics.csdl.jupiter.file.serializers.SerializerException;
import edu.hawaii.ics.csdl.jupiter.file.util.ColumnEntryBuilder;
import edu.hawaii.ics.csdl.jupiter.model.columndata.ColumnData;
import edu.hawaii.ics.csdl.jupiter.model.columndata.ColumnDataBuilder;
import edu.hawaii.ics.csdl.jupiter.model.columndata.ColumnDataModel;
import edu.hawaii.ics.csdl.jupiter.util.JupiterLogger;

/**
 * Provides data-filled manager objects. Clients can pass an object to get
 * proper data.
 * 
 * @author Takuya Yamashita
 * @version $Id: PrefResource.java 84 2008-03-07 10:11:27Z jsakuda $
 */
@Component
public class PreferenceResource {
	/** Jupiter logger */
	private static JupiterLogger log = JupiterLogger.getLogger();

	/** The loaded preference. */
	private Preference preference;

	private PreferenceSerializer preferenceSerializer;

	@Autowired
	public PreferenceResource(PreferenceSerializer serializer) {
		this.preferenceSerializer = serializer;
		try {
			preference = this.preferenceSerializer.loadPreference();
		} catch (SerializerException e) {
			log.error(e);
		}
	}

	/**
	 * Stores the <code>ColumnDataManager</code> instance into configuration XML
	 * file.
	 * 
	 * @param reviewPhaseNameKey
	 *            the review phase name key.
	 * @param columnDataModel
	 *            the <code>ColumnDataManager</code> instance.
	 */
	public void storeColumnDataModel(String reviewPhaseNameKey,
			ColumnDataModel columnDataModel) {
		List<ColumnData> columnDataArray = Arrays.asList(columnDataModel
				.getAllColumnDataArray());

		Phase phase = getPhase(reviewPhaseNameKey);
		if (phase != null) {
			// clear out all column entries
			List<ColumnEntry> columnEntries = phase.getColumnEntry();
			columnEntries.clear();

			for (ColumnData columnData : columnDataArray) {

				ColumnEntry columnEntry = ColumnEntryBuilder.build(columnData);
				columnEntries.add(columnEntry);
			}
			try {
				preferenceSerializer.serialize(preference);
			} catch (SerializerException e) {
				log.error(e);
			}
		}
	}

	/**
	 * Gets the default review phase name.
	 * 
	 * @return the default review phase name.
	 */
	public String getDefaultPhaseNameKey() {
		return preference.getView().getDefault();
	}

	/**
	 * Gets the array of <code>String</code> review phase names or phase name
	 * keys depending upon the argument option. Returns the phase name keys if
	 * isKey is true. Otherwise returns the phase names
	 * 
	 * @param isKey
	 *            true if returning values are array of phase name keys.
	 * @return the array of <code>String</code> review phase name keys or phase
	 *         names.
	 */
	public String[] getPhaseArray(boolean isKey) {
		List<Phase> phases = preference.getView().getPhase();
		List<String> phaseList = new ArrayList<String>();
		for (Phase phase : phases) {
			String phaseNameKey = phase.getName();
			String phaseString = (isKey) ? phaseNameKey : ReviewI18n
					.getString(phaseNameKey);
			phaseList.add(phaseString);
		}
		return phaseList.toArray(new String[] {});
	}

	/**
	 * Gets the update URL string.
	 * 
	 * @return the update URL string.
	 */
	public String getUpdateUrl() {
		return preference.getGeneral().getUpdateUrl();
	}

	/**
	 * Gets the boolean enable update value.
	 * 
	 * @return true if update is enabled.
	 */
	public boolean getEnableUpdate() {
		return preference.getGeneral().isEnableUpdate();
	}

	/**
	 * Gets the boolean enable filter value.
	 * 
	 * @return true if filter is enabled.
	 */
	public boolean getEnableFilter() {
		return preference.getGeneral().isEnableFilter();
	}

	/**
	 * Loads ColumnData instances to the column data model.
	 * 
	 * @param phaseNameKey
	 *            the phase name key.
	 * @param columnDataModel
	 *            the column data model.
	 */
	public void loadColumnData(String phaseNameKey,
			ColumnDataModel columnDataModel) {
		columnDataModel.clear();
		columnDataModel.addAll(getColumnDataList(phaseNameKey));
	}

	/**
	 * Gets the list of the ColumnData instances given the phase name key.
	 * 
	 * @param phaseNameKey
	 *            the phase name key.
	 * @return the list of the ColumnData instances given the phase name key.
	 */
	public List<ColumnData> getColumnDataList(String phaseNameKey) {
		List<ColumnData> columnDataList = new ArrayList<ColumnData>();
		Phase phase = getPhase(phaseNameKey);
		for (ColumnEntry entry : phase.getColumnEntry()) {
			ColumnData columnData = ColumnDataBuilder.build(entry);
			columnDataList.add(columnData);
		}
		return columnDataList;
	}

	/**
	 * Gets the <code>Phase</code> associated with the review phase name key.
	 * 
	 * @param reviewPhaseNameKey
	 *            The name key of the review phase to get.
	 * @return Returns the <code>Phase</code> with the given key or null if
	 *         cannot be found.
	 */
	private Phase getPhase(String reviewPhaseNameKey) {
		List<Phase> phases = preference.getView().getPhase();
		for (Phase phase : phases) {
			if (phase.getName().equals(reviewPhaseNameKey)) {
				return phase;
			}
		}
		return null;
	}
}

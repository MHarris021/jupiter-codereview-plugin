package edu.hawaii.ics.csdl.jupiter.file.util;

import edu.hawaii.ics.csdl.jupiter.file.preference.ColumnEntry;
import edu.hawaii.ics.csdl.jupiter.model.columndata.ColumnData;

public class ColumnEntryBuilder {

	public static ColumnEntry build(ColumnData columnData) {
		int width = columnData.getColumnPixelData().width;
		boolean resizable = columnData.getColumnPixelData().resizable;
		String columnNameKey = columnData.getColumnNameKey();
		boolean isEnabled = columnData.isEnabled();
		ColumnEntry columnEntry = build(width, resizable, columnNameKey,
				isEnabled);

		return columnEntry;
	}

	public static ColumnEntry build(int width, boolean resizable,
			String columnNameKey, boolean isEnabled) {
		// create column data entry from scratch
		ColumnEntry columnEntry = new ColumnEntry();
		columnEntry.setWidth(width);
		columnEntry.setResizable(resizable);
		columnEntry.setName(columnNameKey);
		columnEntry.setEnable(isEnabled);
		return columnEntry;

	}

}

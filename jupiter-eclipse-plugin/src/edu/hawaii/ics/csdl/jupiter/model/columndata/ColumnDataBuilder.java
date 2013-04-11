package edu.hawaii.ics.csdl.jupiter.model.columndata;

import org.eclipse.jface.viewers.ColumnPixelData;

import edu.hawaii.ics.csdl.jupiter.file.preference.ColumnEntry;

public class ColumnDataBuilder {

	public static ColumnData build(String columnNameKey, int width,
			boolean resizeable, boolean enable) {
		ColumnPixelData pixelData = new ColumnPixelData(width, resizeable);
		ColumnData columnData = new ColumnData(columnNameKey, pixelData, enable);
		return columnData;
	}
	
	public static ColumnData build(ColumnEntry columnEntry) {
		String columnNameKey = columnEntry.getName();
		boolean enable = columnEntry.isEnable();
		int width = columnEntry.getWidth();
		boolean resizeable = columnEntry.isResizable();
		ColumnData columnData = build(columnNameKey, width, resizeable, enable);
		return columnData;
	}

}

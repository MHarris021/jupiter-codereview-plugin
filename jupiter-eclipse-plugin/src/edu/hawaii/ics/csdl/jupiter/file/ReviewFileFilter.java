package edu.hawaii.ics.csdl.jupiter.file;

import java.io.File;
import java.io.FileFilter;

public class ReviewFileFilter implements FileFilter {

	public boolean accept(File pathname) {
		boolean result = false;
		if (pathname.getName().endsWith(".review")) {
			result = true;
		}
		return result;
	}

}

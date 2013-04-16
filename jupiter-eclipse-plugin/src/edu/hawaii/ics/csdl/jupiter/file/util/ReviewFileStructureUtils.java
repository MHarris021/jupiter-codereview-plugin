package edu.hawaii.ics.csdl.jupiter.file.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;

public class ReviewFileStructureUtils {

	public static boolean verify(File xmlFile) {
		boolean verify = true;
		if (xmlFile == null) {
			verify = false;
			throw new IllegalArgumentException("File instance is null.");
		}

		if (!xmlFile.exists()) {
			verify = false;
		} else if (!xmlFile.canWrite()) {
			verify = false;
		}
		return verify;
	}

	public static void create(File xmlFile, ReviewId reviewId)
			throws IOException {
		if (xmlFile.exists()) {
			FileUtils.forceDelete(xmlFile);
		}
		if (!xmlFile.getParentFile().exists()) {
			FileUtils.forceMkdir(xmlFile.getParentFile());
		}
		xmlFile.createNewFile();
		if (!xmlFile.canWrite()) {
			throw new SecurityException(xmlFile
					+ " is not writable to record review issue. "
					+ "Please make it writable before proceed. Issue from "
					+ reviewId.getAuthor() + reviewId.getAuthor()
					+ " with message \"" + reviewId.getDescription()
					+ "\" is not saved.");
		}
	}

}

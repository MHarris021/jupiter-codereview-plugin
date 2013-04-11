package edu.hawaii.ics.csdl.jupiter.file.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.hawaii.ics.csdl.jupiter.file.review.ReviewDate;

public class ReviewDateBuilder {

	/**
	 * Creates the <code>Date</code> instance associated with the
	 * <code>dateString</code>. Note the this returns current time
	 * <code>Date</code> instance if <code>dateString</code> could not be parsed
	 * with <code>dateFormat</code>.
	 * 
	 * @param dateString
	 *            the date string to be parsed.
	 * @param dateFormat
	 *            the date format to let parser know the date string to be
	 *            parsed.
	 * 
	 * @return the <code>Date</code> instance associated with the
	 *         <code>dateString</code>.
	 * @throws ParseException
	 */
	public static Date createDate(String dateString, String dateFormat)
			throws ParseException {
		return new SimpleDateFormat(dateFormat).parse(dateString);

	}

	public static Date createDate(ReviewDate reviewDate) throws ParseException {
		return createDate(reviewDate.getValue(), reviewDate.getFormat());
	}

	public static ReviewDate createReviewDate(Date date, String dateFormat,
			SimpleDateFormat simpleDateFormat) {
		ReviewDate reviewDate = new ReviewDate();
		reviewDate.setFormat(dateFormat);
		reviewDate.setValue(simpleDateFormat.format(date));
		return reviewDate;
	}

}

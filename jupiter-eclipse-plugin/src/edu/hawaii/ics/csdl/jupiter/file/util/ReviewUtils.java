package edu.hawaii.ics.csdl.jupiter.file.util;

import java.io.File;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import edu.hawaii.ics.csdl.jupiter.file.review.Review;
import edu.hawaii.ics.csdl.jupiter.file.serializers.ReviewSerializer;
import edu.hawaii.ics.csdl.jupiter.file.serializers.SerializerException;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;

public class ReviewUtils implements ApplicationContextAware{

	private ReviewSerializer reviewSerializer;
	
	private static ReviewUtils singleton;
	
	private ReviewUtils() {
		
	}
	
	public static ReviewUtils getInstance() {
		if(singleton == null){
			singleton = new ReviewUtils();
		}
		return singleton;
	}
	
	
	/**
	 * Checks if the passing <code>File</code> instance is associated with
	 * <code>String</code> review ID.
	 * 
	 * @param reviewId
	 *            the review ID.
	 * @param reviewFile
	 *            the review file to be checked.
	 * @return <code>true</code> if the code>File</code> instance is associated
	 *         with <code>String</code>.
	 * @throws SerializerException
	 */
	public static boolean isReviewIdAssociatedwithReview(ReviewId reviewId,
			Review review) throws SerializerException {
		String reviewIdName = review.getId();
		return (reviewIdName != null && reviewIdName.equals(reviewId
				.getReviewId()));
	}
	
	public static boolean isReviewIdAssociatedwithFile(ReviewId reviewId,
			File file) throws SerializerException {
		Review review = singleton.reviewSerializer.deserialize(file);
		return isReviewIdAssociatedwithReview(reviewId, review);
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		reviewSerializer = applicationContext.getBean(ReviewSerializer.class);
	}

}

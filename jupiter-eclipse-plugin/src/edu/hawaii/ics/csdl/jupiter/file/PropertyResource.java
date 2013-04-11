package edu.hawaii.ics.csdl.jupiter.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import edu.hawaii.ics.csdl.jupiter.ReviewException;
import edu.hawaii.ics.csdl.jupiter.file.property.Property;
import edu.hawaii.ics.csdl.jupiter.file.property.Review;
import edu.hawaii.ics.csdl.jupiter.file.serializers.ISerializer;
import edu.hawaii.ics.csdl.jupiter.file.serializers.PropertySerializer;
import edu.hawaii.ics.csdl.jupiter.file.serializers.SerializerException;
import edu.hawaii.ics.csdl.jupiter.file.util.ReviewBuilder;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewerId;
import edu.hawaii.ics.csdl.jupiter.util.JupiterLogger;

/**
 * Provides the review id wrapper singleton class.
 * 
 * @author Takuya Yamashita
 * @version $Id: PropertyResource.java 180 2011-01-18 03:56:36Z jsakuda $
 */
public class PropertyResource {
	/** Jupiter logger */
	private JupiterLogger log = JupiterLogger.getLogger();

	private Map<String, ReviewId> reviewIdMap;
	private IProject project;
	private Property property;
	private Map<String, Review> reviewIdReviewMap;
	private PropertySerializer serializer;

	/** The default review id. */
	public static final String DEFAULT_ID = PropertyConstraints.DEFAULT_REVIEW_ID;
	public static final String PROPERTY_XML_FILE = ".jupiter";

	/**
	 * Prohibits clients from instantiating this.
	 */
	public PropertyResource(ISerializer<Property> serializer) {
		this.reviewIdMap = new TreeMap<String, ReviewId>();
		this.reviewIdReviewMap = new TreeMap<String, Review>();
		this.serializer = new PropertySerializer(serializer);
	}

	/**
	 * Gets the review id wrapper class for the project.
	 * 
	 * @param project
	 *            the project.
	 * @param isDefaultLoaded
	 *            sets <code>true</code> if the default review id is loaded too.
	 * @return the review id wrapper class which contains set of
	 *         <code>ReviewID</code> instances.
	 */
	public void initialize(IProject project, ISerializer<Property> serializer) {
		setProject(project);
		fillReviewIdReviewMap(project);
		fillReviewIdMap();
	}

	/**
	 * Sets project and isDefaultLoaded values.
	 * 
	 * @param project
	 *            the project.
	 * @param isDefaultLoaded
	 *            set true if default review id is also loaded.
	 */
	private void setProject(IProject project) {
		this.project = project;

	}

	/**
	 * Loads the <code>Review</code>s.
	 * 
	 * @param project
	 *            The project containing the reviews.
	 * @param isDefaultLoaded
	 *            True if the default review ID should be loaded, otherwise
	 *            false.
	 */
	private void fillReviewIdReviewMap(IProject project) {
		try {
			this.property = serializer.newProperty(project);
		} catch (ReviewException e) {
			log.error(e);
		} catch (SerializerException e) {
			log.error(e);
		}
		if (this.property == null) {
			return;
		}

		this.reviewIdReviewMap.clear();
		List<Review> reviews = this.property.getReviews();
		for (Review review : reviews) {
			String reviewId = review.getId();
			if (reviewId.equals(PropertyConstraints.DEFAULT_REVIEW_ID)) {
				// don't load the default review id
				continue;
			}
			this.reviewIdReviewMap.put(reviewId, review);
		}
	}

	/**
	 * Gets the <code>ReviewResource</code> instance associating with the review
	 * id. Returns null if the review id does not exist.
	 * 
	 * @param reviewId
	 *            the review id.
	 * @param isClone
	 *            true if the <code>ReviewResource</code> contains the clone of
	 *            the review element. false if it contains the review element of
	 *            the document.
	 * @return the <code>ReviewResource</code> instance associating with the
	 *         review id. Returns null if the review id does not exist.
	 */
	public ReviewResource getReviewResource(String reviewId, boolean isClone) {
		Review review = this.reviewIdReviewMap.get(reviewId);
		if (review != null) {
			review = (isClone) ? ReviewBuilder.build(review) : review;
			return new ReviewResource(review);
		}
		return null;
	}

	/**
	 * Loads <code>ReviewId</code> instances for the project into review id map.
	 */
	private void fillReviewIdMap() {
		this.reviewIdMap.clear();
		for (Review review : this.reviewIdReviewMap.values()) {
			ReviewResource reviewResource = new ReviewResource(review);
			ReviewId reviewId = reviewResource.getReviewId();
			this.reviewIdMap.put(reviewId.getReviewId(), reviewId);
		}
	}

	/**
	 * Gets the array of the <code>String</code> review id names. Note that the
	 * order of the elements is reverse chronological order in which review id
	 * was created.
	 * 
	 * @return the array of the <code>String</code> review id names
	 */
	public String[] getReviewIdNames() {
		List<ReviewId> reviewIdList = getReviewIdList();
		Map<Date, String> reviewIdNameMap = new TreeMap<Date, String>(
				new Comparator<Date>() {
					public int compare(Date object1, Date object2) {
						return object2.compareTo(object1);
					}
				});
		for (Iterator<ReviewId> i = reviewIdList.iterator(); i.hasNext();) {
			ReviewId reviewId = (ReviewId) i.next();
			reviewIdNameMap.put(reviewId.getDate(), reviewId.getReviewId());
		}
		return new ArrayList<String>(reviewIdNameMap.values())
				.toArray(new String[] {});
	}

	/**
	 * Gets the array of the <code>String</code> reviewer id names.
	 * 
	 * @param reviewIdName
	 *            the review id.
	 * @return the array of the <code>String</code> review id names
	 */
	public String[] getReviewerIdNames(String reviewIdName) {
		Map<String, ReviewerId> reviewers = getReviewers(reviewIdName);
		return new ArrayList<String>(reviewers.keySet())
				.toArray(new String[] {});
	}

	/**
	 * Gets the map of the <code>ReviewerId</code> instances. Returns the map of
	 * default reviewers if no reviewer exists.
	 * 
	 * @param reviewIdName
	 *            the review id name.
	 * @return the <code>Map</code> of the <code>ReviewerId</code> instance.
	 */
	public Map<String, ReviewerId> getReviewers(String reviewIdName) {
		ReviewId reviewId = this.reviewIdMap.get(reviewIdName);
		Map<String, ReviewerId> reviewersMap = new TreeMap<String, ReviewerId>();
		if (reviewId != null) {
			reviewersMap = reviewId.getReviewers();
		}
		return reviewersMap;
	}

	/**
	 * Gets the list of the <code>ReviewId</code> instances.
	 * 
	 * @return the list of the <code>ReviewId</code> instances.
	 */
	public List<ReviewId> getReviewIdList() {
		return new ArrayList<ReviewId>(this.reviewIdMap.values());
	}

	/**
	 * Gets the <code>ReviewId</code> instance from the reviewIdName. Returns
	 * null if the <code>reviewIdName</code> does not exist.
	 * 
	 * @param reviewIdName
	 *            the review id name.
	 * @return the <code>ReviewId</code> instance from the reviewIdName. Returns
	 *         null if the review id name does not exist.
	 */
	public ReviewId getReviewId(String reviewIdName) {
		return this.reviewIdMap.get(reviewIdName);
	}

	/**
	 * Adds <code>ReviewResource</code> instance to the property config file.
	 * 
	 * @param reviewResource
	 *            the <code>ReviewResource</code> instance.
	 * @throws ReviewException
	 *             if the review id could not be written.
	 * @return <code>true</code> if review id does not exist and could be
	 *         written. <code>false</code> if review id already exist.
	 */
	public boolean addReviewResource(ReviewResource reviewResource)
			throws ReviewException {
		if (reviewResource != null) {
			Review review = reviewResource.getReview();
			ReviewId reviewId = reviewResource.getReviewId();

			this.property.getReviews().add(review);
			File propertyFile = returnPropertyFile(project);
			try {
				serializer.serialize(this.property, propertyFile);
			} catch (SerializerException e) {
				log.error(e);
			}
			this.reviewIdReviewMap.put(reviewId.getReviewId(), review);
			this.reviewIdMap.put(reviewId.getReviewId(), reviewId);
			return true;
		}

		return false;
	}

	/**
	 * Removes <code>ReviewId</code> instance from the property config file.
	 * 
	 * @param reviewId
	 *            the <code>ReviewId</code> instance.
	 * @throws ReviewException
	 *             if the review id could not be written.
	 * @return <code>true</code> if review id exists and could be written.
	 *         <code>false</code> if review id does not exist.
	 */
	public boolean removeReviewResource(ReviewId reviewId)
			throws ReviewException {
		ReviewResource reviewResource = getReviewResource(
				reviewId.getReviewId(), false);
		if (reviewResource != null) {
			Review review = reviewResource.getReview();
			this.property.getReviews().remove(review);
			File propertyFile = returnPropertyFile(project);
			try {
				serializer.serialize(this.property, propertyFile);
			} catch (SerializerException e) {
				log.error(e);
			}
			this.reviewIdReviewMap.remove(reviewId.getReviewId());
			this.reviewIdMap.remove(reviewId.getReviewId());
			return true;
		}

		return false;
	}

	private File returnPropertyFile(IProject project) {
		IFile iFile = project.getFile(PROPERTY_XML_FILE);
		File file = iFile.getLocation().toFile();
		return file;
	}
}

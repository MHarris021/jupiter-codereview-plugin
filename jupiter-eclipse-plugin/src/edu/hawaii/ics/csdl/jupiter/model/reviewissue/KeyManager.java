package edu.hawaii.ics.csdl.jupiter.model.reviewissue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.resources.IProject;

import edu.hawaii.ics.csdl.jupiter.ReviewI18n;
import edu.hawaii.ics.csdl.jupiter.file.PropertyConstraints;
import edu.hawaii.ics.csdl.jupiter.file.PropertyResource;
import edu.hawaii.ics.csdl.jupiter.file.ReviewResource;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;

/**
 * Provides the order (priority) of an item. The main purpose is to search the
 * index of a certain keys type of the item.
 * 
 * @author Takuya Yamashita
 * @version $Id: KeyManager.java 81 2008-02-17 08:06:25Z jsakuda $
 */
public abstract class KeyManager<T extends Ordinal> {
	/** The keys for the localized value in the item. */
	protected SortedMap<String, T> ordinalKeys;
	/** The key-value map. */
	protected Map<String, String> keyValues;
	/** The key-localizedLabel map. */
	protected Map<String, String> keyLocalizedLabels;
	/** The localizedLabel-key map. */
	protected Map<String, String> localizedLabelKeys;
	private PropertyResource propertyResource;

	/**
	 * Instantiates this object.
	 */
	protected KeyManager() {
		this.ordinalKeys = new TreeMap<String, T>();
		this.keyValues = new HashMap<String, String>();
		this.keyLocalizedLabels = new HashMap<String, String>();
		this.localizedLabelKeys = new HashMap<String, String>();
	}

	/**
	 * Adds the key and its value of an item. Ignore the second keys if the keys
	 * already exist in the manager (i.e No duplication for the keys). Sets
	 * empty string if key or/and its value is null.
	 * 
	 * @param ordinal
	 *            the keys to add in the manager.
	 * @param value
	 *            the value associated with the key.
	 */
	public void add(T ordinal, String value) {
		if (!ordinalKeys.containsValue(ordinal)) {
			ordinalKeys.put(ordinal.getKey(), ordinal);
			keyLocalizedLabels.put(ordinal.getKey(), ReviewI18n.getString(ordinal.getKey()));
			keyValues.put((ordinal.getKey() != null) ? ordinal.getKey() : "",
					(value != null) ? value : "");
			localizedLabelKeys.put(ReviewI18n.getString(ordinal.getKey()), ordinal.getKey());
		}
	}

	
	/**
	 * Gets the value corresponding to the key.
	 * 
	 * @param key
	 *            the key to get the value.
	 * @return the value for the key. Returns the key if the value corresponding
	 *         to the key is not found.
	 */
	public String getValue(String key) {
		Object value = this.keyValues.get(key);
		if (value != null) {
			return (String) value;
		}
		return key;
	}
	
	public T getDefault() {
		String key = ordinalKeys.firstKey();
		return ordinalKeys.get(key);
	}

	/**
	 * Gets the localized label from the key.
	 * 
	 * @param key
	 *            the key to get the localized label.
	 * @return the localized label from the key. Returns the key if the
	 *         corresponding label is not found.
	 */
	public String getLocalizedLabel(String key) {
		Object object = this.keyLocalizedLabels.get(key);
		if (object != null) {
			return (String) object;
		}
		return key;
	}

	/**
	 * Gets the key from the localized label.
	 * 
	 * @param localizedLabel
	 *            the localized label to get the corresponding key.
	 * @return the key from the localized label. Returns the localized label if
	 *         the corresponding key is not found.
	 */
	public String getKey(String localizedLabel) {
		Object object = this.localizedLabelKeys.get(localizedLabel);
		if (object != null) {
			return (String) object;
		}
		return localizedLabel;
	}

	/**
	 * Returns the size of the manager.
	 * 
	 * @return the size of the manager.
	 */
	public int size() {
		return this.ordinalKeys.size();
	}

	/**
	 * Returns the array of localized label string with the key order.
	 * 
	 * @return the array of localized label string.
	 */
	public List<String> getElements() {
		List<String> localizedLabels = new ArrayList<String>(ordinalKeys.keySet());
		return localizedLabels;
	}

	/**
	 * Clears all internal data structures.
	 */
	public void clear() {
		this.ordinalKeys.clear();
		this.keyValues.clear();
		this.keyLocalizedLabels.clear();
		this.localizedLabelKeys.clear();
	}

	/**
	 * Gets the <code>Status</code> instance from the status key. Returns the
	 * instance with empty string key and Integer.MAX_VALUE ordinal number if
	 * the corresponding item is not found.
	 * 
	 * @param key
	 *            the status key.
	 * 
	 * @return the object, which is able to cast <code>Status</code> instance.
	 */
	public T getItemObject(String key) {
		if ((key != null) && (!key.equals(""))) {
			return ordinalKeys.get(key);
		}
		return (T) new Object();
	}

	

	/**
	   * Loads resolution key.
	   * @param project the project.
	   * @param reviewId the review id.
	   */
	protected void loadKey(IProject project, ReviewId reviewId) {
	    String reviewIdName = reviewId.getReviewId();
	    ReviewResource reviewResource = propertyResource.getReviewResource(reviewIdName, true);
	    if (reviewResource != null) {
	      reviewResource.loadEntryKey(PropertyConstraints.ATTRIBUTE_VALUE_SEVERITY, this);
	    }
	  }
}

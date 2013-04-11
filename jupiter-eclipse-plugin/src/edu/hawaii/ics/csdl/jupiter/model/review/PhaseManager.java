package edu.hawaii.ics.csdl.jupiter.model.review;

import edu.hawaii.ics.csdl.jupiter.file.PreferenceResource;

/**
 * Provides the singleton review phase manager class.
 * @author Takuya Yamashita
 * @version $Id: PhaseManager.java 40 2007-05-30 00:24:50Z hongbing $
 */
public class PhaseManager implements IReviewModelElementChangeListener {
  private String phaseNameKey;
  private String[] ordinalPhaseArray;
  
  /**
   * Sets default review phase name key. Prohibits clients from instantiating this.
   */
  public PhaseManager(PreferenceResource preferenceResource) {
    this.phaseNameKey = preferenceResource.getDefaultPhaseNameKey();
    this.ordinalPhaseArray = preferenceResource.getPhaseArray(false);
  }
   
  /**
   * Sets phase name key if the notified object is instance of String.
   * @param object The object to be notified.
   */
  public void elementChanged(Object object) {
    if (object instanceof String) {
      setPhaseNameKey((String) object);
    }
  }
  
  /**
   * Sets the review phase name key string.
   * @param phaseNameKey the review phase name string.
   */
  private void setPhaseNameKey(String phaseNameKey) {
    this.phaseNameKey = phaseNameKey;
  }
  
  /**
   * Gets the review phase name key string.
   * @return the review phase name key string.
   */
  public String getPhaseNameKey() {
    return this.phaseNameKey;
  }
  
  /**
   * Gets the elements of the String review phase array.
   * @return the elements of the String review phase array.
   */
  public String[] getElements() {
    return this.ordinalPhaseArray;
  }
}

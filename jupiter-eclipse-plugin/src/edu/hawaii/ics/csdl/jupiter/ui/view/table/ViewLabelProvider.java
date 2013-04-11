package edu.hawaii.ics.csdl.jupiter.ui.view.table;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import edu.hawaii.ics.csdl.jupiter.ReviewPluginImpl;
import edu.hawaii.ics.csdl.jupiter.file.FileResource;
import edu.hawaii.ics.csdl.jupiter.model.columndata.ColumnDataModel;
import edu.hawaii.ics.csdl.jupiter.model.columndata.ColumnDataModelManager;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewId;
import edu.hawaii.ics.csdl.jupiter.model.review.ReviewModel;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.KeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ResolutionKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.ReviewIssue;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Severity;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.SeverityKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.Status;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.StatusKeyManager;
import edu.hawaii.ics.csdl.jupiter.model.reviewissue.TypeKeyManager;
import edu.hawaii.ics.csdl.jupiter.util.JupiterLogger;
import edu.hawaii.ics.csdl.jupiter.util.ResourceBundleKey;

/**
 * Provides the label for the code review view.
 * 
 * @author Takuya Yamashita
 * @version $Id: ViewLabelProvider.java 170 2009-10-08 08:38:34Z jsakuda $
 */
class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
	/** Jupiter logger */
	private static JupiterLogger log = JupiterLogger.getLogger();

	/** The date format for the creation date. */
	private static final DateFormat DATE_FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd");

	private Image linkImage;

	private ColumnDataModelManager columnDataModelManager;

	private ColumnDataModel columnDataModel;

	private ReviewModel reviewModel;

	public ViewLabelProvider(ColumnDataModelManager columnDataModelManager,
			ReviewModel reviewModel) {
		super();
		this.columnDataModelManager = columnDataModelManager;
		this.columnDataModel = columnDataModelManager.getModel();
		this.reviewModel = reviewModel;
	}

	/**
	 * Returns the label text for the given column of the given element.
	 * 
	 * @param object
	 *            The object instance to cast the ReviewIssue instance type
	 *            defined in <code>ReviewIssueModel</code> instance.
	 * @param columnIndex
	 *            the zero-based index of the column in which the label appears.
	 * 
	 * @return The column text string. No null value.
	 */
	public String getColumnText(Object object, int columnIndex) {
		ReviewIssue reviewIssue = (ReviewIssue) object;

		IProject project = reviewModel.getProjectManager().getProject();
		ReviewId reviewId = reviewModel.getReviewIdManager().getReviewId();
		String phaseNameKey = reviewModel.getPhaseManager().getPhaseNameKey();
		columnDataModel = columnDataModelManager.getModel(phaseNameKey);
		String[] columnNameKeys = columnDataModel
				.getEnabledColumnNameKeyArray();
		// make sure that columnIndex is not the index out of bounds.
		if (columnNameKeys.length > columnIndex) {
			String columnNameKey = columnNameKeys[columnIndex];
			if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_CREATED_DATE)) {
				return DATE_FORMATTER.format(reviewIssue.getCreationDate());
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_MODIFIED_DATE)) {
				return DATE_FORMATTER.format(reviewIssue.getModificationDate());
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_REVIEWER)) {
				return reviewIssue.getReviewer();
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_ASSGINED_TO)) {
				return reviewIssue.getAssignedTo();
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_SUMMARY)) {
				return reviewIssue.getSummary();
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_DESCRIPTION)) {
				return reviewIssue.getDescription();
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_ANNOTATION)) {
				return reviewIssue.getAnnotation();
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_REVISION)) {
				return reviewIssue.getRevision();
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_TYPE)) {
				TypeKeyManager manager = TypeKeyManager.getInstance(project,
						reviewId);
				return manager
						.getLocalizedLabel(reviewIssue.getType().getKey());
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_SEVERITY)) {
				String severityKey = reviewIssue.getSeverity().getKey();
				KeyManager<Severity> manager = SeverityKeyManager.getInstance(
						project, reviewId);
				return manager.getLocalizedLabel(severityKey);
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_RESOLUTION)) {
				String resolutionKey = reviewIssue.getResolution().getKey();
				ResolutionKeyManager manager = ResolutionKeyManager
						.getInstance(project, reviewId);
				return manager.getLocalizedLabel(resolutionKey);
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_STATUS)) {
				KeyManager<Status> manager = StatusKeyManager.getInstance(
						project, reviewId);
				return manager.getLocalizedLabel(reviewIssue.getStatus()
						.getKey());
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_FILE)) {
				return reviewIssue.getTargetFile();
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_LINE)) {
				return reviewIssue.getLine();
			} else if (columnNameKey.equals(ResourceBundleKey.COLUMN_HEADER_ID)) {
				return reviewIssue.getIssueId();
			} else if (columnNameKey
					.equals(ResourceBundleKey.COLUMN_HEADER_LINK_ICON)) {
				return "";
			}
		}
		return "";
	}

	/**
	 * Returns the label image for the given column of the given element.
	 * 
	 * @param object
	 *            the object representing the entire row, or null indicating
	 *            that no input object is set in the viewer.
	 * @param columnIndex
	 *            the zero-based index of the column in which the label appears.
	 * 
	 * @return The label image for the given column of the given element.
	 */
	public Image getColumnImage(Object object, int columnIndex) {
		String phaseNameKey = reviewModel.getPhaseManager().getPhaseNameKey();
		ColumnDataModel columnDataModel = columnDataModelManager
				.getModel(phaseNameKey);
		String[] columnNameKeys = columnDataModel
				.getEnabledColumnNameKeyArray();
		if (columnNameKeys.length > columnIndex) {
			if (columnNameKeys[columnIndex]
					.equals(ResourceBundleKey.COLUMN_HEADER_LINK_ICON)) {
				return getImage(object);
			}
		}
		return null;
	}

	/**
	 * Gets the image from <code>object</code>. Return the linked icon image if
	 * <code>IFile</code> path to the target review file exist. Otherwise return
	 * null.
	 * 
	 * @param object
	 *            the element for which to provide the label image
	 * 
	 * @return The image used to label the element, or null if there is no image
	 *         for the given object.
	 */
	public Image getImage(Object object) {
		ReviewIssue codeReview = (ReviewIssue) object;
		IProject project = FileResource.getProject(codeReview.getReviewIFile());
		String targetFileString = codeReview.getTargetFile();
		IFile targetIFile = null;
		if (!targetFileString.equals("")) {
			targetIFile = project.getFile(targetFileString);
		}
		try {
			URL url = ReviewPluginImpl.getInstance().getInstallURL();
			url = new URL(url, "icons/link.gif");
			boolean isTargetIFileAvailable = (targetIFile != null) ? targetIFile
					.exists() : false;
			if (linkImage == null) {
				linkImage = ImageDescriptor.createFromURL(url).createImage();
			}
			// Sets link status for the sort by link image.
			codeReview.setLinked(isTargetIFileAvailable);
			return (isTargetIFileAvailable ? linkImage : null);
		} catch (MalformedURLException e) {
			log.debug(e.getMessage());
			return null;
		}
	}
}

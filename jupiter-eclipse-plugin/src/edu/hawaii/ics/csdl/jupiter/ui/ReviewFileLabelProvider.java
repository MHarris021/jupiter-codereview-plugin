package edu.hawaii.ics.csdl.jupiter.ui;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Label provider for providing labels for <code>File</code>s in a tree.
 * 
 * @author Michael Harris
 */
public class ReviewFileLabelProvider extends LabelProvider {
	private static final Image IMG_FOLDER = PlatformUI.getWorkbench()
			.getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);

	private static final Image IMG_FILE = PlatformUI.getWorkbench()
			.getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);

	/** {@inheritDoc} */
	@Override
	public Image getImage(Object element) {
		Image image = super.getImage(element);
		if (element instanceof File) {
			File file = (File) element;
			if (file.isDirectory()) {
				image = IMG_FOLDER;
			} else {
				image = IMG_FILE;
			}
		}

		return image;
	}

	/** {@inheritDoc} */
	@Override
	public String getText(Object element) {
		String text = super.getText(element);
		if (element instanceof File) {
			File file = (File) element;
			text = file.getName();
		}

		return text;
	}
}

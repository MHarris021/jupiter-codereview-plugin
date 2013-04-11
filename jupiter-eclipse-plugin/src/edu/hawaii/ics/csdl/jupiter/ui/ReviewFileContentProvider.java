package edu.hawaii.ics.csdl.jupiter.ui;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The content provider used to show <code>File</code>s in a tree.
 * 
 * @author Michael Harris
 * 
 */
public class ReviewFileContentProvider implements ITreeContentProvider {

  /** {@inheritDoc} */

  public Object[] getChildren(Object parentElement) {
	  Object[] objects =null;
    if (parentElement instanceof File) {
      File file = (File) parentElement;
      objects = file.listFiles();
    }
    
    return objects;
  }

  /** {@inheritDoc} */
  public Object getParent(Object element) {
	  Object object = null;
    if (element instanceof File) {
      File file = (File) element;
      object = file.getParentFile();
    }
    
    return object;
  }

  /** {@inheritDoc} */
  public boolean hasChildren(Object element) {
	  boolean result = false;
    if (element instanceof File) {
      File file = (File) element;
      result = file.isDirectory() && (file.list().length > 0);
    }

    return result;
  }

  /** {@inheritDoc} */
  public Object[] getElements(Object inputElement) {
	  Object[] objects = null;
    if (inputElement instanceof File) {
      objects = getChildren(inputElement);
    }

    return objects;
  }

  /** {@inheritDoc} */
  public void dispose() {
    // do nothing
  }

  /** {@inheritDoc} */
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    // do nothing
  }

}

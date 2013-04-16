package edu.hawaii.ics.csdl.jupiter.aspects;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.osgi.framework.Bundle;

public class StubLog implements ILog {

	public void addLogListener(ILogListener arg0) {
	}

	public Bundle getBundle() {
		return null;
	}

	public void log(IStatus arg0) {
		System.err.println("Log: " + arg0.getMessage());

	}

	public void removeLogListener(ILogListener arg0) {
	}

}

package edu.hawaii.ics.csdl.jupiter.configuration.factorybeans;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceFactoryBean implements FactoryBean<IWorkspace> {

	public IWorkspace getObject() throws Exception {

		return ResourcesPlugin.getWorkspace();
	}

	public Class<?> getObjectType() {
		return IWorkspace.class;
	}

	public boolean isSingleton() {
		return true;
	}

}

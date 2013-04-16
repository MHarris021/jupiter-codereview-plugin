package edu.hawaii.ics.csdl.jupiter.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class JupiterExceptionLoggingAspect {

	@Autowired
	private ILog LOG;

	private final String pluginId = "edu.hawaii.ics.csdl.jupiter";

	private final Logger LOGGER = LoggerFactory.getLogger(pluginId);

	@AfterThrowing(pointcut = "execution(* edu.hawaii.ics.csdl.jupiter.file..*.*(..))", throwing = "e")
	public void logException(Exception e) {
		String message = e.getLocalizedMessage();
		LOGGER.error(message, e);
		IStatus status = new Status(IStatus.ERROR, pluginId, message, e);
		LOG.log(status);
	}
}

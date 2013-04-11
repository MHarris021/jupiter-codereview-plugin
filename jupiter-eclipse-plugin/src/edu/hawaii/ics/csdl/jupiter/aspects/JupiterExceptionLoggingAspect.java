package edu.hawaii.ics.csdl.jupiter.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import edu.hawaii.ics.csdl.jupiter.util.JupiterLogger;

@Component
@Aspect
public class JupiterExceptionLoggingAspect {

	@AfterThrowing(pointcut = "execution(* edu.hawaii.ics.csdl.jupiter.**.*(..))", throwing = "e")
	public void logException(Exception e) {
		JupiterLogger logger = JupiterLogger.getLogger();
		String message = e.getLocalizedMessage();
		logger.error(message, e);
	}
}

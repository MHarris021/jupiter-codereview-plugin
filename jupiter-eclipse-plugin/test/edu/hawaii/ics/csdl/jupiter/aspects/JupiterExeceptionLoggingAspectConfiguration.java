package edu.hawaii.ics.csdl.jupiter.aspects;

import org.eclipse.core.runtime.ILog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JupiterExeceptionLoggingAspectConfiguration {

	@Bean
	public ILog iLog() {
		ILog iLog = new StubLog();

		return iLog;
	}

}

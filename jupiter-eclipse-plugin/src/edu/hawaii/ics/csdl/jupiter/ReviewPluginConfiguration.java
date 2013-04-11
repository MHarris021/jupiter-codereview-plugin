package edu.hawaii.ics.csdl.jupiter;

import org.eclipse.core.runtime.ListenerList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "edu.hawaii.ics.csdl.jupiter.configuration")
public class ReviewPluginConfiguration {

	public ReviewPluginConfiguration() {
	}
	
	@Bean
	public ListenerList listenerList() {
		return new ListenerList();
	}
}

package edu.hawaii.ics.csdl.jupiter.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages="edu.hawaii.ics.csdl.jupiter.aspects")
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AspectConfiguration {

}

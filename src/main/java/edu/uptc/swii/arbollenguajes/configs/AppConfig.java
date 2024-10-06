package edu.uptc.swii.arbollenguajes.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @implNote This class is used to configure the spring context for dependency injection
 */
@Configuration(proxyBeanMethods=false)
@ComponentScan(basePackages = "edu.uptc.swii.arbollenguajes")
public class AppConfig {
}

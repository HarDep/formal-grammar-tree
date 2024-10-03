package edu.uptc.swii.arbollenguajes.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
@ComponentScan(basePackages = "edu.uptc.swii.arbollenguajes")
public class AppConfig {
}

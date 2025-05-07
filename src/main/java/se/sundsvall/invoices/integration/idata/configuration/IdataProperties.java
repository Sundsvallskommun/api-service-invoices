package se.sundsvall.invoices.integration.idata.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("integration.idata")
public record IdataProperties(
	String apiKey,
	String secretKey,
	String customerNumber,
	int connectTimeout,
	int readTimeout) {
}

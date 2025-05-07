package se.sundsvall.invoices.integration.idata.configuration;

import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignMultiCustomizer;
import se.sundsvall.dept44.configuration.feign.decoder.ProblemErrorDecoder;

@Import(FeignConfiguration.class)
public class IdataConfiguration {

	public static final String CLIENT_ID = "idata";

	@Bean
	FeignBuilderCustomizer feignBuilderCustomizer(final IdataProperties properties) {
		return FeignMultiCustomizer.create()
			.withErrorDecoder(new ProblemErrorDecoder(CLIENT_ID))
			.withRequestTimeoutsInSeconds(properties.connectTimeout(), properties.readTimeout())
			.withRequestInterceptor(requestTemplate -> new AuthorizationInterceptor(properties.apiKey(), properties.secretKey()).apply(requestTemplate))
			.composeCustomizersToOne();
	}

}

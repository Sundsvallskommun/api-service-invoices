package se.sundsvall.invoices.integration.idata.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import se.sundsvall.invoices.Application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = Application.class)
@ActiveProfiles("junit")
class IdataPropertiesTest {

	@Autowired
	private IdataProperties properties;

	@Test
	void testProperties() {
		assertThat(properties.connectTimeout()).isEqualTo(10);
		assertThat(properties.readTimeout()).isEqualTo(20);
		assertThat(properties.apiKey()).isEqualTo("apiKey");
		assertThat(properties.secretKey()).isEqualTo("secretKey");
		assertThat(properties.customerNumber()).isEqualTo("customerNumber");
	}
}

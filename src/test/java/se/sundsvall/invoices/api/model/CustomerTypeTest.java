package se.sundsvall.invoices.api.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.invoices.api.model.CustomerType.ENTERPRISE;
import static se.sundsvall.invoices.api.model.CustomerType.PRIVATE;

class CustomerTypeTest {

	@Test
	void values() {
		assertThat(CustomerType.values()).containsExactly(ENTERPRISE, PRIVATE);
	}
}

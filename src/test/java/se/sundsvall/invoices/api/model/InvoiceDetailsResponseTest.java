package se.sundsvall.invoices.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import java.util.List;
import org.junit.jupiter.api.Test;

class InvoiceDetailsResponseTest {

	@Test
	void testBean() {
		assertThat(InvoiceDetailsResponse.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var details = List.of(InvoiceDetail.create());
		final var invoiceDetailsResponse = InvoiceDetailsResponse.create().withDetails(details);

		assertThat(invoiceDetailsResponse)
			.isNotNull()
			.hasNoNullFieldsOrProperties()
			.hasFieldOrPropertyWithValue("details", details);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(InvoiceDetailsResponse.create()).hasAllNullFieldsOrProperties();
		assertThat(new InvoiceDetailsResponse()).hasAllNullFieldsOrProperties();
	}
}

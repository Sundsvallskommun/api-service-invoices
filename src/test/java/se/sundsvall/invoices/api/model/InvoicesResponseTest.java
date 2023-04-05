package se.sundsvall.invoices.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class InvoicesResponseTest {

	@Test
	void testBean() {
		assertThat(InvoicesResponse.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var invoices = new ArrayList<Invoice>();
		final var metaData = MetaData.create();

		final var invoicesResponse = InvoicesResponse.create().withInvoices(invoices).withMetaData(MetaData.create());

		assertThat(invoicesResponse).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(invoicesResponse.getInvoices()).isEqualTo(invoices);
		assertThat(invoicesResponse.getMetaData()).isEqualTo(metaData);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(InvoicesResponse.create()).hasAllNullFieldsOrProperties();
		assertThat(new InvoicesResponse()).hasAllNullFieldsOrProperties();
	}
}

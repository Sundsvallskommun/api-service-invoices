package se.sundsvall.invoices.api.model;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

class CustomerInvoicesResponseTest {

	@Test
	void testBean() {
		assertThat(CustomerInvoicesResponse.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var invoices = new ArrayList<CustomerInvoice>();
		final var metaData = MetaData.create();

		final var response = CustomerInvoicesResponse.create().withInvoices(invoices).withMetaData(metaData);

		assertThat(response).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(response.getInvoices()).isEqualTo(invoices);
		assertThat(response.getMetaData()).isEqualTo(metaData);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(CustomerInvoicesResponse.create()).hasAllNullFieldsOrProperties();
		assertThat(new CustomerInvoicesResponse()).hasAllNullFieldsOrProperties();
	}
}

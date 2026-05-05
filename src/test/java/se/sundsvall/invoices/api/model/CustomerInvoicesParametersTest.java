package se.sundsvall.invoices.api.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.registerValueGenerator;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

class CustomerInvoicesParametersTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), LocalDate.class);
	}

	@Test
	void testBean() {
		assertThat(CustomerInvoicesParameters.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var organizationIds = List.of("5565027223", "5564786647");
		final var periodFrom = LocalDate.now().minusMonths(6);
		final var periodTo = LocalDate.now();
		final var sortBy = "periodFrom";
		final var page = 3;
		final var limit = 50;

		final var parameters = CustomerInvoicesParameters.create()
			.withOrganizationIds(organizationIds)
			.withPeriodFrom(periodFrom)
			.withPeriodTo(periodTo)
			.withSortBy(sortBy)
			.withPage(page)
			.withLimit(limit);

		assertThat(parameters).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(parameters.getOrganizationIds()).isEqualTo(organizationIds);
		assertThat(parameters.getPeriodFrom()).isEqualTo(periodFrom);
		assertThat(parameters.getPeriodTo()).isEqualTo(periodTo);
		assertThat(parameters.getSortBy()).isEqualTo(sortBy);
		assertThat(parameters.getPage()).isEqualTo(page);
		assertThat(parameters.getLimit()).isEqualTo(limit);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(CustomerInvoicesParameters.create())
			.hasAllNullFieldsOrPropertiesExcept("page", "limit")
			.hasFieldOrPropertyWithValue("page", 1)
			.hasFieldOrPropertyWithValue("limit", 100);

		assertThat(new CustomerInvoicesParameters())
			.hasAllNullFieldsOrPropertiesExcept("page", "limit")
			.hasFieldOrPropertyWithValue("page", 1)
			.hasFieldOrPropertyWithValue("limit", 100);
	}
}

package se.sundsvall.invoices.integration.datawarehousereader;

import generated.se.sundsvall.datawarehousereader.CustomerType;
import generated.se.sundsvall.datawarehousereader.Direction;
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

class InvoicesQueryParametersTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(new Random().nextInt()), LocalDate.class);
	}

	@Test
	void testBean() {
		assertThat(InvoicesQueryParameters.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var customerNumber = List.of("123");
		final var customerType = CustomerType.ENTERPRISE;
		final var facilityIds = List.of("facility");
		final var invoiceNumber = 999L;
		final var invoiceDateFrom = LocalDate.now().minusDays(30);
		final var invoiceDateTo = LocalDate.now();
		final var invoiceName = "name";
		final var invoiceType = "Faktura";
		final var invoiceStatus = "Skickad";
		final var ocrNumber = 100L;
		final var dueDateFrom = LocalDate.now();
		final var dueDateTo = LocalDate.now().plusDays(30);
		final var organizationGroup = "stadsbacken";
		final var organizationNumbers = List.of("5565027223");
		final var administration = "Sundsvall Elnät";
		final var sortBy = List.of("invoiceDate");
		final var sortDirection = Direction.DESC;
		final var page = 2;
		final var limit = 50;

		final var query = InvoicesQueryParameters.create()
			.withCustomerNumber(customerNumber)
			.withCustomerType(customerType)
			.withFacilityIds(facilityIds)
			.withInvoiceNumber(invoiceNumber)
			.withInvoiceDateFrom(invoiceDateFrom)
			.withInvoiceDateTo(invoiceDateTo)
			.withInvoiceName(invoiceName)
			.withInvoiceType(invoiceType)
			.withInvoiceStatus(invoiceStatus)
			.withOcrNumber(ocrNumber)
			.withDueDateFrom(dueDateFrom)
			.withDueDateTo(dueDateTo)
			.withOrganizationGroup(organizationGroup)
			.withOrganizationNumbers(organizationNumbers)
			.withAdministration(administration)
			.withSortBy(sortBy)
			.withSortDirection(sortDirection)
			.withPage(page)
			.withLimit(limit);

		assertThat(query).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(query.getCustomerNumber()).isEqualTo(customerNumber);
		assertThat(query.getCustomerType()).isEqualTo(customerType);
		assertThat(query.getFacilityIds()).isEqualTo(facilityIds);
		assertThat(query.getInvoiceNumber()).isEqualTo(invoiceNumber);
		assertThat(query.getInvoiceDateFrom()).isEqualTo(invoiceDateFrom);
		assertThat(query.getInvoiceDateTo()).isEqualTo(invoiceDateTo);
		assertThat(query.getInvoiceName()).isEqualTo(invoiceName);
		assertThat(query.getInvoiceType()).isEqualTo(invoiceType);
		assertThat(query.getInvoiceStatus()).isEqualTo(invoiceStatus);
		assertThat(query.getOcrNumber()).isEqualTo(ocrNumber);
		assertThat(query.getDueDateFrom()).isEqualTo(dueDateFrom);
		assertThat(query.getDueDateTo()).isEqualTo(dueDateTo);
		assertThat(query.getOrganizationGroup()).isEqualTo(organizationGroup);
		assertThat(query.getOrganizationNumbers()).isEqualTo(organizationNumbers);
		assertThat(query.getAdministration()).isEqualTo(administration);
		assertThat(query.getSortBy()).isEqualTo(sortBy);
		assertThat(query.getSortDirection()).isEqualTo(sortDirection);
		assertThat(query.getPage()).isEqualTo(page);
		assertThat(query.getLimit()).isEqualTo(limit);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(InvoicesQueryParameters.create()).hasAllNullFieldsOrProperties();
		assertThat(new InvoicesQueryParameters()).hasAllNullFieldsOrProperties();
	}
}

package se.sundsvall.invoices.api.model;

import com.google.code.beanmatchers.BeanMatchers;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

class InvoiceDetailTest {

	@BeforeAll
	static void setup() {
		BeanMatchers.registerValueGenerator(() -> LocalDate.parse("2024-01-01").plusDays(new Random().nextInt()), LocalDate.class);
		BeanMatchers.registerValueGenerator(() -> BigDecimal.valueOf(new Random().nextDouble()), BigDecimal.class);
	}

	@Test
	void testBean() {
		assertThat(InvoiceDetail.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var amount = BigDecimal.valueOf(100);
		final var amountVatExcluded = BigDecimal.valueOf(80);
		final var vat = BigDecimal.valueOf(20);
		final var vatRate = BigDecimal.valueOf(25);
		final var quantity = BigDecimal.valueOf(10);
		final var unit = "unit";
		final var unitPrice = BigDecimal.valueOf(10);
		final var description = "description";
		final var productCode = "productCode";
		final var productName = "productName";
		final var fromDate = LocalDate.parse("2024-01-01").minusDays(10);
		final var toDate = LocalDate.parse("2024-01-01").plusDays(10);
		final var facilityId = "facilityId";
		final var administration = "administration";

		final var invoiceDetail = InvoiceDetail.create()
			.withAmount(amount)
			.withAmountVatExcluded(amountVatExcluded)
			.withVat(vat)
			.withVatRate(vatRate)
			.withQuantity(quantity)
			.withUnit(unit)
			.withUnitPrice(unitPrice)
			.withDescription(description)
			.withProductCode(productCode)
			.withProductName(productName)
			.withFromDate(fromDate)
			.withToDate(toDate)
			.withFacilityId(facilityId)
			.withAdministration(administration);

		assertThat(invoiceDetail).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(invoiceDetail.getAmount()).isEqualTo(amount);
		assertThat(invoiceDetail.getAmountVatExcluded()).isEqualTo(amountVatExcluded);
		assertThat(invoiceDetail.getVat()).isEqualTo(vat);
		assertThat(invoiceDetail.getVatRate()).isEqualTo(vatRate);
		assertThat(invoiceDetail.getQuantity()).isEqualTo(quantity);
		assertThat(invoiceDetail.getUnit()).isEqualTo(unit);
		assertThat(invoiceDetail.getUnitPrice()).isEqualTo(unitPrice);
		assertThat(invoiceDetail.getDescription()).isEqualTo(description);
		assertThat(invoiceDetail.getProductCode()).isEqualTo(productCode);
		assertThat(invoiceDetail.getProductName()).isEqualTo(productName);
		assertThat(invoiceDetail.getFromDate()).isEqualTo(fromDate);
		assertThat(invoiceDetail.getToDate()).isEqualTo(toDate);
		assertThat(invoiceDetail.getFacilityId()).isEqualTo(facilityId);
		assertThat(invoiceDetail.getAdministration()).isEqualTo(administration);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(InvoiceDetail.create()).hasAllNullFieldsOrProperties();
	}
}

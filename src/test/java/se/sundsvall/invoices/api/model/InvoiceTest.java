package se.sundsvall.invoices.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import com.google.code.beanmatchers.BeanMatchers;
import java.time.LocalDate;
import java.util.Random;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InvoiceTest {

	@BeforeAll
	static void setup() {
		BeanMatchers.registerValueGenerator(() -> LocalDate.now().plusDays(new Random().nextInt()), LocalDate.class);
	}

	@Test
	void testBean() {
		assertThat(Invoice.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var dueDate = LocalDate.now();
		final var totalAmount = 100;
		final var amountVatExcluded = 80;
		final var amountVatIncluded = 100;
		final var vatEligibleAmount = 10;
		final var rounding = 0.5f;
		final var vat = 20;
		final var reverseVat = true;
		final var pdfAvailable = true;
		final var currency = "currency";
		final var invoiceDate = LocalDate.now();
		final var fromDate = LocalDate.now().minusDays(10);
		final var toDate = LocalDate.now().plusDays(10);
		final var invoiceNumber = "invoiceNumber";
		final var invoiceStatus = InvoiceStatus.PAID;
		final var ocrNumber = "ocrNumber";
		final var invoiceName = "invoiceName";
		final var invoiceType = InvoiceType.INVOICE;
		final var invoiceDescription = "invoiceDescription";
		final var invoiceAddress = Address.create();
		final var facilityId = "facilityId";
		final var organizationNumber = "organizationNumber";
		final var invoiceOrigin = InvoiceOrigin.PUBLIC_ADMINISTRATION;

		final var invoice = Invoice.create()
			.withDueDate(dueDate)
			.withTotalAmount(totalAmount)
			.withVat(vat)
			.withAmountVatIncluded(amountVatIncluded)
			.withAmountVatExcluded(amountVatExcluded)
			.withVatEligibleAmount(vatEligibleAmount)
			.withRounding(rounding)
			.withReversedVat(reverseVat)
			.withPdfAvailable(pdfAvailable)
			.withCurrency(currency)
			.withInvoiceDate(invoiceDate)
			.withFromDate(fromDate)
			.withToDate(toDate)
			.withInvoiceNumber(invoiceNumber)
			.withInvoiceStatus(invoiceStatus)
			.withOcrNumber(ocrNumber)
			.withInvoiceName(invoiceName)
			.withInvoiceType(invoiceType)
			.withInvoiceDescription(invoiceDescription)
			.withInvoiceAddress(invoiceAddress)
			.withFacilityId(facilityId)
			.withOrganizationNumber(organizationNumber)
			.withInvoiceOrigin(invoiceOrigin);

		assertThat(invoice).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(invoice.getDueDate()).isEqualTo(dueDate);
		assertThat(invoice.getTotalAmount()).isEqualTo(totalAmount);
		assertThat(invoice.getVat()).isEqualTo(vat);
		assertThat(invoice.getAmountVatIncluded()).isEqualTo(amountVatIncluded);
		assertThat(invoice.getAmountVatExcluded()).isEqualTo(amountVatExcluded);
		assertThat(invoice.getVatEligibleAmount()).isEqualTo(vatEligibleAmount);
		assertThat(invoice.getRounding()).isEqualTo(rounding);
		assertThat(invoice.getReversedVat()).isEqualTo(reverseVat);
		assertThat(invoice.getPdfAvailable()).isEqualTo(pdfAvailable);
		assertThat(invoice.getCurrency()).isEqualTo(currency);
		assertThat(invoice.getInvoiceDate()).isEqualTo(invoiceDate);
		assertThat(invoice.getFromDate()).isEqualTo(fromDate);
		assertThat(invoice.getToDate()).isEqualTo(toDate);
		assertThat(invoice.getInvoiceNumber()).isEqualTo(invoiceNumber);
		assertThat(invoice.getInvoiceStatus()).isEqualTo(invoiceStatus);
		assertThat(invoice.getOcrNumber()).isEqualTo(ocrNumber);
		assertThat(invoice.getInvoiceName()).isEqualTo(invoiceName);
		assertThat(invoice.getInvoiceType()).isEqualTo(invoiceType);
		assertThat(invoice.getInvoiceDescription()).isEqualTo(invoiceDescription);
		assertThat(invoice.getInvoiceAddress()).isEqualTo(invoiceAddress);
		assertThat(invoice.getFacilityId()).isEqualTo(facilityId);
		assertThat(invoice.getOrganizationNumber()).isEqualTo(organizationNumber);
		assertThat(invoice.getInvoiceOrigin()).isEqualTo(invoiceOrigin);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(Invoice.create())
			.hasAllNullFieldsOrPropertiesExcept("totalAmount", "amountVatIncluded", "amountVatExcluded", "vatEligibleAmount", "rounding", "reversedVat", "pdfAvailable", "vat")
			.hasFieldOrPropertyWithValue("totalAmount", 0f).hasFieldOrPropertyWithValue("amountVatIncluded", 0f)
			.hasFieldOrPropertyWithValue("amountVatExcluded", 0f)
			.hasFieldOrPropertyWithValue("vatEligibleAmount", 0f).hasFieldOrPropertyWithValue("vat", 0f);
	}
}

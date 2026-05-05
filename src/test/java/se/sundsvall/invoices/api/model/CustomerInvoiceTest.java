package se.sundsvall.invoices.api.model;

import com.google.code.beanmatchers.BeanMatchers;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

class CustomerInvoiceTest {

	@BeforeAll
	static void setup() {
		BeanMatchers.registerValueGenerator(() -> LocalDate.now().plusDays(new Random().nextInt()), LocalDate.class);
	}

	@Test
	void testBean() {
		assertThat(CustomerInvoice.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var customerNumber = "123456";
		final var customerType = CustomerType.ENTERPRISE;
		final var facilityId = "facilityId";
		final var invoiceNumber = "999";
		final var invoiceId = 1062916396L;
		final var jointInvoiceId = 123L;
		final var invoiceDate = LocalDate.now().minusDays(10);
		final var invoiceName = "invoiceName";
		final var invoiceType = InvoiceType.INVOICE;
		final var invoiceDescription = "El";
		final var invoiceStatus = InvoiceStatus.PAID;
		final var ocrNumber = "295334999";
		final var dueDate = LocalDate.now().plusDays(20);
		final var periodFrom = LocalDate.now().minusMonths(2);
		final var periodTo = LocalDate.now().minusMonths(1);
		final var totalAmount = 1234.0f;
		final var amountVatIncluded = 1233.51f;
		final var amountVatExcluded = 986.81f;
		final var vatEligibleAmount = 986.81f;
		final var rounding = 0.49f;
		final var organizationGroup = "stadsbacken";
		final var organizationNumber = "5565027223";
		final var administration = "Sundsvall Elnät";
		final var street = "Ankeborgsvägen 11";
		final var postCode = "87654";
		final var city = "Sundsvall";
		final var careOf = "Anka Kalle";
		final var invoiceReference = "ref-1";
		final var pdfAvailable = true;
		final var details = List.of(InvoiceDetail.create());

		final var customerInvoice = CustomerInvoice.create()
			.withCustomerNumber(customerNumber)
			.withCustomerType(customerType)
			.withFacilityId(facilityId)
			.withInvoiceNumber(invoiceNumber)
			.withInvoiceId(invoiceId)
			.withJointInvoiceId(jointInvoiceId)
			.withInvoiceDate(invoiceDate)
			.withInvoiceName(invoiceName)
			.withInvoiceType(invoiceType)
			.withInvoiceDescription(invoiceDescription)
			.withInvoiceStatus(invoiceStatus)
			.withOcrNumber(ocrNumber)
			.withDueDate(dueDate)
			.withPeriodFrom(periodFrom)
			.withPeriodTo(periodTo)
			.withTotalAmount(totalAmount)
			.withAmountVatIncluded(amountVatIncluded)
			.withAmountVatExcluded(amountVatExcluded)
			.withVatEligibleAmount(vatEligibleAmount)
			.withRounding(rounding)
			.withOrganizationGroup(organizationGroup)
			.withOrganizationNumber(organizationNumber)
			.withAdministration(administration)
			.withStreet(street)
			.withPostCode(postCode)
			.withCity(city)
			.withCareOf(careOf)
			.withInvoiceReference(invoiceReference)
			.withPdfAvailable(pdfAvailable)
			.withDetails(details);

		assertThat(customerInvoice).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(customerInvoice.getCustomerNumber()).isEqualTo(customerNumber);
		assertThat(customerInvoice.getCustomerType()).isEqualTo(customerType);
		assertThat(customerInvoice.getFacilityId()).isEqualTo(facilityId);
		assertThat(customerInvoice.getInvoiceNumber()).isEqualTo(invoiceNumber);
		assertThat(customerInvoice.getInvoiceId()).isEqualTo(invoiceId);
		assertThat(customerInvoice.getJointInvoiceId()).isEqualTo(jointInvoiceId);
		assertThat(customerInvoice.getInvoiceDate()).isEqualTo(invoiceDate);
		assertThat(customerInvoice.getInvoiceName()).isEqualTo(invoiceName);
		assertThat(customerInvoice.getInvoiceType()).isEqualTo(invoiceType);
		assertThat(customerInvoice.getInvoiceDescription()).isEqualTo(invoiceDescription);
		assertThat(customerInvoice.getInvoiceStatus()).isEqualTo(invoiceStatus);
		assertThat(customerInvoice.getOcrNumber()).isEqualTo(ocrNumber);
		assertThat(customerInvoice.getDueDate()).isEqualTo(dueDate);
		assertThat(customerInvoice.getPeriodFrom()).isEqualTo(periodFrom);
		assertThat(customerInvoice.getPeriodTo()).isEqualTo(periodTo);
		assertThat(customerInvoice.getTotalAmount()).isEqualTo(totalAmount);
		assertThat(customerInvoice.getAmountVatIncluded()).isEqualTo(amountVatIncluded);
		assertThat(customerInvoice.getAmountVatExcluded()).isEqualTo(amountVatExcluded);
		assertThat(customerInvoice.getVatEligibleAmount()).isEqualTo(vatEligibleAmount);
		assertThat(customerInvoice.getRounding()).isEqualTo(rounding);
		assertThat(customerInvoice.getOrganizationGroup()).isEqualTo(organizationGroup);
		assertThat(customerInvoice.getOrganizationNumber()).isEqualTo(organizationNumber);
		assertThat(customerInvoice.getAdministration()).isEqualTo(administration);
		assertThat(customerInvoice.getStreet()).isEqualTo(street);
		assertThat(customerInvoice.getPostCode()).isEqualTo(postCode);
		assertThat(customerInvoice.getCity()).isEqualTo(city);
		assertThat(customerInvoice.getCareOf()).isEqualTo(careOf);
		assertThat(customerInvoice.getInvoiceReference()).isEqualTo(invoiceReference);
		assertThat(customerInvoice.getPdfAvailable()).isEqualTo(pdfAvailable);
		assertThat(customerInvoice.getDetails()).isEqualTo(details);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(CustomerInvoice.create())
			.hasAllNullFieldsOrPropertiesExcept("totalAmount", "amountVatIncluded", "amountVatExcluded", "vatEligibleAmount", "rounding")
			.hasFieldOrPropertyWithValue("totalAmount", 0f)
			.hasFieldOrPropertyWithValue("amountVatIncluded", 0f)
			.hasFieldOrPropertyWithValue("amountVatExcluded", 0f)
			.hasFieldOrPropertyWithValue("vatEligibleAmount", 0f)
			.hasFieldOrPropertyWithValue("rounding", 0f);
	}
}

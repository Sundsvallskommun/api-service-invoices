package se.sundsvall.invoices.api.model;

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

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InvoicesParametersTest {
    @BeforeAll
    static void setup() {
        registerValueGenerator(() -> now().plusDays(new Random().nextInt()), LocalDate.class);
    }

    @Test
    void testBean() {
        assertThat(InvoicesParameters.class, allOf(
                hasValidBeanConstructor(),
                hasValidGettersAndSetters(),
                hasValidBeanHashCode(),
                hasValidBeanEquals(),
                hasValidBeanToString()));
    }

    @Test
    void testBuilderMethods() {
        final var dueDateFrom = LocalDate.now().minusDays(1);
        final var dueDateTo = LocalDate.now().plusDays(30);
        final var facilityId = List.of("facilityId-1", "facilityId-2");
        final var invoiceNumber = "invoiceNumber";
        final var invoiceName = "invoiceName";
        final var invoiceType = InvoiceType.CREDIT;
        final var invoiceStatus = InvoiceStatus.DEBT_COLLECTION;
        final var invoiceDateFrom = LocalDate.now().minusDays(30);
        final var invoiceDateTo = LocalDate.now();
        final var limit = 123;
        final var organizationGroup = "organizationGroup";
        final var organizationNumber = "organizationNumber";
        final var ocrNumber = "ocrNumber";
        final var page = 321;
        final var partyId = List.of("partyId-1", "partyId-2");

        final var invoicesParameters = InvoicesParameters.create()
            .withDueDateFrom(dueDateFrom)
            .withDueDateTo(dueDateTo)
            .withFacilityId(facilityId)
            .withInvoiceNumber(invoiceNumber)
            .withInvoiceName(invoiceName)
            .withInvoiceType(invoiceType)
            .withInvoiceStatus(invoiceStatus)
            .withInvoiceDateFrom(invoiceDateFrom)
            .withInvoiceDateTo(invoiceDateTo)
            .withLimit(limit)
            .withOrganizationGroup(organizationGroup)
            .withOrganizationNumber(organizationNumber)
            .withOcrNumber(ocrNumber)
            .withPage(page)
            .withPartyId(partyId);
        
        assertThat(invoicesParameters).isNotNull().hasNoNullFieldsOrProperties();
        assertThat(invoicesParameters.getDueDateFrom()).isEqualTo(dueDateFrom);
        assertThat(invoicesParameters.getDueDateTo()).isEqualTo(dueDateTo);
        assertThat(invoicesParameters.getFacilityId()).isEqualTo(facilityId);
        assertThat(invoicesParameters.getInvoiceNumber()).isEqualTo(invoiceNumber);
        assertThat(invoicesParameters.getInvoiceName()).isEqualTo(invoiceName);
        assertThat(invoicesParameters.getInvoiceType()).isEqualTo(invoiceType);
        assertThat(invoicesParameters.getInvoiceStatus()).isEqualTo(invoiceStatus);
        assertThat(invoicesParameters.getInvoiceDateFrom()).isEqualTo(invoiceDateFrom);
        assertThat(invoicesParameters.getInvoiceDateTo()).isEqualTo(invoiceDateTo);
        assertThat(invoicesParameters.getLimit()).isEqualTo(limit);
        assertThat(invoicesParameters.getOrganizationGroup()).isEqualTo(organizationGroup);
        assertThat(invoicesParameters.getOrganizationNumber()).isEqualTo(organizationNumber);
        assertThat(invoicesParameters.getOcrNumber()).isEqualTo(ocrNumber);
        assertThat(invoicesParameters.getPage()).isEqualTo(page);
        assertThat(invoicesParameters.getPartyId()).isEqualTo(partyId);
    }

    @Test
    void testNoDirtOnCreatedBean() {
        assertThat(InvoicesParameters.create())
            .hasAllNullFieldsOrPropertiesExcept("page", "limit")
            .hasFieldOrPropertyWithValue("page", 1)
            .hasFieldOrPropertyWithValue("limit", 100);

        assertThat(new InvoicesParameters())
            .hasAllNullFieldsOrPropertiesExcept("page", "limit")
            .hasFieldOrPropertyWithValue("page", 1)
            .hasFieldOrPropertyWithValue("limit", 100);
    }
}

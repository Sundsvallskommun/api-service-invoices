package se.sundsvall.invoices.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.invoices.api.model.InvoiceType.CONSOLIDATED_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.CREDIT_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.DIRECT_DEBIT;
import static se.sundsvall.invoices.api.model.InvoiceType.FINAL_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.INTERNAL_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.OFFSET_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.REMINDER;
import static se.sundsvall.invoices.api.model.InvoiceType.SELF_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.START_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.UNKNOWN;

import org.junit.jupiter.api.Test;

class InvoiceTypeTest {

	@Test
	void values() {
		assertThat(InvoiceType.values()).containsExactly(
			INVOICE,
			CREDIT_INVOICE,
			START_INVOICE,
			FINAL_INVOICE,
			DIRECT_DEBIT,
			SELF_INVOICE,
			REMINDER,
			CONSOLIDATED_INVOICE,
			INTERNAL_INVOICE,
			OFFSET_INVOICE,
			UNKNOWN);
	}
}

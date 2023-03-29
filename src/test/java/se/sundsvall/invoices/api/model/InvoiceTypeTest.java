package se.sundsvall.invoices.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.invoices.api.model.InvoiceType.CREDIT_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.START_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.FINAL_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.UNKNOWN;

import org.junit.jupiter.api.Test;

class InvoiceTypeTest {

	@Test
	void values() {
		assertThat(InvoiceType.values()).containsExactly(INVOICE, CREDIT_INVOICE, START_INVOICE, FINAL_INVOICE, UNKNOWN);
	}
}

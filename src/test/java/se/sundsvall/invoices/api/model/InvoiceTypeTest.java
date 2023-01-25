package se.sundsvall.invoices.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.invoices.api.model.InvoiceType.CREDIT;
import static se.sundsvall.invoices.api.model.InvoiceType.NORMAL;
import static se.sundsvall.invoices.api.model.InvoiceType.START;
import static se.sundsvall.invoices.api.model.InvoiceType.STOP;
import static se.sundsvall.invoices.api.model.InvoiceType.UNKNOWN;

import org.junit.jupiter.api.Test;

class InvoiceTypeTest {

	@Test
	void values() {
		assertThat(InvoiceType.values()).containsExactly(NORMAL, CREDIT, START, STOP, UNKNOWN);
	}
}

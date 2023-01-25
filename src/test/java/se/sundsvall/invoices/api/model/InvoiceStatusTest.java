package se.sundsvall.invoices.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.invoices.api.model.InvoiceStatus.CREDITED;
import static se.sundsvall.invoices.api.model.InvoiceStatus.DEBT_COLLECTION;
import static se.sundsvall.invoices.api.model.InvoiceStatus.PAID;
import static se.sundsvall.invoices.api.model.InvoiceStatus.PAID_TOO_MUCH;
import static se.sundsvall.invoices.api.model.InvoiceStatus.PARTIALLY_PAID;
import static se.sundsvall.invoices.api.model.InvoiceStatus.REMINDER;
import static se.sundsvall.invoices.api.model.InvoiceStatus.SENT;
import static se.sundsvall.invoices.api.model.InvoiceStatus.UNKNOWN;
import static se.sundsvall.invoices.api.model.InvoiceStatus.WRITTEN_OFF;

import org.junit.jupiter.api.Test;

class InvoiceStatusTest {

	@Test
	void values() {
		assertThat(InvoiceStatus.values()).containsExactly(
			PAID, PARTIALLY_PAID, PAID_TOO_MUCH, CREDITED, DEBT_COLLECTION, REMINDER, WRITTEN_OFF, SENT, UNKNOWN);
	}
}

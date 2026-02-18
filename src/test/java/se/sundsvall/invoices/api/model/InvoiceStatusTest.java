package se.sundsvall.invoices.api.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static se.sundsvall.invoices.api.model.InvoiceStatus.CREDITED;
import static se.sundsvall.invoices.api.model.InvoiceStatus.DEBT_COLLECTION;
import static se.sundsvall.invoices.api.model.InvoiceStatus.PAID;
import static se.sundsvall.invoices.api.model.InvoiceStatus.PAID_TOO_MUCH;
import static se.sundsvall.invoices.api.model.InvoiceStatus.PARTIALLY_PAID;
import static se.sundsvall.invoices.api.model.InvoiceStatus.REMINDER;
import static se.sundsvall.invoices.api.model.InvoiceStatus.SENT;
import static se.sundsvall.invoices.api.model.InvoiceStatus.UNKNOWN;
import static se.sundsvall.invoices.api.model.InvoiceStatus.VOID;
import static se.sundsvall.invoices.api.model.InvoiceStatus.WRITTEN_OFF;

class InvoiceStatusTest {

	@Test
	void values() {
		assertThat(InvoiceStatus.values()).containsExactlyInAnyOrder(
			PAID,
			SENT,
			PARTIALLY_PAID,
			DEBT_COLLECTION,
			PAID_TOO_MUCH,
			REMINDER,
			VOID,
			CREDITED,
			WRITTEN_OFF,
			UNKNOWN);
	}
}

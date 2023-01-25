package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = "PAID", description = "Status of invoice", enumAsRef = true)
public enum InvoiceStatus {
	PAID,
	PARTIALLY_PAID,
	PAID_TOO_MUCH,
	CREDITED,
	DEBT_COLLECTION,
	REMINDER,
	WRITTEN_OFF,
	SENT,
	UNKNOWN;
}

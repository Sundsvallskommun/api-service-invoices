package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = "PAID", description = "Status of invoice", enumAsRef = true)
public enum InvoiceStatus {
	PAID,
	SENT,
	PARTIALLY_PAID,
	DEBT_COLLECTION,
	PAID_TOO_MUCH,
	REMINDER,
	VOID,
	CREDITED,
	WRITTEN_OFF,
	UNKNOWN;
}

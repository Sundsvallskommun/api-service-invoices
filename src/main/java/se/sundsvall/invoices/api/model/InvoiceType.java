package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(examples = "INVOICE", description = "Type of invoice", enumAsRef = true)
public enum InvoiceType {
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
	UNKNOWN
}

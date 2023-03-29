package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = "INVOICE", description = "Type of invoice", enumAsRef = true)
public enum InvoiceType {
	INVOICE,
	CREDIT_INVOICE,
	START_INVOICE,
	FINAL_INVOICE,
	UNKNOWN;
}

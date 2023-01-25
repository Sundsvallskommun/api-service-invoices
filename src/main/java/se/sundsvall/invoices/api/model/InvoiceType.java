package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = "NORMAL", description = "Type of invoice", enumAsRef = true)
public enum InvoiceType {
	NORMAL,
	CREDIT,
	START,
	STOP,
	UNKNOWN;
}

package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Invoice origin (invoices originates from either commercial or public activities)", enumAsRef = true, example = "COMMERCIAL")
public enum InvoiceOrigin {
	COMMERCIAL,
	PUBLIC_ADMINISTRATION
}

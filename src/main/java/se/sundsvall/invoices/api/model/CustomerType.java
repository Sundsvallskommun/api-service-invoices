package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(examples = "ENTERPRISE", description = "Customer type", enumAsRef = true)
public enum CustomerType {
	ENTERPRISE,
	PRIVATE
}

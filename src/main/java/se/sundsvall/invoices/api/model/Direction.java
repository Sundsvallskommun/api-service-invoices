package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(examples = "ASC", description = "The sort order direction", enumAsRef = true)
public enum Direction {
	ASC,
	DESC
}

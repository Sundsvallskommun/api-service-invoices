package se.sundsvall.invoices.api.model;

import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public class InvoiceDetailsResponse {

	@ArraySchema(schema = @Schema(implementation = InvoiceDetail.class))
	private List<InvoiceDetail> details;

	public static InvoiceDetailsResponse create() {
		return new InvoiceDetailsResponse();
	}

	public InvoiceDetailsResponse withDetails(final List<InvoiceDetail> details) {
		this.details = details;
		return this;
	}

	public List<InvoiceDetail> getDetails() {
		return details;
	}

	public void setDetails(final List<InvoiceDetail> details) {
		this.details = details;
	}

	@Override
	public int hashCode() {
		return Objects.hash(details);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof InvoiceDetailsResponse other)) {
			return false;
		}
		return Objects.equals(details, other.details);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("InvoiceDetailsResponse [details=").append(details).append("]");
		return builder.toString();
	}
}

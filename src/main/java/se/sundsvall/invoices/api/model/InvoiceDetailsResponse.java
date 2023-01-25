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

	public InvoiceDetailsResponse withDetails(List<InvoiceDetail> details) {
		this.details = details;
		return this;
	}

	public List<InvoiceDetail> getDetails() {
		return details;
	}

	public void setDetails(List<InvoiceDetail> details) {
		this.details = details;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		InvoiceDetailsResponse invoiceDetailsResponse = (InvoiceDetailsResponse) o;
		return Objects.equals(this.details, invoiceDetailsResponse.details);
	}

	@Override
	public int hashCode() {
		return Objects.hash(details);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceDetailsResponse [details=").append(details).append("]");
		return builder.toString();
	}
}

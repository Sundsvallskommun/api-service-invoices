package se.sundsvall.invoices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "CustomerInvoicesResponse model")
public class CustomerInvoicesResponse {

	@JsonProperty("_meta")
	@Schema(implementation = MetaData.class, accessMode = READ_ONLY)
	private MetaData metaData;

	@ArraySchema(schema = @Schema(implementation = CustomerInvoice.class))
	private List<CustomerInvoice> invoices;

	public static CustomerInvoicesResponse create() {
		return new CustomerInvoicesResponse();
	}

	public List<CustomerInvoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(final List<CustomerInvoice> invoices) {
		this.invoices = invoices;
	}

	public CustomerInvoicesResponse withInvoices(final List<CustomerInvoice> invoices) {
		this.invoices = invoices;
		return this;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(final MetaData metaData) {
		this.metaData = metaData;
	}

	public CustomerInvoicesResponse withMetaData(final MetaData metaData) {
		this.metaData = metaData;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(invoices, metaData);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CustomerInvoicesResponse other = (CustomerInvoicesResponse) obj;
		return Objects.equals(invoices, other.invoices) && Objects.equals(metaData, other.metaData);
	}

	@Override
	public String toString() {
		return "CustomerInvoicesResponse [invoices=" + invoices + ", metaData=" + metaData + "]";
	}
}

package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import se.sundsvall.dept44.common.validators.annotation.ValidOrganizationNumber;

@Schema(description = "Customer invoice request parameters model")
@ParameterObject
public class CustomerInvoicesParameters extends AbstractParameterBase {

	@NotEmpty
	@ArraySchema(arraySchema = @Schema(description = "Customer numbers (one or more)", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(examples = "216870"), minItems = 1)
	private List<@NotBlank String> customerNumbers;

	@ArraySchema(schema = @Schema(description = "Organization id of invoice issuer, if not provided all will be returned.", examples = "5565027223"))
	private List<@ValidOrganizationNumber String> organizationNumbers;

	@ArraySchema(schema = @Schema(description = "Facility ids to filter by, if not provided all will be returned.", examples = "123456789012345670"))
	private List<String> facilityIds;

	@Schema(description = "Invoice status filter", examples = "PAID")
	private InvoiceStatus status;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Earliest invoice period start. Format is YYYY-MM-DD.", examples = "2025-01-01")
	private LocalDate periodFrom;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Latest invoice period end. Format is YYYY-MM-DD.", examples = "2025-12-31")
	private LocalDate periodTo;

	// Not validated — DataWarehouseReader silently ignores unknown sortBy values.
	@Schema(description = "Column to sort by", examples = {
		"periodFrom", "periodTo", "InvoiceDate", "DueDate", "InvoiceNumber", "TotalAmount"
	})
	private String sortBy;

	public static CustomerInvoicesParameters create() {
		return new CustomerInvoicesParameters();
	}

	public List<String> getCustomerNumbers() {
		return customerNumbers;
	}

	public void setCustomerNumbers(final List<String> customerNumbers) {
		this.customerNumbers = customerNumbers;
	}

	public CustomerInvoicesParameters withCustomerNumbers(final List<String> customerNumbers) {
		this.customerNumbers = customerNumbers;
		return this;
	}

	public List<String> getOrganizationNumbers() {
		return organizationNumbers;
	}

	public void setOrganizationNumbers(final List<String> organizationNumbers) {
		this.organizationNumbers = organizationNumbers;
	}

	public CustomerInvoicesParameters withOrganizationNumbers(final List<String> organizationNumbers) {
		this.organizationNumbers = organizationNumbers;
		return this;
	}

	public List<String> getFacilityIds() {
		return facilityIds;
	}

	public void setFacilityIds(final List<String> facilityIds) {
		this.facilityIds = facilityIds;
	}

	public CustomerInvoicesParameters withFacilityIds(final List<String> facilityIds) {
		this.facilityIds = facilityIds;
		return this;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public void setStatus(final InvoiceStatus status) {
		this.status = status;
	}

	public CustomerInvoicesParameters withStatus(final InvoiceStatus status) {
		this.status = status;
		return this;
	}

	public LocalDate getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(final LocalDate periodFrom) {
		this.periodFrom = periodFrom;
	}

	public CustomerInvoicesParameters withPeriodFrom(final LocalDate periodFrom) {
		this.periodFrom = periodFrom;
		return this;
	}

	public LocalDate getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(final LocalDate periodTo) {
		this.periodTo = periodTo;
	}

	public CustomerInvoicesParameters withPeriodTo(final LocalDate periodTo) {
		this.periodTo = periodTo;
		return this;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(final String sortBy) {
		this.sortBy = sortBy;
	}

	public CustomerInvoicesParameters withSortBy(final String sortBy) {
		this.sortBy = sortBy;
		return this;
	}

	public CustomerInvoicesParameters withLimit(final int limit) {
		this.limit = limit;
		return this;
	}

	public CustomerInvoicesParameters withPage(final int page) {
		this.page = page;
		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		final CustomerInvoicesParameters that = (CustomerInvoicesParameters) o;
		return Objects.equals(customerNumbers, that.customerNumbers) && Objects.equals(organizationNumbers, that.organizationNumbers) && Objects.equals(facilityIds, that.facilityIds) && Objects.equals(status, that.status)
			&& Objects.equals(periodFrom, that.periodFrom) && Objects.equals(periodTo, that.periodTo) && Objects.equals(sortBy, that.sortBy);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), customerNumbers, organizationNumbers, facilityIds, status, periodFrom, periodTo, sortBy);
	}

	@Override
	public String toString() {
		return "CustomerInvoicesParameters{" +
			"customerNumbers=" + customerNumbers +
			", organizationNumbers=" + organizationNumbers +
			", facilityIds=" + facilityIds +
			", status=" + status +
			", periodFrom=" + periodFrom +
			", periodTo=" + periodTo +
			", sortBy='" + sortBy + '\'' +
			", page=" + page +
			", limit=" + limit +
			'}';
	}
}

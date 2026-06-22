package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import se.sundsvall.dept44.common.validators.annotation.MemberOf;
import se.sundsvall.dept44.common.validators.annotation.ValidOrganizationNumber;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;
import se.sundsvall.dept44.models.api.paging.AbstractParameterPagingAndSortingBase;

@Schema(description = "Customer invoice request parameters model")
@ParameterObject
public class CustomerInvoicesParameters extends AbstractParameterPagingAndSortingBase {

	@NotEmpty
	@ArraySchema(arraySchema = @Schema(description = "Customer numbers (one or more)", requiredMode = Schema.RequiredMode.REQUIRED), schema = @Schema(examples = "216870"), minItems = 1)
	private List<@NotBlank String> customerNumbers;

	@ArraySchema(schema = @Schema(description = "PartyId (e.g. a personId or an organizationId). Resolved to customer numbers and merged with customerNumbers.", examples = "81471222-5798-11e9-ae24-57fa13b361e1"), uniqueItems = true)
	private List<@ValidUuid String> partyIds;

	@ArraySchema(schema = @Schema(description = "Organization id of invoice issuer, if not provided all will be returned.", examples = "5565027223"))
	private List<@ValidOrganizationNumber String> organizationNumbers;

	@ArraySchema(schema = @Schema(description = "Facility ids to filter by, if not provided all will be returned.", examples = "123456789012345670"))
	private List<String> facilityIds;

	@MemberOf(value = InvoiceStatus.class, nullable = true)
	@Schema(description = "Invoice status filter", examples = "PAID", allowableValues = {
		"PAID", "SENT", "PARTIALLY_PAID", "DEBT_COLLECTION", "PAID_TOO_MUCH", "REMINDER", "VOID", "CREDITED", "WRITTEN_OFF", "UNKNOWN"
	})
	private String status;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Earliest invoice period start. Format is YYYY-MM-DD.", examples = "2025-01-01")
	private LocalDate periodFrom;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Latest invoice period end. Format is YYYY-MM-DD.", examples = "2025-12-31")
	private LocalDate periodTo;

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

	public List<String> getPartyIds() {
		return partyIds;
	}

	public void setPartyIds(final List<String> partyIds) {
		this.partyIds = partyIds;
	}

	public CustomerInvoicesParameters withPartyIds(final List<String> partyIds) {
		this.partyIds = partyIds;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public CustomerInvoicesParameters withStatus(final String status) {
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

	@Override
	@ArraySchema(schema = @Schema(description = "Column to sort by", examples = {
		"periodFrom", "periodTo", "InvoiceDate", "DueDate", "InvoiceNumber", "TotalAmount"
	}))
	public List<String> getSortBy() {
		return super.getSortBy();
	}

	public CustomerInvoicesParameters withSortBy(final List<String> sortBy) {
		this.sortBy = sortBy;
		return this;
	}

	public CustomerInvoicesParameters withSortDirection(final Sort.Direction sortDirection) {
		this.sortDirection = sortDirection;
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
		return Objects.equals(customerNumbers, that.customerNumbers)
			&& Objects.equals(partyIds, that.partyIds)
			&& Objects.equals(organizationNumbers, that.organizationNumbers)
			&& Objects.equals(facilityIds, that.facilityIds)
			&& Objects.equals(status, that.status)
			&& Objects.equals(periodFrom, that.periodFrom)
			&& Objects.equals(periodTo, that.periodTo)
			&& Objects.equals(sortBy, that.sortBy)
			&& Objects.equals(sortDirection, that.sortDirection)
			&& Objects.equals(limit, that.limit)
			&& Objects.equals(page, that.page);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), customerNumbers, partyIds, organizationNumbers, facilityIds, status, periodFrom, periodTo, sortBy, sortDirection, page, limit);
	}

	@Override
	public String toString() {
		return "CustomerInvoicesParameters{" +
			"customerNumbers=" + customerNumbers +
			", partyIds=" + partyIds +
			", organizationNumbers=" + organizationNumbers +
			", facilityIds=" + facilityIds +
			", status=" + status +
			", periodFrom=" + periodFrom +
			", periodTo=" + periodTo +
			", sortBy=" + sortBy +
			", sortDirection=" + sortDirection +
			", page=" + page +
			", limit=" + limit +
			'}';
	}
}

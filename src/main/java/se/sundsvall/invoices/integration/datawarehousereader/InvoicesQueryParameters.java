package se.sundsvall.invoices.integration.datawarehousereader;

import generated.se.sundsvall.datawarehousereader.CustomerType;
import generated.se.sundsvall.datawarehousereader.Direction;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Query parameters for {@link DataWarehouseReaderClient#getInvoices(String, InvoicesQueryParameters)}. Used with
 * {@code @SpringQueryMap} so the wire-level field-by-field signature lives here instead of as 19 method parameters.
 */
public class InvoicesQueryParameters {

	private List<String> customerNumber;
	private CustomerType customerType;
	private List<String> facilityIds;
	private Long invoiceNumber;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate invoiceDateFrom;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate invoiceDateTo;

	private String invoiceName;
	private String invoiceType;
	private String invoiceStatus;
	private Long ocrNumber;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dueDateFrom;

	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dueDateTo;

	private String organizationGroup;
	private List<String> organizationNumbers;
	private String administration;
	private List<String> sortBy;
	private Direction sortDirection;
	private Integer page;
	private Integer limit;

	public static InvoicesQueryParameters create() {
		return new InvoicesQueryParameters();
	}

	public List<String> getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(final List<String> customerNumber) {
		this.customerNumber = customerNumber;
	}

	public InvoicesQueryParameters withCustomerNumber(final List<String> customerNumber) {
		this.customerNumber = customerNumber;
		return this;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(final CustomerType customerType) {
		this.customerType = customerType;
	}

	public InvoicesQueryParameters withCustomerType(final CustomerType customerType) {
		this.customerType = customerType;
		return this;
	}

	public List<String> getFacilityIds() {
		return facilityIds;
	}

	public void setFacilityIds(final List<String> facilityIds) {
		this.facilityIds = facilityIds;
	}

	public InvoicesQueryParameters withFacilityIds(final List<String> facilityIds) {
		this.facilityIds = facilityIds;
		return this;
	}

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(final Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public InvoicesQueryParameters withInvoiceNumber(final Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
		return this;
	}

	public LocalDate getInvoiceDateFrom() {
		return invoiceDateFrom;
	}

	public void setInvoiceDateFrom(final LocalDate invoiceDateFrom) {
		this.invoiceDateFrom = invoiceDateFrom;
	}

	public InvoicesQueryParameters withInvoiceDateFrom(final LocalDate invoiceDateFrom) {
		this.invoiceDateFrom = invoiceDateFrom;
		return this;
	}

	public LocalDate getInvoiceDateTo() {
		return invoiceDateTo;
	}

	public void setInvoiceDateTo(final LocalDate invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
	}

	public InvoicesQueryParameters withInvoiceDateTo(final LocalDate invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
		return this;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(final String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public InvoicesQueryParameters withInvoiceName(final String invoiceName) {
		this.invoiceName = invoiceName;
		return this;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(final String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public InvoicesQueryParameters withInvoiceType(final String invoiceType) {
		this.invoiceType = invoiceType;
		return this;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(final String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public InvoicesQueryParameters withInvoiceStatus(final String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
		return this;
	}

	public Long getOcrNumber() {
		return ocrNumber;
	}

	public void setOcrNumber(final Long ocrNumber) {
		this.ocrNumber = ocrNumber;
	}

	public InvoicesQueryParameters withOcrNumber(final Long ocrNumber) {
		this.ocrNumber = ocrNumber;
		return this;
	}

	public LocalDate getDueDateFrom() {
		return dueDateFrom;
	}

	public void setDueDateFrom(final LocalDate dueDateFrom) {
		this.dueDateFrom = dueDateFrom;
	}

	public InvoicesQueryParameters withDueDateFrom(final LocalDate dueDateFrom) {
		this.dueDateFrom = dueDateFrom;
		return this;
	}

	public LocalDate getDueDateTo() {
		return dueDateTo;
	}

	public void setDueDateTo(final LocalDate dueDateTo) {
		this.dueDateTo = dueDateTo;
	}

	public InvoicesQueryParameters withDueDateTo(final LocalDate dueDateTo) {
		this.dueDateTo = dueDateTo;
		return this;
	}

	public String getOrganizationGroup() {
		return organizationGroup;
	}

	public void setOrganizationGroup(final String organizationGroup) {
		this.organizationGroup = organizationGroup;
	}

	public InvoicesQueryParameters withOrganizationGroup(final String organizationGroup) {
		this.organizationGroup = organizationGroup;
		return this;
	}

	public List<String> getOrganizationNumbers() {
		return organizationNumbers;
	}

	public void setOrganizationNumbers(final List<String> organizationNumbers) {
		this.organizationNumbers = organizationNumbers;
	}

	public InvoicesQueryParameters withOrganizationNumbers(final List<String> organizationNumbers) {
		this.organizationNumbers = organizationNumbers;
		return this;
	}

	public String getAdministration() {
		return administration;
	}

	public void setAdministration(final String administration) {
		this.administration = administration;
	}

	public InvoicesQueryParameters withAdministration(final String administration) {
		this.administration = administration;
		return this;
	}

	public List<String> getSortBy() {
		return sortBy;
	}

	public void setSortBy(final List<String> sortBy) {
		this.sortBy = sortBy;
	}

	public InvoicesQueryParameters withSortBy(final List<String> sortBy) {
		this.sortBy = sortBy;
		return this;
	}

	public Direction getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(final Direction sortDirection) {
		this.sortDirection = sortDirection;
	}

	public InvoicesQueryParameters withSortDirection(final Direction sortDirection) {
		this.sortDirection = sortDirection;
		return this;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(final Integer page) {
		this.page = page;
	}

	public InvoicesQueryParameters withPage(final Integer page) {
		this.page = page;
		return this;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(final Integer limit) {
		this.limit = limit;
	}

	public InvoicesQueryParameters withLimit(final Integer limit) {
		this.limit = limit;
		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		final InvoicesQueryParameters that = (InvoicesQueryParameters) o;
		return Objects.equals(customerNumber, that.customerNumber) && customerType == that.customerType && Objects.equals(facilityIds, that.facilityIds) && Objects.equals(invoiceNumber, that.invoiceNumber)
			&& Objects.equals(invoiceDateFrom, that.invoiceDateFrom) && Objects.equals(invoiceDateTo, that.invoiceDateTo) && Objects.equals(invoiceName, that.invoiceName) && Objects.equals(invoiceType, that.invoiceType)
			&& Objects.equals(invoiceStatus, that.invoiceStatus) && Objects.equals(ocrNumber, that.ocrNumber) && Objects.equals(dueDateFrom, that.dueDateFrom) && Objects.equals(dueDateTo, that.dueDateTo)
			&& Objects.equals(organizationGroup, that.organizationGroup) && Objects.equals(organizationNumbers, that.organizationNumbers) && Objects.equals(administration, that.administration) && Objects.equals(sortBy, that.sortBy)
			&& sortDirection == that.sortDirection && Objects.equals(page, that.page) && Objects.equals(limit, that.limit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerNumber, customerType, facilityIds, invoiceNumber, invoiceDateFrom, invoiceDateTo, invoiceName, invoiceType, invoiceStatus, ocrNumber, dueDateFrom, dueDateTo, organizationGroup, organizationNumbers,
			administration, sortBy, sortDirection, page, limit);
	}

	@Override
	public String toString() {
		return "InvoicesQueryParameters{" +
			"customerNumber=" + customerNumber +
			", customerType=" + customerType +
			", facilityIds=" + facilityIds +
			", invoiceNumber=" + invoiceNumber +
			", invoiceDateFrom=" + invoiceDateFrom +
			", invoiceDateTo=" + invoiceDateTo +
			", invoiceName='" + invoiceName + '\'' +
			", invoiceType='" + invoiceType + '\'' +
			", invoiceStatus='" + invoiceStatus + '\'' +
			", ocrNumber=" + ocrNumber +
			", dueDateFrom=" + dueDateFrom +
			", dueDateTo=" + dueDateTo +
			", organizationGroup='" + organizationGroup + '\'' +
			", organizationNumbers=" + organizationNumbers +
			", administration='" + administration + '\'' +
			", sortBy=" + sortBy +
			", sortDirection=" + sortDirection +
			", page=" + page +
			", limit=" + limit +
			'}';
	}
}

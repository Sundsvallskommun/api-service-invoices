package se.sundsvall.invoices.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import se.sundsvall.dept44.common.validators.annotation.ValidOrganizationNumber;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;

@Schema(description = "Invoice request parameters model")
@ParameterObject
public class InvoicesParameters extends AbstractParameterBase {

	@ArraySchema(schema = @Schema(description = "PartyId (e.g. a personId or an organizationId)", examples = "81471222-5798-11e9-ae24-57fa13b361e1"), minItems = 1, uniqueItems = true)
	@NotEmpty
	private List<@ValidUuid String> partyId;

	@ArraySchema(schema = @Schema(description = "Facility id", examples = "735999109151401011"))
	private List<String> facilityIds;

	@Schema(description = "Invoice number", examples = "767915994")
	private String invoiceNumber;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Earliest invoice date. Format is YYYY-MM-DD.", examples = "2022-01-01")
	private LocalDate invoiceDateFrom;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Latest invoice date. Format is YYYY-MM-DD.", examples = "2022-01-31")
	private LocalDate invoiceDateTo;

	@Schema(description = "invoice name", examples = "765801493.pdf")
	private String invoiceName;

	@Schema(description = "Invoice type", examples = "NORMAL", enumAsRef = true)
	private InvoiceType invoiceType;

	@Schema(description = "Invoice status", examples = "PAID", enumAsRef = true)
	private InvoiceStatus invoiceStatus;

	@Schema(description = "Ocr number", examples = "767915994")
	private String ocrNumber;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Earliest due date. Format is YYYY-MM-DD.", examples = "2022-01-01")
	private LocalDate dueDateFrom;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Latest due date. Format is YYYY-MM-DD.", examples = "2022-01-31")
	private LocalDate dueDateTo;

	@ArraySchema(schema = @Schema(description = "Creditor organization number", examples = "5564786647"))
	@JsonProperty("organizationNumber")
	private List<@ValidOrganizationNumber(nullable = true) String> organizationNumbers;

	@Schema(description = "Organization group", examples = "stadsbacken")
	private String organizationGroup;

	public static InvoicesParameters create() {
		return new InvoicesParameters();
	}

	public List<String> getPartyId() {
		return partyId;
	}

	public void setPartyId(final List<String> partyId) {
		this.partyId = partyId;
	}

	public InvoicesParameters withPartyId(final List<String> partyId) {
		this.partyId = partyId;
		return this;
	}

	public List<String> getFacilityIds() {
		return facilityIds;
	}

	public void setFacilityIds(final List<String> facilityIds) {
		this.facilityIds = facilityIds;
	}

	public InvoicesParameters withFacilityIds(final List<String> facilityIds) {
		this.facilityIds = facilityIds;
		return this;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(final String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public InvoicesParameters withInvoiceNumber(final String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
		return this;
	}

	public LocalDate getInvoiceDateFrom() {
		return invoiceDateFrom;
	}

	public void setInvoiceDateFrom(final LocalDate invoiceDateFrom) {
		this.invoiceDateFrom = invoiceDateFrom;
	}

	public InvoicesParameters withInvoiceDateFrom(final LocalDate invoiceDateFrom) {
		this.invoiceDateFrom = invoiceDateFrom;
		return this;
	}

	public LocalDate getInvoiceDateTo() {
		return invoiceDateTo;
	}

	public void setInvoiceDateTo(final LocalDate invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
	}

	public InvoicesParameters withInvoiceDateTo(final LocalDate invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
		return this;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(final String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public InvoicesParameters withInvoiceName(final String invoiceName) {
		this.invoiceName = invoiceName;
		return this;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(final InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public InvoicesParameters withInvoiceType(final InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
		return this;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(final InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public InvoicesParameters withInvoiceStatus(final InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
		return this;
	}

	public String getOcrNumber() {
		return ocrNumber;
	}

	public void setOcrNumber(final String ocrNumber) {
		this.ocrNumber = ocrNumber;
	}

	public InvoicesParameters withOcrNumber(final String ocrNumber) {
		this.ocrNumber = ocrNumber;
		return this;
	}

	public LocalDate getDueDateFrom() {
		return dueDateFrom;
	}

	public void setDueDateFrom(final LocalDate dueDateFrom) {
		this.dueDateFrom = dueDateFrom;
	}

	public InvoicesParameters withDueDateFrom(final LocalDate dueDateFrom) {
		this.dueDateFrom = dueDateFrom;
		return this;
	}

	public LocalDate getDueDateTo() {
		return dueDateTo;
	}

	public void setDueDateTo(final LocalDate dueDateTo) {
		this.dueDateTo = dueDateTo;
	}

	public InvoicesParameters withDueDateTo(final LocalDate dueDateTo) {
		this.dueDateTo = dueDateTo;
		return this;
	}

	public String getOrganizationGroup() {
		return organizationGroup;
	}

	public void setOrganizationGroup(final String organizationGroup) {
		this.organizationGroup = organizationGroup;
	}

	public InvoicesParameters withOrganizationGroup(final String organizationGroup) {
		this.organizationGroup = organizationGroup;
		return this;
	}

	public void setOrganizationNumber(final List<String> organizationNumber) {
		this.organizationNumbers = organizationNumber;
	}

	public List<String> getOrganizationNumbers() {
		return organizationNumbers;
	}

	public void setOrganizationNumbers(final List<String> organizationNumbers) {
		this.organizationNumbers = organizationNumbers;
	}

	public InvoicesParameters withOrganizationNumbers(final List<String> organizationNumbers) {
		this.organizationNumbers = organizationNumbers;
		return this;
	}

	public InvoicesParameters withLimit(final int limit) {
		this.limit = limit;
		return this;
	}

	public InvoicesParameters withPage(final int page) {
		this.page = page;
		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		final InvoicesParameters that = (InvoicesParameters) o;
		return Objects.equals(partyId, that.partyId) && Objects.equals(facilityIds, that.facilityIds) && Objects.equals(invoiceNumber, that.invoiceNumber) && Objects.equals(invoiceDateFrom, that.invoiceDateFrom)
			&& Objects.equals(invoiceDateTo, that.invoiceDateTo) && Objects.equals(invoiceName, that.invoiceName) && invoiceType == that.invoiceType && invoiceStatus == that.invoiceStatus && Objects.equals(ocrNumber,
				that.ocrNumber) && Objects.equals(dueDateFrom, that.dueDateFrom) && Objects.equals(dueDateTo, that.dueDateTo) && Objects.equals(organizationNumbers, that.organizationNumbers) && Objects.equals(
					organizationGroup, that.organizationGroup);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), partyId, facilityIds, invoiceNumber, invoiceDateFrom, invoiceDateTo, invoiceName, invoiceType, invoiceStatus, ocrNumber, dueDateFrom, dueDateTo, organizationNumbers, organizationGroup);
	}

	@Override
	public String toString() {
		return "InvoicesParameters{" +
			"partyId=" + partyId +
			", facilityIds=" + facilityIds +
			", invoiceNumber='" + invoiceNumber + '\'' +
			", invoiceDateFrom=" + invoiceDateFrom +
			", invoiceDateTo=" + invoiceDateTo +
			", invoiceName='" + invoiceName + '\'' +
			", invoiceType=" + invoiceType +
			", invoiceStatus=" + invoiceStatus +
			", ocrNumber='" + ocrNumber + '\'' +
			", dueDateFrom=" + dueDateFrom +
			", dueDateTo=" + dueDateTo +
			", organizationNumbers=" + organizationNumbers +
			", organizationGroup='" + organizationGroup + '\'' +
			", page=" + page +
			", limit=" + limit +
			'}';
	}
}

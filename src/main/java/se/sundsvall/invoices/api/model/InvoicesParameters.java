package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import se.sundsvall.dept44.common.validators.annotation.ValidOrganizationNumber;
import se.sundsvall.dept44.common.validators.annotation.ValidUuid;

@Schema(description = "Invoice request parameters model")
public class InvoicesParameters extends AbstractParameterBase {

	@ArraySchema(schema = @Schema(description = "PartyId (e.g. a personId or an organizationId)", example = "81471222-5798-11e9-ae24-57fa13b361e1"), minItems = 1, uniqueItems = true)
	@NotEmpty
	private List<@ValidUuid String> partyId;

	@ArraySchema(schema = @Schema(description = "Facility id", example = "735999109151401011"))
	private List<String> facilityId;

	@Schema(description = "Invoice number", example = "767915994")
	private String invoiceNumber;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Earliest invoice date. Format is YYYY-MM-DD.", example = "2022-01-01")
	private LocalDate invoiceDateFrom;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Latest invoice date. Format is YYYY-MM-DD.", example = "2022-01-31")
	private LocalDate invoiceDateTo;

	@Schema(description = "invoice name", example = "765801493.pdf")
	private String invoiceName;

	@Schema(description = "Invoice type", example = "NORMAL", enumAsRef = true)
	private InvoiceType invoiceType;

	@Schema(description = "Invoice status", example = "PAID", enumAsRef = true)
	private InvoiceStatus invoiceStatus;

	@Schema(description = "Ocr number", example = "767915994")
	private String ocrNumber;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Earliest due date. Format is YYYY-MM-DD.", example = "2022-01-01")
	private LocalDate dueDateFrom;

	@DateTimeFormat(iso = ISO.DATE)
	@Schema(description = "Latest due date. Format is YYYY-MM-DD.", example = "2022-01-31")
	private LocalDate dueDateTo;

	@Schema(description = "Creditor organization number", example = "5564786647")
	@ValidOrganizationNumber(nullable = true)
	private String organizationNumber;

	@Schema(description = "Organization group", example = "stadsbacken")
	private String organizationGroup;

	public static InvoicesParameters create() {
		return new InvoicesParameters();
	}

	public List<String> getPartyId() {
		return partyId;
	}

	public void setPartyId(List<String> partyId) {
		this.partyId = partyId;
	}

	public InvoicesParameters withPartyId(List<String> partyId) {
		this.partyId = partyId;
		return this;
	}

	public List<String> getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(List<String> facilityId) {
		this.facilityId = facilityId;
	}

	public InvoicesParameters withFacilityId(List<String> facilityId) {
		this.facilityId = facilityId;
		return this;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public InvoicesParameters withInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
		return this;
	}

	public LocalDate getInvoiceDateFrom() {
		return invoiceDateFrom;
	}

	public void setInvoiceDateFrom(LocalDate invoiceDateFrom) {
		this.invoiceDateFrom = invoiceDateFrom;
	}

	public InvoicesParameters withInvoiceDateFrom(LocalDate invoiceDateFrom) {
		this.invoiceDateFrom = invoiceDateFrom;
		return this;
	}

	public LocalDate getInvoiceDateTo() {
		return invoiceDateTo;
	}

	public void setInvoiceDateTo(LocalDate invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
	}

	public InvoicesParameters withInvoiceDateTo(LocalDate invoiceDateTo) {
		this.invoiceDateTo = invoiceDateTo;
		return this;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public InvoicesParameters withInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
		return this;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public InvoicesParameters withInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
		return this;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public InvoicesParameters withInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
		return this;
	}

	public String getOcrNumber() {
		return ocrNumber;
	}

	public void setOcrNumber(String ocrNumber) {
		this.ocrNumber = ocrNumber;
	}

	public InvoicesParameters withOcrNumber(String ocrNumber) {
		this.ocrNumber = ocrNumber;
		return this;
	}

	public LocalDate getDueDateFrom() {
		return dueDateFrom;
	}

	public void setDueDateFrom(LocalDate dueDateFrom) {
		this.dueDateFrom = dueDateFrom;
	}

	public InvoicesParameters withDueDateFrom(LocalDate dueDateFrom) {
		this.dueDateFrom = dueDateFrom;
		return this;
	}

	public LocalDate getDueDateTo() {
		return dueDateTo;
	}

	public void setDueDateTo(LocalDate dueDateTo) {
		this.dueDateTo = dueDateTo;
	}

	public InvoicesParameters withDueDateTo(LocalDate dueDateTo) {
		this.dueDateTo = dueDateTo;
		return this;
	}

	public String getOrganizationGroup() {
		return organizationGroup;
	}

	public void setOrganizationGroup(String organizationGroup) {
		this.organizationGroup = organizationGroup;
	}

	public InvoicesParameters withOrganizationGroup(String organizationGroup) {
		this.organizationGroup = organizationGroup;
		return this;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
	}

	public InvoicesParameters withOrganizationNumber(String organizationNumber) {
		this.organizationNumber = organizationNumber;
		return this;
	}

	public InvoicesParameters withLimit(int limit) {
		this.limit = limit;
		return this;
	}

	public InvoicesParameters withPage(int page) {
		this.page = page;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(dueDateFrom, dueDateTo, facilityId, invoiceDateFrom, invoiceDateTo, invoiceName, invoiceNumber, invoiceStatus, invoiceType, ocrNumber, organizationGroup, organizationNumber, partyId);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		InvoicesParameters other = (InvoicesParameters) obj;
		return Objects.equals(dueDateFrom, other.dueDateFrom) && Objects.equals(dueDateTo, other.dueDateTo) && Objects.equals(facilityId, other.facilityId) && Objects.equals(invoiceDateFrom, other.invoiceDateFrom) && Objects.equals(invoiceDateTo,
			other.invoiceDateTo) && Objects.equals(invoiceName, other.invoiceName) && Objects.equals(invoiceNumber, other.invoiceNumber) && invoiceStatus == other.invoiceStatus && invoiceType == other.invoiceType && Objects.equals(ocrNumber,
				other.ocrNumber) && Objects.equals(organizationGroup, other.organizationGroup) && Objects.equals(organizationNumber, other.organizationNumber) && Objects.equals(partyId, other.partyId);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoicesParameters [partyId=").append(partyId).append(", facilityId=").append(facilityId).append(", invoiceNumber=").append(invoiceNumber).append(", invoiceDateFrom=").append(invoiceDateFrom).append(", invoiceDateTo=").append(
			invoiceDateTo).append(", invoiceName=").append(invoiceName).append(", invoiceType=").append(invoiceType).append(", invoiceStatus=").append(invoiceStatus).append(", ocrNumber=").append(ocrNumber).append(", dueDateFrom=").append(
				dueDateFrom).append(", dueDateTo=").append(dueDateTo).append(", organizationNumber=").append(organizationNumber).append(", organizationGroup=").append(organizationGroup).append(", page=").append(page).append(", limit=").append(limit)
			.append("]");
		return builder.toString();
	}
}

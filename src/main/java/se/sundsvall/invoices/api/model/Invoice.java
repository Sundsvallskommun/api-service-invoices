package se.sundsvall.invoices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Invoice model")
public class Invoice {

	@Schema(examples = "2022-02-28", description = "Due date")
	private LocalDate dueDate;

	@Schema(examples = "814.00", description = "Invoice-amount including VAT and rounding")
	private float totalAmount;

	@Schema(examples = "813.50", description = "Invoice-amount including VAT")
	private float amountVatIncluded;

	@Schema(examples = "651.20", description = "Invoice-amount excluding VAT")
	private float amountVatExcluded;

	@Schema(examples = "651.20", description = "Amount which VAT is applied on")
	private float vatEligibleAmount;

	@Schema(examples = "0.50", description = "Equalization to integer")
	private float rounding;

	@Schema(examples = "162.80", description = "VAT")
	private float vat;

	@Schema(examples = "false", description = "Is VAT reversed")
	private Boolean reversedVat;

	@Schema(examples = "false", description = "Is invoice-pdf available")
	private Boolean pdfAvailable;

	@Schema(examples = "SEK", description = "Currency")
	private String currency;

	@Schema(examples = "2022-01-15", description = "Invoice-date")
	private LocalDate invoiceDate;

	@Schema(examples = "2022-01-01", description = "Invoice from-date")
	private LocalDate fromDate;

	@Schema(examples = "2022-01-31", description = "Invoice to-date")
	private LocalDate toDate;

	@Schema(examples = "999", description = "Invoice-number")
	private String invoiceNumber;

	@Schema(implementation = InvoiceStatus.class)
	private InvoiceStatus invoiceStatus;

	@Schema(examples = "96758235", description = "OCR-number")
	private String ocrNumber;

	@Schema(description = "Organization number of the creditor", examples = "5565027223")
	private String organizationNumber;

	@Schema(examples = "faktura-999.pdf", description = "Invoice-name")
	private String invoiceName;

	@Schema(implementation = InvoiceType.class)
	private InvoiceType invoiceType;

	@Schema(examples = "Fjärrvärme", description = "Invoice-description")
	private String invoiceDescription;

	@Schema(description = "Invoice-address")
	private Address invoiceAddress;

	@Schema(description = "Facility-id")
	private String facilityId;

	@Schema(implementation = InvoiceOrigin.class)
	private InvoiceOrigin invoiceOrigin;

	public static Invoice create() {
		return new Invoice();
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Invoice withDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
		return this;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Invoice withTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
		return this;
	}

	public float getAmountVatIncluded() {
		return amountVatIncluded;
	}

	public void setAmountVatIncluded(float amountVatIncluded) {
		this.amountVatIncluded = amountVatIncluded;
	}

	public Invoice withAmountVatIncluded(float amountVatIncluded) {
		this.amountVatIncluded = amountVatIncluded;
		return this;
	}

	public float getAmountVatExcluded() {
		return amountVatExcluded;
	}

	public void setAmountVatExcluded(float amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
	}

	public Invoice withAmountVatExcluded(float amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
		return this;
	}

	public float getVatEligibleAmount() {
		return vatEligibleAmount;
	}

	public void setVatEligibleAmount(float vatEligibleAmount) {
		this.vatEligibleAmount = vatEligibleAmount;
	}

	public Invoice withVatEligibleAmount(float vatEligibleAmount) {
		this.vatEligibleAmount = vatEligibleAmount;
		return this;
	}

	public float getRounding() {
		return rounding;
	}

	public void setRounding(float rounding) {
		this.rounding = rounding;
	}

	public Invoice withRounding(float rounding) {
		this.rounding = rounding;
		return this;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(float vat) {
		this.vat = vat;
	}

	public Invoice withVat(float vat) {
		this.vat = vat;
		return this;
	}

	public Boolean getReversedVat() {
		return reversedVat;
	}

	public void setReversedVat(Boolean reversedVat) {
		this.reversedVat = reversedVat;
	}

	public Invoice withReversedVat(Boolean reversedVat) {
		this.reversedVat = reversedVat;
		return this;
	}

	public Boolean getPdfAvailable() {
		return pdfAvailable;
	}

	public void setPdfAvailable(Boolean pdfAvailable) {
		this.pdfAvailable = pdfAvailable;
	}

	public Invoice withPdfAvailable(Boolean pdfAvailable) {
		this.pdfAvailable = pdfAvailable;
		return this;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Invoice withCurrency(String currency) {
		this.currency = currency;
		return this;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Invoice withInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
		return this;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public Invoice withFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
		return this;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public Invoice withToDate(LocalDate toDate) {
		this.toDate = toDate;
		return this;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Invoice withInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
		return this;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Invoice withInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
		return this;
	}

	public String getOcrNumber() {
		return ocrNumber;
	}

	public void setOcrNumber(String ocrNumber) {
		this.ocrNumber = ocrNumber;
	}

	public Invoice withOcrNumber(String ocrNumber) {
		this.ocrNumber = ocrNumber;
		return this;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public Invoice withInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
		return this;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Invoice withInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
		return this;
	}

	public Invoice withInvoiceDescription(String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
		return this;
	}

	public String getInvoiceDescription() {
		return invoiceDescription;
	}

	public void setInvoiceDescription(String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
	}

	public Address getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(Address invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public Invoice withInvoiceAddress(Address invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
		return this;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Invoice withFacilityId(String facilityId) {
		this.facilityId = facilityId;
		return this;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(String creditorOrganizationNumber) {
		this.organizationNumber = creditorOrganizationNumber;
	}

	public Invoice withOrganizationNumber(String creditorOrganizationNumber) {
		this.organizationNumber = creditorOrganizationNumber;
		return this;
	}

	public InvoiceOrigin getInvoiceOrigin() {
		return invoiceOrigin;
	}

	public void setInvoiceOrigin(InvoiceOrigin invoiceOrigin) {
		this.invoiceOrigin = invoiceOrigin;
	}

	public Invoice withInvoiceOrigin(InvoiceOrigin invoiceOrigin) {
		this.invoiceOrigin = invoiceOrigin;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amountVatExcluded, amountVatIncluded, currency, dueDate, facilityId, fromDate, invoiceAddress, invoiceDate, invoiceDescription, invoiceName, invoiceNumber, invoiceOrigin, invoiceStatus, invoiceType, ocrNumber,
			organizationNumber, pdfAvailable, reversedVat, rounding, toDate, totalAmount, vat, vatEligibleAmount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Invoice other = (Invoice) obj;
		return Float.floatToIntBits(amountVatExcluded) == Float.floatToIntBits(other.amountVatExcluded) && Float.floatToIntBits(amountVatIncluded) == Float.floatToIntBits(other.amountVatIncluded) && Objects.equals(currency, other.currency) && Objects
			.equals(dueDate, other.dueDate) && Objects.equals(facilityId, other.facilityId) && Objects.equals(fromDate, other.fromDate) && Objects.equals(invoiceAddress, other.invoiceAddress) && Objects.equals(invoiceDate, other.invoiceDate)
			&& Objects.equals(invoiceDescription, other.invoiceDescription) && Objects.equals(invoiceName, other.invoiceName) && Objects.equals(invoiceNumber, other.invoiceNumber) && invoiceOrigin == other.invoiceOrigin
			&& invoiceStatus == other.invoiceStatus && invoiceType == other.invoiceType && Objects.equals(ocrNumber, other.ocrNumber) && Objects.equals(organizationNumber, other.organizationNumber) && Objects.equals(pdfAvailable, other.pdfAvailable)
			&& Objects.equals(reversedVat, other.reversedVat) && Float.floatToIntBits(rounding) == Float.floatToIntBits(other.rounding) && Objects.equals(toDate, other.toDate) && Float.floatToIntBits(totalAmount) == Float.floatToIntBits(
				other.totalAmount) && Float.floatToIntBits(vat) == Float.floatToIntBits(other.vat) && Float.floatToIntBits(vatEligibleAmount) == Float.floatToIntBits(other.vatEligibleAmount);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invoice [dueDate=").append(dueDate).append(", totalAmount=").append(totalAmount).append(", amountVatIncluded=").append(amountVatIncluded).append(", amountVatExcluded=").append(amountVatExcluded).append(", vatEligibleAmount=")
			.append(vatEligibleAmount).append(", rounding=").append(rounding).append(", vat=").append(vat).append(", reversedVat=").append(reversedVat).append(", pdfAvailable=").append(pdfAvailable).append(", currency=").append(currency).append(
				", invoiceDate=").append(invoiceDate).append(", fromDate=").append(fromDate).append(", toDate=").append(toDate).append(", invoiceNumber=").append(invoiceNumber).append(", invoiceStatus=").append(invoiceStatus).append(", ocrNumber=")
			.append(ocrNumber).append(", organizationNumber=").append(organizationNumber).append(", invoiceName=").append(invoiceName).append(", invoiceType=").append(invoiceType).append(", invoiceDescription=").append(invoiceDescription).append(
				", invoiceAddress=").append(invoiceAddress).append(", facilityId=").append(facilityId).append(", invoiceOrigin=").append(invoiceOrigin).append("]");
		return builder.toString();
	}
}

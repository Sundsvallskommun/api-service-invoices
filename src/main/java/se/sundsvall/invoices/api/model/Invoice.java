package se.sundsvall.invoices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

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
	private Set<String> invoiceDescriptions;

	@Schema(description = "Invoice-address")
	private Address invoiceAddress;

	@Schema(description = "Facility-id")
	private Set<String> facilityIds;

	@Schema(implementation = InvoiceOrigin.class)
	private InvoiceOrigin invoiceOrigin;

	public static Invoice create() {
		return new Invoice();
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(final LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Invoice withDueDate(final LocalDate dueDate) {
		this.dueDate = dueDate;
		return this;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(final float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Invoice withTotalAmount(final float totalAmount) {
		this.totalAmount = totalAmount;
		return this;
	}

	public float getAmountVatIncluded() {
		return amountVatIncluded;
	}

	public void setAmountVatIncluded(final float amountVatIncluded) {
		this.amountVatIncluded = amountVatIncluded;
	}

	public Invoice withAmountVatIncluded(final float amountVatIncluded) {
		this.amountVatIncluded = amountVatIncluded;
		return this;
	}

	public float getAmountVatExcluded() {
		return amountVatExcluded;
	}

	public void setAmountVatExcluded(final float amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
	}

	public Invoice withAmountVatExcluded(final float amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
		return this;
	}

	public float getVatEligibleAmount() {
		return vatEligibleAmount;
	}

	public void setVatEligibleAmount(final float vatEligibleAmount) {
		this.vatEligibleAmount = vatEligibleAmount;
	}

	public Invoice withVatEligibleAmount(final float vatEligibleAmount) {
		this.vatEligibleAmount = vatEligibleAmount;
		return this;
	}

	public float getRounding() {
		return rounding;
	}

	public void setRounding(final float rounding) {
		this.rounding = rounding;
	}

	public Invoice withRounding(final float rounding) {
		this.rounding = rounding;
		return this;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(final float vat) {
		this.vat = vat;
	}

	public Invoice withVat(final float vat) {
		this.vat = vat;
		return this;
	}

	public Boolean getReversedVat() {
		return reversedVat;
	}

	public void setReversedVat(final Boolean reversedVat) {
		this.reversedVat = reversedVat;
	}

	public Invoice withReversedVat(final Boolean reversedVat) {
		this.reversedVat = reversedVat;
		return this;
	}

	public Boolean getPdfAvailable() {
		return pdfAvailable;
	}

	public void setPdfAvailable(final Boolean pdfAvailable) {
		this.pdfAvailable = pdfAvailable;
	}

	public Invoice withPdfAvailable(final Boolean pdfAvailable) {
		this.pdfAvailable = pdfAvailable;
		return this;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	public Invoice withCurrency(final String currency) {
		this.currency = currency;
		return this;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(final LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Invoice withInvoiceDate(final LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
		return this;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(final LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public Invoice withFromDate(final LocalDate fromDate) {
		this.fromDate = fromDate;
		return this;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(final LocalDate toDate) {
		this.toDate = toDate;
	}

	public Invoice withToDate(final LocalDate toDate) {
		this.toDate = toDate;
		return this;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(final String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Invoice withInvoiceNumber(final String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
		return this;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(final InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Invoice withInvoiceStatus(final InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
		return this;
	}

	public String getOcrNumber() {
		return ocrNumber;
	}

	public void setOcrNumber(final String ocrNumber) {
		this.ocrNumber = ocrNumber;
	}

	public Invoice withOcrNumber(final String ocrNumber) {
		this.ocrNumber = ocrNumber;
		return this;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(final String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public Invoice withInvoiceName(final String invoiceName) {
		this.invoiceName = invoiceName;
		return this;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(final InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Invoice withInvoiceType(final InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
		return this;
	}

	public Set<String> getInvoiceDescriptions() {
		return invoiceDescriptions;
	}

	public void setInvoiceDescriptions(final Set<String> invoiceDescriptions) {
		this.invoiceDescriptions = invoiceDescriptions;
	}

	public Invoice withInvoiceDescriptions(final Set<String> invoiceDescriptions) {
		this.invoiceDescriptions = invoiceDescriptions;
		return this;
	}

	public Address getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(final Address invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public Invoice withInvoiceAddress(final Address invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
		return this;
	}

	public Set<String> getFacilityIds() {
		return facilityIds;
	}

	public void setFacilityIds(final Set<String> facilityIds) {
		this.facilityIds = facilityIds;
	}

	public Invoice withFacilityIds(final Set<String> facilityIds) {
		this.facilityIds = facilityIds;
		return this;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(final String creditorOrganizationNumber) {
		this.organizationNumber = creditorOrganizationNumber;
	}

	public Invoice withOrganizationNumber(final String creditorOrganizationNumber) {
		this.organizationNumber = creditorOrganizationNumber;
		return this;
	}

	public InvoiceOrigin getInvoiceOrigin() {
		return invoiceOrigin;
	}

	public void setInvoiceOrigin(final InvoiceOrigin invoiceOrigin) {
		this.invoiceOrigin = invoiceOrigin;
	}

	public Invoice withInvoiceOrigin(final InvoiceOrigin invoiceOrigin) {
		this.invoiceOrigin = invoiceOrigin;
		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		final Invoice invoice = (Invoice) o;
		return Float.compare(totalAmount, invoice.totalAmount) == 0 && Float.compare(amountVatIncluded, invoice.amountVatIncluded) == 0 && Float.compare(amountVatExcluded, invoice.amountVatExcluded) == 0
			&& Float.compare(vatEligibleAmount, invoice.vatEligibleAmount) == 0 && Float.compare(rounding, invoice.rounding) == 0 && Float.compare(vat, invoice.vat) == 0 && Objects.equals(dueDate, invoice.dueDate)
			&& Objects.equals(reversedVat, invoice.reversedVat) && Objects.equals(pdfAvailable, invoice.pdfAvailable) && Objects.equals(currency, invoice.currency) && Objects.equals(invoiceDate, invoice.invoiceDate)
			&& Objects.equals(fromDate, invoice.fromDate) && Objects.equals(toDate, invoice.toDate) && Objects.equals(invoiceNumber, invoice.invoiceNumber) && invoiceStatus == invoice.invoiceStatus && Objects.equals(
				ocrNumber, invoice.ocrNumber) && Objects.equals(organizationNumber, invoice.organizationNumber) && Objects.equals(invoiceName, invoice.invoiceName) && invoiceType == invoice.invoiceType && Objects.equals(
					invoiceDescriptions, invoice.invoiceDescriptions) && Objects.equals(invoiceAddress, invoice.invoiceAddress) && Objects.equals(facilityIds, invoice.facilityIds) && invoiceOrigin == invoice.invoiceOrigin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dueDate, totalAmount, amountVatIncluded, amountVatExcluded, vatEligibleAmount, rounding, vat, reversedVat, pdfAvailable, currency, invoiceDate, fromDate, toDate, invoiceNumber, invoiceStatus, ocrNumber, organizationNumber,
			invoiceName, invoiceType, invoiceDescriptions, invoiceAddress, facilityIds, invoiceOrigin);
	}

	@Override
	public String toString() {
		return "Invoice{" +
			"dueDate=" + dueDate +
			", totalAmount=" + totalAmount +
			", amountVatIncluded=" + amountVatIncluded +
			", amountVatExcluded=" + amountVatExcluded +
			", vatEligibleAmount=" + vatEligibleAmount +
			", rounding=" + rounding +
			", vat=" + vat +
			", reversedVat=" + reversedVat +
			", pdfAvailable=" + pdfAvailable +
			", currency='" + currency + '\'' +
			", invoiceDate=" + invoiceDate +
			", fromDate=" + fromDate +
			", toDate=" + toDate +
			", invoiceNumber='" + invoiceNumber + '\'' +
			", invoiceStatus=" + invoiceStatus +
			", ocrNumber='" + ocrNumber + '\'' +
			", organizationNumber='" + organizationNumber + '\'' +
			", invoiceName='" + invoiceName + '\'' +
			", invoiceType=" + invoiceType +
			", invoiceDescriptions=" + invoiceDescriptions +
			", invoiceAddress=" + invoiceAddress +
			", facilityIds=" + facilityIds +
			", invoiceOrigin=" + invoiceOrigin +
			'}';
	}
}

package se.sundsvall.invoices.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Customer invoice model")
public class CustomerInvoice {

	@Schema(description = "Customer number", examples = "123456")
	private String customerNumber;

	@Schema(implementation = CustomerType.class)
	private CustomerType customerType;

	@Schema(description = "Facility id", examples = "735999109425048010")
	private String facilityId;

	@Schema(description = "Invoice number", examples = "999")
	private String invoiceNumber;

	@Schema(description = "Invoice id", examples = "1062916396")
	private Long invoiceId;

	@Schema(description = "Joint invoice id", examples = "123")
	private Long jointInvoiceId;

	@Schema(description = "Invoice date", examples = "2025-10-08")
	private LocalDate invoiceDate;

	@Schema(description = "Invoice name", examples = "123456789.pdf")
	private String invoiceName;

	@Schema(implementation = InvoiceType.class)
	private InvoiceType invoiceType;

	@Schema(description = "Invoice description", examples = "El")
	private String invoiceDescription;

	@Schema(implementation = InvoiceStatus.class)
	private InvoiceStatus invoiceStatus;

	@Schema(description = "OCR number", examples = "295334999")
	private String ocrNumber;

	@Schema(description = "Due date", examples = "2025-10-28")
	private LocalDate dueDate;

	@Schema(description = "Invoice period start", examples = "2025-09-01")
	private LocalDate periodFrom;

	@Schema(description = "Invoice period end", examples = "2025-09-30")
	private LocalDate periodTo;

	@Schema(description = "Total amount", examples = "1234.00")
	private float totalAmount;

	@Schema(description = "Amount included VAT", examples = "1233.51")
	private float amountVatIncluded;

	@Schema(description = "Amount excluded VAT", examples = "986.81")
	private float amountVatExcluded;

	@Schema(description = "Amount eligible for VAT", examples = "986.81")
	private float vatEligibleAmount;

	@Schema(description = "Rounding", examples = "0.49")
	private float rounding;

	@Schema(description = "Organization group", examples = "stadsbacken")
	private String organizationGroup;

	@Schema(description = "Organization number of invoice issuer", examples = "5565027223")
	private String organizationNumber;

	@Schema(description = "Administration", examples = "Sundsvall Elnät")
	private String administration;

	@Schema(description = "Street", examples = "Ankeborgsvägen 11")
	private String street;

	@Schema(description = "Postal code", examples = "87654")
	private String postCode;

	@Schema(description = "City", examples = "Sundsvall")
	private String city;

	@Schema(description = "Care of address", examples = "Anka Kalle")
	private String careOf;

	@Schema(description = "Invoice reference")
	private String invoiceReference;

	@Schema(description = "Is pdf-version of invoice available", examples = "true")
	private Boolean pdfAvailable;

	@ArraySchema(schema = @Schema(implementation = InvoiceDetail.class))
	private List<InvoiceDetail> details;

	public static CustomerInvoice create() {
		return new CustomerInvoice();
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(final String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public CustomerInvoice withCustomerNumber(final String customerNumber) {
		this.customerNumber = customerNumber;
		return this;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(final CustomerType customerType) {
		this.customerType = customerType;
	}

	public CustomerInvoice withCustomerType(final CustomerType customerType) {
		this.customerType = customerType;
		return this;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(final String facilityId) {
		this.facilityId = facilityId;
	}

	public CustomerInvoice withFacilityId(final String facilityId) {
		this.facilityId = facilityId;
		return this;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(final String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public CustomerInvoice withInvoiceNumber(final String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
		return this;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(final Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public CustomerInvoice withInvoiceId(final Long invoiceId) {
		this.invoiceId = invoiceId;
		return this;
	}

	public Long getJointInvoiceId() {
		return jointInvoiceId;
	}

	public void setJointInvoiceId(final Long jointInvoiceId) {
		this.jointInvoiceId = jointInvoiceId;
	}

	public CustomerInvoice withJointInvoiceId(final Long jointInvoiceId) {
		this.jointInvoiceId = jointInvoiceId;
		return this;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(final LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public CustomerInvoice withInvoiceDate(final LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
		return this;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(final String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public CustomerInvoice withInvoiceName(final String invoiceName) {
		this.invoiceName = invoiceName;
		return this;
	}

	public InvoiceType getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(final InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}

	public CustomerInvoice withInvoiceType(final InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
		return this;
	}

	public String getInvoiceDescription() {
		return invoiceDescription;
	}

	public void setInvoiceDescription(final String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
	}

	public CustomerInvoice withInvoiceDescription(final String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
		return this;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(final InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public CustomerInvoice withInvoiceStatus(final InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
		return this;
	}

	public String getOcrNumber() {
		return ocrNumber;
	}

	public void setOcrNumber(final String ocrNumber) {
		this.ocrNumber = ocrNumber;
	}

	public CustomerInvoice withOcrNumber(final String ocrNumber) {
		this.ocrNumber = ocrNumber;
		return this;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(final LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public CustomerInvoice withDueDate(final LocalDate dueDate) {
		this.dueDate = dueDate;
		return this;
	}

	public LocalDate getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(final LocalDate periodFrom) {
		this.periodFrom = periodFrom;
	}

	public CustomerInvoice withPeriodFrom(final LocalDate periodFrom) {
		this.periodFrom = periodFrom;
		return this;
	}

	public LocalDate getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(final LocalDate periodTo) {
		this.periodTo = periodTo;
	}

	public CustomerInvoice withPeriodTo(final LocalDate periodTo) {
		this.periodTo = periodTo;
		return this;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(final float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public CustomerInvoice withTotalAmount(final float totalAmount) {
		this.totalAmount = totalAmount;
		return this;
	}

	public float getAmountVatIncluded() {
		return amountVatIncluded;
	}

	public void setAmountVatIncluded(final float amountVatIncluded) {
		this.amountVatIncluded = amountVatIncluded;
	}

	public CustomerInvoice withAmountVatIncluded(final float amountVatIncluded) {
		this.amountVatIncluded = amountVatIncluded;
		return this;
	}

	public float getAmountVatExcluded() {
		return amountVatExcluded;
	}

	public void setAmountVatExcluded(final float amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
	}

	public CustomerInvoice withAmountVatExcluded(final float amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
		return this;
	}

	public float getVatEligibleAmount() {
		return vatEligibleAmount;
	}

	public void setVatEligibleAmount(final float vatEligibleAmount) {
		this.vatEligibleAmount = vatEligibleAmount;
	}

	public CustomerInvoice withVatEligibleAmount(final float vatEligibleAmount) {
		this.vatEligibleAmount = vatEligibleAmount;
		return this;
	}

	public float getRounding() {
		return rounding;
	}

	public void setRounding(final float rounding) {
		this.rounding = rounding;
	}

	public CustomerInvoice withRounding(final float rounding) {
		this.rounding = rounding;
		return this;
	}

	public String getOrganizationGroup() {
		return organizationGroup;
	}

	public void setOrganizationGroup(final String organizationGroup) {
		this.organizationGroup = organizationGroup;
	}

	public CustomerInvoice withOrganizationGroup(final String organizationGroup) {
		this.organizationGroup = organizationGroup;
		return this;
	}

	public String getOrganizationNumber() {
		return organizationNumber;
	}

	public void setOrganizationNumber(final String organizationNumber) {
		this.organizationNumber = organizationNumber;
	}

	public CustomerInvoice withOrganizationNumber(final String organizationNumber) {
		this.organizationNumber = organizationNumber;
		return this;
	}

	public String getAdministration() {
		return administration;
	}

	public void setAdministration(final String administration) {
		this.administration = administration;
	}

	public CustomerInvoice withAdministration(final String administration) {
		this.administration = administration;
		return this;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public CustomerInvoice withStreet(final String street) {
		this.street = street;
		return this;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(final String postCode) {
		this.postCode = postCode;
	}

	public CustomerInvoice withPostCode(final String postCode) {
		this.postCode = postCode;
		return this;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public CustomerInvoice withCity(final String city) {
		this.city = city;
		return this;
	}

	public String getCareOf() {
		return careOf;
	}

	public void setCareOf(final String careOf) {
		this.careOf = careOf;
	}

	public CustomerInvoice withCareOf(final String careOf) {
		this.careOf = careOf;
		return this;
	}

	public String getInvoiceReference() {
		return invoiceReference;
	}

	public void setInvoiceReference(final String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}

	public CustomerInvoice withInvoiceReference(final String invoiceReference) {
		this.invoiceReference = invoiceReference;
		return this;
	}

	public Boolean getPdfAvailable() {
		return pdfAvailable;
	}

	public void setPdfAvailable(final Boolean pdfAvailable) {
		this.pdfAvailable = pdfAvailable;
	}

	public CustomerInvoice withPdfAvailable(final Boolean pdfAvailable) {
		this.pdfAvailable = pdfAvailable;
		return this;
	}

	public List<InvoiceDetail> getDetails() {
		return details;
	}

	public void setDetails(final List<InvoiceDetail> details) {
		this.details = details;
	}

	public CustomerInvoice withDetails(final List<InvoiceDetail> details) {
		this.details = details;
		return this;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		final CustomerInvoice that = (CustomerInvoice) o;
		return Float.compare(totalAmount, that.totalAmount) == 0 && Float.compare(amountVatIncluded, that.amountVatIncluded) == 0 && Float.compare(amountVatExcluded, that.amountVatExcluded) == 0
			&& Float.compare(vatEligibleAmount, that.vatEligibleAmount) == 0 && Float.compare(rounding, that.rounding) == 0 && Objects.equals(customerNumber, that.customerNumber) && customerType == that.customerType
			&& Objects.equals(facilityId, that.facilityId) && Objects.equals(invoiceNumber, that.invoiceNumber) && Objects.equals(invoiceId, that.invoiceId) && Objects.equals(jointInvoiceId, that.jointInvoiceId)
			&& Objects.equals(invoiceDate, that.invoiceDate) && Objects.equals(invoiceName, that.invoiceName) && invoiceType == that.invoiceType && Objects.equals(invoiceDescription, that.invoiceDescription)
			&& invoiceStatus == that.invoiceStatus && Objects.equals(ocrNumber, that.ocrNumber) && Objects.equals(dueDate, that.dueDate) && Objects.equals(periodFrom, that.periodFrom) && Objects.equals(periodTo, that.periodTo)
			&& Objects.equals(organizationGroup, that.organizationGroup) && Objects.equals(organizationNumber, that.organizationNumber) && Objects.equals(administration, that.administration) && Objects.equals(street, that.street)
			&& Objects.equals(postCode, that.postCode) && Objects.equals(city, that.city) && Objects.equals(careOf, that.careOf) && Objects.equals(invoiceReference, that.invoiceReference) && Objects.equals(pdfAvailable, that.pdfAvailable)
			&& Objects.equals(details, that.details);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerNumber, customerType, facilityId, invoiceNumber, invoiceId, jointInvoiceId, invoiceDate, invoiceName, invoiceType, invoiceDescription, invoiceStatus, ocrNumber, dueDate, periodFrom, periodTo, totalAmount,
			amountVatIncluded, amountVatExcluded, vatEligibleAmount, rounding, organizationGroup, organizationNumber, administration, street, postCode, city, careOf, invoiceReference, pdfAvailable, details);
	}

	@Override
	public String toString() {
		return "CustomerInvoice{" +
			"customerNumber='" + customerNumber + '\'' +
			", customerType=" + customerType +
			", facilityId='" + facilityId + '\'' +
			", invoiceNumber='" + invoiceNumber + '\'' +
			", invoiceId=" + invoiceId +
			", jointInvoiceId=" + jointInvoiceId +
			", invoiceDate=" + invoiceDate +
			", invoiceName='" + invoiceName + '\'' +
			", invoiceType=" + invoiceType +
			", invoiceDescription='" + invoiceDescription + '\'' +
			", invoiceStatus=" + invoiceStatus +
			", ocrNumber='" + ocrNumber + '\'' +
			", dueDate=" + dueDate +
			", periodFrom=" + periodFrom +
			", periodTo=" + periodTo +
			", totalAmount=" + totalAmount +
			", amountVatIncluded=" + amountVatIncluded +
			", amountVatExcluded=" + amountVatExcluded +
			", vatEligibleAmount=" + vatEligibleAmount +
			", rounding=" + rounding +
			", organizationGroup='" + organizationGroup + '\'' +
			", organizationNumber='" + organizationNumber + '\'' +
			", administration='" + administration + '\'' +
			", street='" + street + '\'' +
			", postCode='" + postCode + '\'' +
			", city='" + city + '\'' +
			", careOf='" + careOf + '\'' +
			", invoiceReference='" + invoiceReference + '\'' +
			", pdfAvailable=" + pdfAvailable +
			", details=" + details +
			'}';
	}
}

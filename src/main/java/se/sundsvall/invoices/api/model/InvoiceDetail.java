package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Objects;

@Schema(description = "Invoice-detail")
public class InvoiceDetail {

	@Schema(examples = "814.00", description = "Amount")
	private float amount;

	@Schema(examples = "651.20", description = "Invoice-amount excluding VAT")
	private float amountVatExcluded;

	@Schema(examples = "162.80", description = "VAT")
	private float vat;

	@Schema(examples = "25.00", description = "VAT-rate in percent")
	private float vatRate;

	@Schema(examples = "3.45", description = "Quantity of product")
	private float quantity;

	@Schema(examples = "kWh", description = "Unit in quantity")
	private String unit;

	@Schema(examples = "271.30", description = "Unit-price")
	private float unitPrice;

	@Schema(examples = "Förbrukning el", description = "Description of detail")
	private String description;

	@Schema(examples = "999", description = "Product code")
	private String productCode;

	@Schema(examples = "Elförbrukning", description = "Product name")
	private String productName;

	@Schema(examples = "2022-01-01", description = "Invoice-detail from-date")
	private LocalDate fromDate;

	@Schema(examples = "2022-01-31", description = "Invoice-detail to-date")
	private LocalDate toDate;

	@Schema(examples = "735999109151401011", description = "Facility id")
	private String facilityId;

	@Schema(examples = "Sundsvalls Energi AB", description = "Administration")
	private String administration;

	public static InvoiceDetail create() {
		return new InvoiceDetail();
	}

	public String getFacilityId() {
		return facilityId;
	}

	public InvoiceDetail withFacilityId(String facilityId) {
		this.facilityId = facilityId;
		return this;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getAdministration() {
		return administration;
	}

	public InvoiceDetail withAdministration(String administration) {
		this.administration = administration;
		return this;
	}

	public void setAdministration(String administration) {
		this.administration = administration;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(final float amount) {
		this.amount = amount;
	}

	public InvoiceDetail withAmount(final float amount) {
		this.amount = amount;
		return this;
	}

	public float getAmountVatExcluded() {
		return amountVatExcluded;
	}

	public void setAmountVatExcluded(final float amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
	}

	public InvoiceDetail withAmountVatExcluded(final float amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
		return this;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(final float vat) {
		this.vat = vat;
	}

	public InvoiceDetail withVat(final float vat) {
		this.vat = vat;
		return this;
	}

	public float getVatRate() {
		return vatRate;
	}

	public void setVatRate(final float vatRate) {
		this.vatRate = vatRate;
	}

	public InvoiceDetail withVatRate(final float vatRate) {
		this.vatRate = vatRate;
		return this;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(final float quantity) {
		this.quantity = quantity;
	}

	public InvoiceDetail withQuantity(final float quantity) {
		this.quantity = quantity;
		return this;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(final String unit) {
		this.unit = unit;
	}

	public InvoiceDetail withUnit(final String unit) {
		this.unit = unit;
		return this;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(final float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public InvoiceDetail withUnitPrice(final float unitPrice) {
		this.unitPrice = unitPrice;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public InvoiceDetail withDescription(final String description) {
		this.description = description;
		return this;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(final String productCode) {
		this.productCode = productCode;
	}

	public InvoiceDetail withProductCode(final String productCode) {
		this.productCode = productCode;
		return this;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(final String productName) {
		this.productName = productName;
	}

	public InvoiceDetail withProductName(final String productName) {
		this.productName = productName;
		return this;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(final LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public InvoiceDetail withFromDate(final LocalDate fromDate) {
		this.fromDate = fromDate;
		return this;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(final LocalDate toDate) {
		this.toDate = toDate;
	}

	public InvoiceDetail withToDate(final LocalDate toDate) {
		this.toDate = toDate;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		InvoiceDetail that = (InvoiceDetail) o;
		return Float.compare(amount, that.amount) == 0 && Float.compare(amountVatExcluded, that.amountVatExcluded) == 0 && Float.compare(vat, that.vat) == 0 && Float.compare(vatRate, that.vatRate) == 0
			&& Float.compare(quantity, that.quantity) == 0 && Float.compare(unitPrice, that.unitPrice) == 0 && Objects.equals(unit, that.unit) && Objects.equals(description, that.description) && Objects.equals(
				productCode, that.productCode) && Objects.equals(productName, that.productName) && Objects.equals(fromDate, that.fromDate) && Objects.equals(toDate, that.toDate) && Objects.equals(facilityId, that.facilityId)
			&& Objects.equals(administration, that.administration);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, amountVatExcluded, vat, vatRate, quantity, unit, unitPrice, description, productCode, productName, fromDate, toDate, facilityId, administration);
	}

	@Override
	public String toString() {
		return "InvoiceDetail{" +
			"amount=" + amount +
			", amountVatExcluded=" + amountVatExcluded +
			", vat=" + vat +
			", vatRate=" + vatRate +
			", quantity=" + quantity +
			", unit='" + unit + '\'' +
			", unitPrice=" + unitPrice +
			", description='" + description + '\'' +
			", productCode='" + productCode + '\'' +
			", productName='" + productName + '\'' +
			", fromDate=" + fromDate +
			", toDate=" + toDate +
			", facilityId='" + facilityId + '\'' +
			", administration='" + administration + '\'' +
			'}';
	}
}

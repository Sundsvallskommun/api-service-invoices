package se.sundsvall.invoices.api.model;

import java.time.LocalDate;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Invoice-detail")
public class InvoiceDetail {

	@Schema(example = "814.00", description = "Amount")
	private float amount;

	@Schema(example = "651.20", description = "Invoice-amount excluding VAT")
	private float amountVatExcluded;

	@Schema(example = "162.80", description = "VAT")
	private float vat;

	@Schema(example = "25.00", description = "VAT-rate in percent")
	private float vatRate;

	@Schema(example = "3.45", description = "Quantity of product")
	private float quantity;

	@Schema(example = "kWh", description = "Unit in quantity")
	private String unit;

	@Schema(example = "271.30", description = "Unit-price")
	private float unitPrice;

	@Schema(example = "Förbrukning el", description = "Description of detail")
	private String description;

	@Schema(example = "999", description = "Product code")
	private String productCode;

	@Schema(example = "Elförbrukning", description = "Product name")
	private String productName;

	@Schema(example = "2022-01-01", description = "Invoice-detail from-date")
	private LocalDate fromDate;

	@Schema(example = "2022-01-31", description = "Invoice-detail to-date")
	private LocalDate toDate;

	public static InvoiceDetail create() {
		return new InvoiceDetail();
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public InvoiceDetail withAmount(float amount) {
		this.amount = amount;
		return this;
	}

	public float getAmountVatExcluded() {
		return amountVatExcluded;
	}

	public void setAmountVatExcluded(float amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
	}

	public InvoiceDetail withAmountVatExcluded(float amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
		return this;
	}

	public float getVat() {
		return vat;
	}

	public void setVat(float vat) {
		this.vat = vat;
	}

	public InvoiceDetail withVat(float vat) {
		this.vat = vat;
		return this;
	}

	public float getVatRate() {
		return vatRate;
	}

	public void setVatRate(float vatRate) {
		this.vatRate = vatRate;
	}

	public InvoiceDetail withVatRate(float vatRate) {
		this.vatRate = vatRate;
		return this;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public InvoiceDetail withQuantity(float quantity) {
		this.quantity = quantity;
		return this;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public InvoiceDetail withUnit(String unit) {
		this.unit = unit;
		return this;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public InvoiceDetail withUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public InvoiceDetail withDescription(String description) {
		this.description = description;
		return this;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public InvoiceDetail withProductCode(String productCode) {
		this.productCode = productCode;
		return this;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public InvoiceDetail withProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public InvoiceDetail withFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
		return this;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public InvoiceDetail withToDate(LocalDate toDate) {
		this.toDate = toDate;
		return this;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		InvoiceDetail detail = (InvoiceDetail) o;
		return Objects.equals(this.amount, detail.amount) &&
			Objects.equals(this.amountVatExcluded, detail.amountVatExcluded) &&
			Objects.equals(this.vat, detail.vat) &&
			Objects.equals(this.vatRate, detail.vatRate) &&
			Objects.equals(this.quantity, detail.quantity) &&
			Objects.equals(this.unit, detail.unit) &&
			Objects.equals(this.unitPrice, detail.unitPrice) &&
			Objects.equals(this.description, detail.description) &&
			Objects.equals(this.productCode, detail.productCode) &&
			Objects.equals(this.productName, detail.productName) &&
			Objects.equals(this.fromDate, detail.fromDate) &&
			Objects.equals(this.toDate, detail.toDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, amountVatExcluded, vat, vatRate, quantity, unit, unitPrice, description, productCode, productName, fromDate, toDate);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceDetailsResponseDetails [amount=").append(amount).append(", amountVatExcluded=").append(amountVatExcluded)
			.append(", vat=").append(vat).append(", vatRate=").append(vatRate).append(", quantity=").append(quantity)
			.append(", unit=").append(unit).append(", unitPrice=").append(unitPrice).append(", description=").append(description)
			.append(", productCode=").append(productCode).append(", productName=").append(productName)
			.append(", fromDate=").append(fromDate).append(", toDate=").append(toDate).append("]");
		return builder.toString();
	}
}

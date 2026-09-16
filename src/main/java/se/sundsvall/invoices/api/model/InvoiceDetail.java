package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Schema(description = "Invoice-detail")
public class InvoiceDetail {

	@Schema(examples = "814.00", description = "Amount")
	private BigDecimal amount;

	@Schema(examples = "651.20", description = "Invoice-amount excluding VAT")
	private BigDecimal amountVatExcluded;

	@Schema(examples = "162.80", description = "VAT")
	private BigDecimal vat;

	@Schema(examples = "25.00", description = "VAT-rate in percent")
	private BigDecimal vatRate;

	@Schema(examples = "3.45", description = "Quantity of product")
	private BigDecimal quantity;

	@Schema(examples = "kWh", description = "Unit in quantity")
	private String unit;

	@Schema(examples = "271.30", description = "Unit-price")
	private BigDecimal unitPrice;

	@Schema(examples = "217.04", description = "Unit-price excluding VAT")
	private BigDecimal unitPriceVatExcluded;

	@Schema(examples = "27130.00", description = "Unit-price as presented on the invoice, in the currency and unit given by invoiceUnitPriceCurrency and invoiceUnitPriceUnit")
	private BigDecimal invoiceUnitPrice;

	@Schema(examples = "21704.00", description = "Unit-price excluding VAT as presented on the invoice, in the currency and unit given by invoiceUnitPriceCurrency and invoiceUnitPriceUnit")
	private BigDecimal invoiceUnitPriceVatExcluded;

	@Schema(examples = "öre", description = "Currency that the invoice unit-prices are expressed in")
	private String invoiceUnitPriceCurrency;

	@Schema(examples = "kWh", description = "Unit that the invoice unit-prices are expressed per")
	private String invoiceUnitPriceUnit;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public InvoiceDetail withAmount(final BigDecimal amount) {
		this.amount = amount;
		return this;
	}

	public BigDecimal getAmountVatExcluded() {
		return amountVatExcluded;
	}

	public void setAmountVatExcluded(final BigDecimal amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
	}

	public InvoiceDetail withAmountVatExcluded(final BigDecimal amountVatExcluded) {
		this.amountVatExcluded = amountVatExcluded;
		return this;
	}

	public BigDecimal getVat() {
		return vat;
	}

	public void setVat(final BigDecimal vat) {
		this.vat = vat;
	}

	public InvoiceDetail withVat(final BigDecimal vat) {
		this.vat = vat;
		return this;
	}

	public BigDecimal getVatRate() {
		return vatRate;
	}

	public void setVatRate(final BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	public InvoiceDetail withVatRate(final BigDecimal vatRate) {
		this.vatRate = vatRate;
		return this;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

	public InvoiceDetail withQuantity(final BigDecimal quantity) {
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

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(final BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public InvoiceDetail withUnitPrice(final BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
		return this;
	}

	public BigDecimal getUnitPriceVatExcluded() {
		return unitPriceVatExcluded;
	}

	public void setUnitPriceVatExcluded(final BigDecimal unitPriceVatExcluded) {
		this.unitPriceVatExcluded = unitPriceVatExcluded;
	}

	public InvoiceDetail withUnitPriceVatExcluded(final BigDecimal unitPriceVatExcluded) {
		this.unitPriceVatExcluded = unitPriceVatExcluded;
		return this;
	}

	public BigDecimal getInvoiceUnitPrice() {
		return invoiceUnitPrice;
	}

	public void setInvoiceUnitPrice(final BigDecimal invoiceUnitPrice) {
		this.invoiceUnitPrice = invoiceUnitPrice;
	}

	public InvoiceDetail withInvoiceUnitPrice(final BigDecimal invoiceUnitPrice) {
		this.invoiceUnitPrice = invoiceUnitPrice;
		return this;
	}

	public BigDecimal getInvoiceUnitPriceVatExcluded() {
		return invoiceUnitPriceVatExcluded;
	}

	public void setInvoiceUnitPriceVatExcluded(final BigDecimal invoiceUnitPriceVatExcluded) {
		this.invoiceUnitPriceVatExcluded = invoiceUnitPriceVatExcluded;
	}

	public InvoiceDetail withInvoiceUnitPriceVatExcluded(final BigDecimal invoiceUnitPriceVatExcluded) {
		this.invoiceUnitPriceVatExcluded = invoiceUnitPriceVatExcluded;
		return this;
	}

	public String getInvoiceUnitPriceCurrency() {
		return invoiceUnitPriceCurrency;
	}

	public void setInvoiceUnitPriceCurrency(final String invoiceUnitPriceCurrency) {
		this.invoiceUnitPriceCurrency = invoiceUnitPriceCurrency;
	}

	public InvoiceDetail withInvoiceUnitPriceCurrency(final String invoiceUnitPriceCurrency) {
		this.invoiceUnitPriceCurrency = invoiceUnitPriceCurrency;
		return this;
	}

	public String getInvoiceUnitPriceUnit() {
		return invoiceUnitPriceUnit;
	}

	public void setInvoiceUnitPriceUnit(final String invoiceUnitPriceUnit) {
		this.invoiceUnitPriceUnit = invoiceUnitPriceUnit;
	}

	public InvoiceDetail withInvoiceUnitPriceUnit(final String invoiceUnitPriceUnit) {
		this.invoiceUnitPriceUnit = invoiceUnitPriceUnit;
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
		return Objects.equals(amount, that.amount) && Objects.equals(amountVatExcluded, that.amountVatExcluded) && Objects.equals(vat, that.vat) && Objects.equals(vatRate, that.vatRate)
			&& Objects.equals(quantity, that.quantity) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(unitPriceVatExcluded, that.unitPriceVatExcluded) && Objects.equals(invoiceUnitPrice, that.invoiceUnitPrice)
			&& Objects.equals(invoiceUnitPriceVatExcluded, that.invoiceUnitPriceVatExcluded) && Objects.equals(invoiceUnitPriceCurrency, that.invoiceUnitPriceCurrency) && Objects.equals(invoiceUnitPriceUnit, that.invoiceUnitPriceUnit)
			&& Objects.equals(unit, that.unit) && Objects.equals(description, that.description) && Objects.equals(
				productCode, that.productCode) && Objects.equals(productName, that.productName) && Objects.equals(fromDate, that.fromDate) && Objects.equals(toDate, that.toDate) && Objects.equals(facilityId, that.facilityId)
			&& Objects.equals(administration, that.administration);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, amountVatExcluded, vat, vatRate, quantity, unit, unitPrice, unitPriceVatExcluded, invoiceUnitPrice, invoiceUnitPriceVatExcluded, invoiceUnitPriceCurrency, invoiceUnitPriceUnit, description,
			productCode, productName, fromDate, toDate, facilityId, administration);
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
			", unitPriceVatExcluded=" + unitPriceVatExcluded +
			", invoiceUnitPrice=" + invoiceUnitPrice +
			", invoiceUnitPriceVatExcluded=" + invoiceUnitPriceVatExcluded +
			", invoiceUnitPriceCurrency='" + invoiceUnitPriceCurrency + '\'' +
			", invoiceUnitPriceUnit='" + invoiceUnitPriceUnit + '\'' +
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

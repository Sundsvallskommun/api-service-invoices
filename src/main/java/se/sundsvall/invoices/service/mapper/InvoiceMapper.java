package se.sundsvall.invoices.service.mapper;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static se.sundsvall.invoices.api.model.InvoiceOrigin.COMMERCIAL;
import static se.sundsvall.invoices.api.model.InvoiceOrigin.PUBLIC_ADMINISTRATION;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import generated.se.sundsvall.datawarehousereader.Direction;
import generated.se.sundsvall.datawarehousereader.InvoiceParameters;
import generated.se.sundsvall.invoicecache.InvoiceRequest;
import se.sundsvall.invoices.api.model.Address;
import se.sundsvall.invoices.api.model.Invoice;
import se.sundsvall.invoices.api.model.InvoiceDetail;
import se.sundsvall.invoices.api.model.InvoiceStatus;
import se.sundsvall.invoices.api.model.InvoiceType;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.api.model.MetaData;

public class InvoiceMapper {

	private InvoiceMapper() {}

	/***************************************************************
	 * DATAWAREHOUSEREADER (i.e.commercial) MAPPING
	 ***************************************************************/

	public static InvoicesResponse toInvoicesResponse(generated.se.sundsvall.datawarehousereader.InvoiceResponse dataWarehouseReaderInvoiceResponse) {
		return InvoicesResponse.create()
			.withMetaData(toMetaData(dataWarehouseReaderInvoiceResponse.getMeta()))
			.withInvoices(toInvoicesFromDatawarehouseReader(dataWarehouseReaderInvoiceResponse.getInvoices()));
	}

	public static generated.se.sundsvall.datawarehousereader.InvoiceParameters toDataWarehouseReaderInvoiceParameters(List<String> customerNumbers, InvoicesParameters invoiceParameters) {
		return new InvoiceParameters()
			.customerNumber(customerNumbers)
			.facilityId(invoiceParameters.getFacilityId())
			.invoiceName(invoiceParameters.getInvoiceName())
			.invoiceNumber(toLong(invoiceParameters.getInvoiceNumber()))
			.organizationGroup(invoiceParameters.getOrganizationGroup())
			.organizationNumber(invoiceParameters.getOrganizationNumber())
			.invoiceDateFrom(invoiceParameters.getInvoiceDateFrom())
			.invoiceDateTo(invoiceParameters.getInvoiceDateTo())
			.dueDateFrom(invoiceParameters.getDueDateFrom())
			.dueDateTo(invoiceParameters.getDueDateTo())
			.invoiceType(toDataWarehouseReaderInvoiceType(invoiceParameters.getInvoiceType()))
			.invoiceStatus(toDataWarehouseReaderInvoiceStatus(invoiceParameters.getInvoiceStatus()))
			.ocrNumber(toLong(invoiceParameters.getOcrNumber()))
			.page(invoiceParameters.getPage())
			.limit(invoiceParameters.getLimit())
			.sortBy(List.of("invoiceDate"))
			.sortDirection(Direction.DESC);
	}

	public static List<InvoiceDetail> toInvoiceDetails(List<generated.se.sundsvall.datawarehousereader.InvoiceDetail> dataWarehouseReaderInvoiceDetails) {
		return ofNullable(dataWarehouseReaderInvoiceDetails).orElse(emptyList()).stream()
			.map(InvoiceMapper::toInvoiceDetail)
			.toList();
	}

	private static List<Invoice> toInvoicesFromDatawarehouseReader(List<generated.se.sundsvall.datawarehousereader.Invoice> dataWarehouseReaderInvoices) {
		return ofNullable(dataWarehouseReaderInvoices).orElse(emptyList()).stream()
			.map(InvoiceMapper::toInvoice)
			.toList();
	}

	private static Invoice toInvoice(generated.se.sundsvall.datawarehousereader.Invoice dataWarehouseReaderInvoice) {
		return Invoice.create().withDueDate(dataWarehouseReaderInvoice.getDueDate())
			.withTotalAmount(ofNullable(dataWarehouseReaderInvoice.getTotalAmount()).orElse(ZERO).floatValue())
			.withAmountVatIncluded(ofNullable(dataWarehouseReaderInvoice.getAmountVatIncluded()).orElse(ZERO).floatValue())
			.withAmountVatExcluded(ofNullable(dataWarehouseReaderInvoice.getAmountVatExcluded()).orElse(ZERO).floatValue())
			.withVatEligibleAmount(ofNullable(dataWarehouseReaderInvoice.getVatEligibleAmount()).orElse(ZERO).floatValue())
			.withRounding(ofNullable(dataWarehouseReaderInvoice.getRounding()).orElse(ZERO).floatValue())
			.withVat(ofNullable(dataWarehouseReaderInvoice.getVat()).orElse(ZERO).floatValue())
			.withReversedVat(dataWarehouseReaderInvoice.getReversedVat())
			.withCurrency(dataWarehouseReaderInvoice.getCurrency())
			.withInvoiceDate(dataWarehouseReaderInvoice.getInvoiceDate())
			.withInvoiceNumber(toString(dataWarehouseReaderInvoice.getInvoiceNumber()))
			.withInvoiceStatus(toInvoiceStatus(dataWarehouseReaderInvoice.getInvoiceStatus()))
			.withOcrNumber(toString(dataWarehouseReaderInvoice.getOcrNumber()))
			.withOrganizationNumber(dataWarehouseReaderInvoice.getOrganizationNumber())
			.withInvoiceName(dataWarehouseReaderInvoice.getInvoiceName())
			.withInvoiceType(toInvoiceType(dataWarehouseReaderInvoice.getInvoiceType()))
			.withInvoiceDescription(dataWarehouseReaderInvoice.getInvoiceDescription())
			.withFacilityId(dataWarehouseReaderInvoice.getFacilityId())
			.withPdfAvailable(dataWarehouseReaderInvoice.getPdfAvailable())
			.withInvoiceAddress(toAddress(dataWarehouseReaderInvoice))
			.withInvoiceOrigin(COMMERCIAL);
	}

	private static InvoiceDetail toInvoiceDetail(generated.se.sundsvall.datawarehousereader.InvoiceDetail dataWarehouseReaderInvoiceDetail) {
		return InvoiceDetail.create().withAmount(ofNullable(dataWarehouseReaderInvoiceDetail.getAmount()).orElse(ZERO).floatValue())
			.withAmountVatExcluded(ofNullable(dataWarehouseReaderInvoiceDetail.getAmountVatExcluded()).orElse(ZERO).floatValue())
			.withVat(ofNullable(dataWarehouseReaderInvoiceDetail.getVat()).orElse(ZERO).floatValue())
			.withUnitPrice(ofNullable(dataWarehouseReaderInvoiceDetail.getUnitPrice()).orElse(ZERO).floatValue())
			.withVatRate(ofNullable(dataWarehouseReaderInvoiceDetail.getVatRate()).orElse(0D).floatValue())
			.withUnit(dataWarehouseReaderInvoiceDetail.getUnit())
			.withUnitPrice(ofNullable(dataWarehouseReaderInvoiceDetail.getUnitPrice()).orElse(BigDecimal.valueOf(0)).floatValue())
			.withDescription(dataWarehouseReaderInvoiceDetail.getDescription())
			.withFromDate(toLocalDate(dataWarehouseReaderInvoiceDetail.getPeriodFrom()))
			.withToDate(toLocalDate(dataWarehouseReaderInvoiceDetail.getPeriodTo()))
			.withProductCode(String.valueOf(dataWarehouseReaderInvoiceDetail.getProductCode()))
			.withProductName(dataWarehouseReaderInvoiceDetail.getProductName())
			.withQuantity(ofNullable(dataWarehouseReaderInvoiceDetail.getQuantity()).orElse(0D).floatValue());
	}

	private static MetaData toMetaData(generated.se.sundsvall.datawarehousereader.MetaData dataWarehouseReaderMetaData) {
		return MetaData.create().withCount(dataWarehouseReaderMetaData.getCount())
			.withLimit(dataWarehouseReaderMetaData.getLimit())
			.withTotalPages(dataWarehouseReaderMetaData.getTotalPages())
			.withTotalRecords(dataWarehouseReaderMetaData.getTotalRecords())
			.withPage(dataWarehouseReaderMetaData.getPage());
	}

	private static Address toAddress(generated.se.sundsvall.datawarehousereader.Invoice dataWarehouseReaderInvoice) {
		return Address.create().withStreet(dataWarehouseReaderInvoice.getStreet())
			.withCareOf(dataWarehouseReaderInvoice.getCareOf())
			.withCity(dataWarehouseReaderInvoice.getCity())
			.withPostcode(dataWarehouseReaderInvoice.getPostCode());
	}

	static InvoiceStatus toInvoiceStatus(String dataWarehouseReaderInvoiceStatus) {
		return Optional.ofNullable(dataWarehouseReaderInvoiceStatus)
			.map(invoiceStatus -> switch (invoiceStatus) {
				case "Betalad" -> InvoiceStatus.PAID;
				case "Krediterad" -> InvoiceStatus.CREDITED;
				case "Inkasso" -> InvoiceStatus.DEBT_COLLECTION;
				case "Påminnelse" -> InvoiceStatus.REMINDER;
				case "Avskriven" -> InvoiceStatus.WRITTEN_OFF;
				case "Skickad" -> InvoiceStatus.SENT;
				default -> InvoiceStatus.UNKNOWN;
			})
			.orElse(null);
	}

	static InvoiceType toInvoiceType(String dataWarehouseReaderInvoiceType) {
		return Optional.ofNullable(dataWarehouseReaderInvoiceType)
			.map(invoiceType -> switch (invoiceType) {
				case "Faktura" -> InvoiceType.NORMAL;
				case "Kreditfaktura" -> InvoiceType.CREDIT;
				case "Startfaktura" -> InvoiceType.START;
				case "Slutfaktura" -> InvoiceType.STOP;
				default -> InvoiceType.UNKNOWN;
			})
			.orElse(null);
	}

	static String toDataWarehouseReaderInvoiceStatus(InvoiceStatus invoiceStatus) {
		return ofNullable(invoiceStatus)
			.map(status -> switch (status) {
				case PAID -> "Betalad";
				case CREDITED -> "Krediterad";
				case DEBT_COLLECTION -> "Inkasso";
				case WRITTEN_OFF -> "Avskriven";
				case SENT -> "Skickad";
				case REMINDER -> "Påminnelse";
				case UNKNOWN -> null;
				default -> null;
			})
			.orElse(null);
	}

	static String toDataWarehouseReaderInvoiceType(InvoiceType invoiceType) {
		return ofNullable(invoiceType)
			.map(type -> switch (type) {
				case NORMAL -> "Faktura";
				case CREDIT -> "Kreditfaktura";
				case START -> "Startfaktura";
				case STOP -> "Slutfaktura";
				case UNKNOWN -> null;
			})
			.orElse(null);
	}

	/***************************************************************
	 * INVOICECACHE (i.e. public administration) MAPPING
	 ***************************************************************/

	public static InvoicesResponse toInvoicesResponse(generated.se.sundsvall.invoicecache.InvoicesResponse invoiceCacheInvoiceResponse) {
		return InvoicesResponse.create()
			.withMetaData(toMetaData(invoiceCacheInvoiceResponse.getMeta()))
			.withInvoices(toInvoicesFromInvoiceCache(invoiceCacheInvoiceResponse.getInvoices()));
	}

	public static generated.se.sundsvall.invoicecache.InvoiceRequest toInvoiceCacheParameters(InvoicesParameters invoiceParameters) {
		return new InvoiceRequest()
			.invoiceNumbers(Optional.ofNullable(invoiceParameters.getInvoiceNumber()).stream().toList())
			.invoiceDateFrom(invoiceParameters.getInvoiceDateFrom())
			.invoiceDateTo(invoiceParameters.getInvoiceDateTo())
			.dueDateFrom(invoiceParameters.getDueDateFrom())
			.dueDateTo(invoiceParameters.getDueDateTo())
			.partyIds(invoiceParameters.getPartyId())
			.ocrNumber(invoiceParameters.getOcrNumber())
			.page(invoiceParameters.getPage())
			.limit(invoiceParameters.getLimit());
	}

	private static List<Invoice> toInvoicesFromInvoiceCache(List<generated.se.sundsvall.invoicecache.Invoice> invoiceCacheInvoices) {
		return ofNullable(invoiceCacheInvoices).orElse(emptyList()).stream()
			.map(InvoiceMapper::toInvoice)
			.toList();
	}

	private static Invoice toInvoice(generated.se.sundsvall.invoicecache.Invoice invoiceCacheInvoice) {
		return Invoice.create()
			.withCurrency("SEK")
			.withDueDate(invoiceCacheInvoice.getInvoiceDueDate())
			.withTotalAmount(ofNullable(invoiceCacheInvoice.getTotalAmount()).orElse(ZERO).floatValue())
			.withAmountVatIncluded(ofNullable(invoiceCacheInvoice.getAmountVatExcluded()).orElse(ZERO).floatValue() + ofNullable(invoiceCacheInvoice.getVat()).orElse(ZERO).floatValue())
			.withAmountVatExcluded(ofNullable(invoiceCacheInvoice.getAmountVatExcluded()).orElse(ZERO).floatValue())
			.withVat(ofNullable(invoiceCacheInvoice.getVat()).orElse(ZERO).floatValue())
			.withInvoiceDate(invoiceCacheInvoice.getInvoiceDate())
			.withInvoiceDescription(invoiceCacheInvoice.getInvoiceDescription())
			.withInvoiceName(invoiceCacheInvoice.getInvoiceName())
			.withInvoiceNumber(invoiceCacheInvoice.getInvoiceNumber())
			.withOcrNumber(invoiceCacheInvoice.getOcrNumber())
			.withInvoiceStatus(toInvoiceStatus(invoiceCacheInvoice.getInvoiceStatus()))
			.withInvoiceType(toInvoiceType(invoiceCacheInvoice.getInvoiceType()))
			.withInvoiceAddress(toAddress(invoiceCacheInvoice))
			.withInvoiceOrigin(PUBLIC_ADMINISTRATION);
	}

	private static Address toAddress(generated.se.sundsvall.invoicecache.Invoice invoiceCacheInvoice) {
		return Optional.ofNullable(invoiceCacheInvoice.getInvoiceAddress())
			.map(invoiceAddress -> Address.create()
				.withCareOf(invoiceAddress.getCareOf())
				.withCity(invoiceAddress.getCity())
				.withPostcode(invoiceAddress.getPostcode())
				.withStreet(invoiceAddress.getStreet()))
			.orElse(null);
	}

	static InvoiceStatus toInvoiceStatus(generated.se.sundsvall.invoicecache.Invoice.InvoiceStatusEnum invoiceStatusEnum) {
		return Optional.ofNullable(invoiceStatusEnum)
			.map(invoiceStatus -> switch (invoiceStatus) {
				case PAID -> InvoiceStatus.PAID;
				case UNPAID -> InvoiceStatus.SENT;
				case PARTIALLY_PAID -> InvoiceStatus.PARTIALLY_PAID;
				case DEBT_COLLECTION -> InvoiceStatus.DEBT_COLLECTION;
				case PAID_TOO_MUCH -> InvoiceStatus.PAID_TOO_MUCH;
				case UNKNOWN -> null;
			})
			.orElse(null);
	}

	static InvoiceType toInvoiceType(generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum invoiceTypeEnum) {
		return Optional.ofNullable(invoiceTypeEnum)
			.map(invoiceType -> switch (invoiceType) {
				case NORMAL -> InvoiceType.NORMAL;
				case CREDIT -> InvoiceType.CREDIT;
			})
			.orElse(null);
	}

	private static MetaData toMetaData(generated.se.sundsvall.invoicecache.MetaData invoiceCacheMetaData) {
		return MetaData.create().withCount(invoiceCacheMetaData.getCount())
			.withLimit(invoiceCacheMetaData.getLimit())
			.withTotalPages(invoiceCacheMetaData.getTotalPages())
			.withTotalRecords(invoiceCacheMetaData.getTotalRecords())
			.withPage(invoiceCacheMetaData.getPage());
	}

	/*********************************
	 * COMMON MAPPING
	 *********************************/

	private static String toString(Long value) {
		return ofNullable(value)
			.map(String::valueOf)
			.orElse(null);
	}

	private static Long toLong(String value) {
		return ofNullable(value)
			.map(Long::parseLong)
			.orElse(null);
	}

	private static LocalDate toLocalDate(String value) {
		return ofNullable(value)
			.map(LocalDate::parse)
			.orElse(null);
	}
}

package se.sundsvall.invoices.service.mapper;

import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static se.sundsvall.invoices.api.model.InvoiceOrigin.COMMERCIAL;
import static se.sundsvall.invoices.api.model.InvoiceOrigin.PUBLIC_ADMINISTRATION;

import generated.se.sundsvall.datawarehousereader.Direction;
import generated.se.sundsvall.datawarehousereader.InvoiceParameters;
import generated.se.sundsvall.datawarehousereader.InvoiceResponse;
import generated.se.sundsvall.invoicecache.Invoice.InvoiceStatusEnum;
import generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum;
import generated.se.sundsvall.invoicecache.InvoiceFilterRequest;
import generated.se.sundsvall.invoicecache.InvoicePdf;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.Optional;
import se.sundsvall.invoices.api.model.Address;
import se.sundsvall.invoices.api.model.Invoice;
import se.sundsvall.invoices.api.model.InvoiceDetail;
import se.sundsvall.invoices.api.model.InvoiceStatus;
import se.sundsvall.invoices.api.model.InvoiceType;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.api.model.MetaData;
import se.sundsvall.invoices.api.model.PdfInvoice;

public class InvoiceMapper {

	private static final Decoder DECODER = Base64.getDecoder();

	private InvoiceMapper() {}

	/***************************************************************
	 * DATAWAREHOUSEREADER (i.e.commercial) MAPPING
	 ***************************************************************/

	public static InvoicesResponse toInvoicesResponse(final InvoiceResponse dataWarehouseReaderInvoiceResponse) {
		return InvoicesResponse.create()
			.withMetaData(toMetaData(dataWarehouseReaderInvoiceResponse.getMeta()))
			.withInvoices(toInvoicesFromDatawarehouseReader(dataWarehouseReaderInvoiceResponse.getInvoices()));
	}

	public static InvoiceParameters toDataWarehouseReaderInvoiceParameters(final List<String> customerNumbers, final InvoicesParameters invoiceParameters) {
		return new InvoiceParameters()
			.customerNumber(customerNumbers)
			.facilityIds(invoiceParameters.getFacilityId())
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

	public static List<InvoiceDetail> toInvoiceDetails(final List<generated.se.sundsvall.datawarehousereader.InvoiceDetail> dataWarehouseReaderInvoiceDetails) {
		return ofNullable(dataWarehouseReaderInvoiceDetails).orElse(emptyList()).stream()
			.map(InvoiceMapper::toInvoiceDetail)
			.toList();
	}

	private static List<Invoice> toInvoicesFromDatawarehouseReader(final List<generated.se.sundsvall.datawarehousereader.Invoice> dataWarehouseReaderInvoices) {
		return ofNullable(dataWarehouseReaderInvoices).orElse(emptyList()).stream()
			.map(InvoiceMapper::toInvoice)
			.toList();
	}

	private static Invoice toInvoice(final generated.se.sundsvall.datawarehousereader.Invoice dataWarehouseReaderInvoice) {
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
			.withInvoiceDescription(Optional.ofNullable(dataWarehouseReaderInvoice.getInvoiceDescriptions())
				.flatMap(list -> list.stream().findFirst())
				.orElse(null))
			.withFacilityId(Optional.ofNullable(dataWarehouseReaderInvoice.getFacilityIds())
				.flatMap(list -> list.stream().findFirst())
				.orElse(null))
			.withPdfAvailable(dataWarehouseReaderInvoice.getPdfAvailable())
			.withInvoiceAddress(toAddress(dataWarehouseReaderInvoice))
			.withInvoiceOrigin(COMMERCIAL);
	}

	private static InvoiceDetail toInvoiceDetail(final generated.se.sundsvall.datawarehousereader.InvoiceDetail dataWarehouseReaderInvoiceDetail) {
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

	private static MetaData toMetaData(final generated.se.sundsvall.datawarehousereader.PagingAndSortingMetaData pagingAndSortingMetaData) {
		return MetaData.create().withCount(pagingAndSortingMetaData.getCount())
			.withLimit(pagingAndSortingMetaData.getLimit())
			.withTotalPages(pagingAndSortingMetaData.getTotalPages())
			.withTotalRecords(pagingAndSortingMetaData.getTotalRecords())
			.withPage(pagingAndSortingMetaData.getPage());
	}

	private static Address toAddress(final generated.se.sundsvall.datawarehousereader.Invoice dataWarehouseReaderInvoice) {
		return Address.create().withStreet(dataWarehouseReaderInvoice.getStreet())
			.withCareOf(dataWarehouseReaderInvoice.getCareOf())
			.withCity(dataWarehouseReaderInvoice.getCity())
			.withPostcode(dataWarehouseReaderInvoice.getPostCode());
	}

	static InvoiceStatus toInvoiceStatus(final String dataWarehouseReaderInvoiceStatus) {
		return Optional.ofNullable(dataWarehouseReaderInvoiceStatus)
			.map(invoiceStatus -> switch (invoiceStatus)
			{
				case "Betalad" -> InvoiceStatus.PAID;
				case "Krediterad" -> InvoiceStatus.CREDITED;
				case "Inkasso" -> InvoiceStatus.DEBT_COLLECTION;
				case "Påminnelse" -> InvoiceStatus.REMINDER;
				case "Avskriven" -> InvoiceStatus.WRITTEN_OFF;
				case "Skickad" -> InvoiceStatus.SENT;
				case "Makulerad" -> InvoiceStatus.VOID;
				default -> InvoiceStatus.UNKNOWN;
			})
			.orElse(null);
	}

	static InvoiceType toInvoiceType(final String dataWarehouseReaderInvoiceType) {
		return Optional.ofNullable(dataWarehouseReaderInvoiceType)
			.map(invoiceType -> switch (invoiceType)
			{
				case "Faktura" -> InvoiceType.INVOICE;
				case "Kreditfaktura" -> InvoiceType.CREDIT_INVOICE;
				case "Startfaktura" -> InvoiceType.START_INVOICE;
				case "Slutfaktura" -> InvoiceType.FINAL_INVOICE;
				case "Kvittning" -> InvoiceType.OFFSET_INVOICE;
				case "Internfaktura" -> InvoiceType.INTERNAL_INVOICE;
				case "Samlingsfaktura" -> InvoiceType.CONSOLIDATED_INVOICE;
				default -> InvoiceType.UNKNOWN;
			})
			.orElse(null);
	}

	static String toDataWarehouseReaderInvoiceStatus(final InvoiceStatus invoiceStatus) {
		return ofNullable(invoiceStatus)
			.map(status -> switch (status)
			{
				case PAID -> "Betalad";
				case CREDITED -> "Krediterad";
				case DEBT_COLLECTION -> "Inkasso";
				case WRITTEN_OFF -> "Avskriven";
				case SENT -> "Skickad";
				case REMINDER -> "Påminnelse";
				case VOID -> "Makulerad";
				default -> null;
			})
			.orElse(null);
	}

	static String toDataWarehouseReaderInvoiceType(final InvoiceType invoiceType) {
		return ofNullable(invoiceType)
			.map(type -> switch (type)
			{
				case INVOICE -> "Faktura";
				case CREDIT_INVOICE -> "Kreditfaktura";
				case START_INVOICE -> "Startfaktura";
				case FINAL_INVOICE -> "Slutfaktura";
				case OFFSET_INVOICE -> "Kvittning";
				case INTERNAL_INVOICE -> "Internfaktura";
				case CONSOLIDATED_INVOICE -> "Samlingsfaktura";
				default -> null;
			})
			.orElse(null);
	}

	/***************************************************************
	 * INVOICECACHE (i.e. public administration) MAPPING
	 ***************************************************************/

	public static InvoiceTypeEnum toInvoiceCacheInvoiceType(final InvoiceType invoiceType) {
		return Optional.ofNullable(invoiceType)
			.map(type -> switch (type)
			{
				case INVOICE -> InvoiceTypeEnum.INVOICE;
				case CREDIT_INVOICE -> InvoiceTypeEnum.CREDIT_INVOICE;
				case FINAL_INVOICE -> InvoiceTypeEnum.FINAL_INVOICE;
				case DIRECT_DEBIT -> InvoiceTypeEnum.DIRECT_DEBIT;
				case SELF_INVOICE -> InvoiceTypeEnum.SELF_INVOICE;
				case REMINDER -> InvoiceTypeEnum.REMINDER;
				case CONSOLIDATED_INVOICE -> InvoiceTypeEnum.CONSOLIDATED_INVOICE;
				default -> null;
			}).orElse(null);
	}

	public static InvoicesResponse toInvoicesResponse(final generated.se.sundsvall.invoicecache.InvoicesResponse invoiceCacheInvoiceResponse) {
		return InvoicesResponse.create()
			.withMetaData(toMetaData(invoiceCacheInvoiceResponse.getMeta()))
			.withInvoices(toInvoicesFromInvoiceCache(invoiceCacheInvoiceResponse.getInvoices()));
	}

	public static InvoiceFilterRequest toInvoiceCacheParameters(final InvoicesParameters invoiceParameters) {
		return new InvoiceFilterRequest()
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

	private static List<Invoice> toInvoicesFromInvoiceCache(final List<generated.se.sundsvall.invoicecache.Invoice> invoiceCacheInvoices) {
		return ofNullable(invoiceCacheInvoices).orElse(emptyList()).stream()
			.map(InvoiceMapper::toInvoice)
			.toList();
	}

	private static Invoice toInvoice(final generated.se.sundsvall.invoicecache.Invoice invoiceCacheInvoice) {
		return Invoice.create()
			.withCurrency("SEK")
			.withDueDate(invoiceCacheInvoice.getInvoiceDueDate())
			.withTotalAmount(ofNullable(invoiceCacheInvoice.getTotalAmount()).orElse(ZERO).floatValue())
			.withAmountVatIncluded(ofNullable(invoiceCacheInvoice.getAmountVatExcluded()).orElse(ZERO).floatValue() + ofNullable(invoiceCacheInvoice.getVat()).orElse(ZERO).floatValue())
			.withAmountVatExcluded(ofNullable(invoiceCacheInvoice.getAmountVatExcluded()).orElse(ZERO).floatValue())
			.withVat(ofNullable(invoiceCacheInvoice.getVat()).orElse(ZERO).floatValue())
			.withInvoiceDate(invoiceCacheInvoice.getInvoiceDate())
			.withInvoiceDescription(invoiceCacheInvoice.getInvoiceDescription())
			.withInvoiceNumber(invoiceCacheInvoice.getInvoiceNumber())
			.withOcrNumber(invoiceCacheInvoice.getOcrNumber())
			.withInvoiceStatus(toInvoiceStatus(invoiceCacheInvoice.getInvoiceStatus()))
			.withInvoiceType(toInvoiceType(invoiceCacheInvoice.getInvoiceType()))
			.withInvoiceAddress(toAddress(invoiceCacheInvoice))
			.withInvoiceOrigin(PUBLIC_ADMINISTRATION);
	}

	private static Address toAddress(final generated.se.sundsvall.invoicecache.Invoice invoiceCacheInvoice) {
		return Optional.ofNullable(invoiceCacheInvoice.getInvoiceAddress())
			.map(invoiceAddress -> Address.create()
				.withCareOf(invoiceAddress.getCareOf())
				.withCity(invoiceAddress.getCity())
				.withPostcode(invoiceAddress.getPostcode())
				.withStreet(invoiceAddress.getStreet()))
			.orElse(null);
	}

	static InvoiceStatus toInvoiceStatus(final InvoiceStatusEnum invoiceStatusEnum) {
		return Optional.ofNullable(invoiceStatusEnum)
			.map(invoiceStatus -> switch (invoiceStatus)
			{
				case PAID -> InvoiceStatus.PAID;
				case UNPAID -> InvoiceStatus.SENT;
				case SENT -> InvoiceStatus.SENT;
				case PARTIALLY_PAID -> InvoiceStatus.PARTIALLY_PAID;
				case DEBT_COLLECTION -> InvoiceStatus.DEBT_COLLECTION;
				case PAID_TOO_MUCH -> InvoiceStatus.PAID_TOO_MUCH;
				case REMINDER -> InvoiceStatus.REMINDER;
				case VOID -> InvoiceStatus.VOID;
				case UNKNOWN -> InvoiceStatus.UNKNOWN;
			})
			.orElse(null);
	}

	static InvoiceType toInvoiceType(final InvoiceTypeEnum invoiceTypeEnum) {
		return Optional.ofNullable(invoiceTypeEnum)
			.map(invoiceType -> switch (invoiceType)
			{
				case INVOICE -> InvoiceType.INVOICE;
				case CREDIT_INVOICE -> InvoiceType.CREDIT_INVOICE;
				case FINAL_INVOICE -> InvoiceType.FINAL_INVOICE;
				case DIRECT_DEBIT -> InvoiceType.DIRECT_DEBIT;
				case SELF_INVOICE -> InvoiceType.SELF_INVOICE;
				case REMINDER -> InvoiceType.REMINDER;
				case CONSOLIDATED_INVOICE -> InvoiceType.CONSOLIDATED_INVOICE;
			})
			.orElse(null);
	}

	private static MetaData toMetaData(final generated.se.sundsvall.invoicecache.MetaData invoiceCacheMetaData) {
		return MetaData.create().withCount(invoiceCacheMetaData.getCount())
			.withLimit(invoiceCacheMetaData.getLimit())
			.withTotalPages(invoiceCacheMetaData.getTotalPages())
			.withTotalRecords(invoiceCacheMetaData.getTotalRecords())
			.withPage(invoiceCacheMetaData.getPage());
	}

	public static PdfInvoice toPdfInvoice(final InvoicePdf invoicePdf) {
		return ofNullable(invoicePdf)
			.map(i -> PdfInvoice.create()
				.withFileName(i.getName())
				.withFile(ofNullable(i.getContent())
					.map(c -> DECODER.decode(c.getBytes(StandardCharsets.UTF_8)))
					.orElse(null)))
			.orElse(null);
	}

	public static PdfInvoice toPdfInvoice(final byte[] file, final String invoiceNumber) {
		return ofNullable(file)
			.map(i -> PdfInvoice.create()
				.withFileName(invoiceNumber + ".pdf")
				.withFile(file))
			.orElse(null);
	}

	/*********************************
	 * COMMON MAPPING
	 *********************************/

	private static String toString(final Long value) {
		return ofNullable(value)
			.map(String::valueOf)
			.orElse(null);
	}

	private static Long toLong(final String value) {
		return ofNullable(value)
			.map(Long::parseLong)
			.orElse(null);
	}

	private static LocalDate toLocalDate(final String value) {
		return ofNullable(value)
			.map(LocalDate::parse)
			.orElse(null);
	}
}

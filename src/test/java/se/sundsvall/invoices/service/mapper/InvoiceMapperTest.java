package se.sundsvall.invoices.service.mapper;

import generated.se.sundsvall.datawarehousereader.Direction;
import generated.se.sundsvall.datawarehousereader.InvoiceParameters;
import generated.se.sundsvall.invoicecache.Invoice.InvoiceStatusEnum;
import generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum;
import generated.se.sundsvall.invoicecache.InvoiceFilterRequest;
import generated.se.sundsvall.invoicecache.InvoicePdf;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import se.sundsvall.invoices.api.model.Address;
import se.sundsvall.invoices.api.model.Invoice;
import se.sundsvall.invoices.api.model.InvoiceDetail;
import se.sundsvall.invoices.api.model.InvoiceStatus;
import se.sundsvall.invoices.api.model.InvoiceType;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.MetaData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class InvoiceMapperTest {

	private static final String DATAWAREHOUSEREADER_ADMINISTRATION = "Sundsvall Elnät";
	private static final BigDecimal DATAWAREHOUSEREADER_AMOUNT_VAT_EXCLUDED = BigDecimal.valueOf(13.37d);
	private static final BigDecimal DATAWAREHOUSEREADER_AMOUNT_VAT_INCLUDED = BigDecimal.valueOf(26.779999d);
	private static final String DATAWAREHOUSEREADER_CARE_OF = "careOf";
	private static final String DATAWAREHOUSEREADER_CITY = "city";
	private static final String DATAWAREHOUSEREADER_CURRENCY = "SEK";
	private static final String DATAWAREHOUSEREADER_CUSTOMER_NUMBER = "1337";
	private static final LocalDate DATAWAREHOUSEREADER_DUE_DATE = LocalDate.now().plusDays(30);
	private static final Set<String> DATAWAREHOUSEREADER_FACILITY_IDS = Set.of("facilityId");
	private static final LocalDate DATAWAREHOUSEREADER_INVOICE_DATE = LocalDate.now();
	private static final LocalDate DATAWAREHOUSEREADER_INVOICE_DATE_FROM = LocalDate.of(2022, 3, 1);
	private static final LocalDate DATAWAREHOUSEREADER_INVOICE_DATE_TO = LocalDate.of(2022, 3, 31);
	private static final LocalDate DATAWAREHOUSEREADER_DUE_DATE_FROM = LocalDate.of(2022, 4, 1);
	private static final LocalDate DATAWAREHOUSEREADER_DUE_DATE_TO = LocalDate.of(2022, 4, 30);
	private static final Set<String> DATAWAREHOUSEREADER_INVOICE_DESCRIPTIONS = Set.of("invoiceDescription");
	private static final String DATAWAREHOUSEREADER_INVOICE_NAME = "invoiceName";
	private static final long DATAWAREHOUSEREADER_INVOICE_NUMBER = 4321;
	private static final String DATAWAREHOUSEREADER_INVOICE_STATUS = "Betalad";
	private static final String DATAWAREHOUSEREADER_INVOICE_TYPE = "Faktura";
	private static final long DATAWAREHOUSEREADER_OCR_NUMBER = 1234;
	private static final String DATAWAREHOUSEREADER_ORGANIZATION_GROUP = "organizationGroup";
	private static final String DATAWAREHOUSEREADER_ORGANIZATION_NUMBER = "1234567890";
	private static final String DATAWAREHOUSEREADER_POSTAL_CODE = "postalCode";
	private static final Boolean DATAWAREHOUSEREADER_REVERSED_VAT = false;
	private static final BigDecimal DATAWAREHOUSEREADER_ROUNDING = BigDecimal.valueOf(13.39d);
	private static final BigDecimal DATAWAREHOUSEREADER_AMOUNT = BigDecimal.valueOf(13.43d);
	private static final BigDecimal DATAWAREHOUSEREADER_TOTAL_AMOUNT = BigDecimal.valueOf(13.40d);
	private static final BigDecimal DATAWAREHOUSEREADER_VAT = BigDecimal.valueOf(13.41d);
	private static final BigDecimal DATAWAREHOUSEREADER_VAT_ELIGIBLE_AMOUNT = BigDecimal.valueOf(13.42d);
	private static final String DATAWAREHOUSEREADER_PERIOD_FROM = "2022-01-01";
	private static final String DATAWAREHOUSEREADER_PERIOD_TO = "2022-01-31";
	private static final int DATAWAREHOUSEREADER_PRODUCT_CODE = 7371;
	private static final String DATAWAREHOUSEREADER_PRODUCT_NAME = "productName";
	private static final double DATAWAREHOUSEREADER_QUANTITY = 13.44;
	private static final String DATAWAREHOUSEREADER_UNIT = "unit";
	private static final BigDecimal DATAWAREHOUSEREADER_UNIT_PRICE = BigDecimal.valueOf(13.45d);
	private static final double DATAWAREHOUSEREADER_VAT_RATE = 13.46;
	private static final String DATAWAREHOUSEREADER_STREET = "street";
	private static final Integer DATAWAREHOUSEREADER_LIMIT = 50;
	private static final Integer DATAWAREHOUSEREADER_PAGE = 5;
	private static final Boolean DATAWAREHOUSEREADER_PDF_AVAILABLE = false;
	private static final String SORT_BY = "invoiceDate";

	private static final String INVOICECACHE_INVOICE_NUMBER = "4321";
	private static final InvoiceStatusEnum INVOICECACHE_INVOICE_STATUS = InvoiceStatusEnum.PAID;
	private static final String INVOICECACHE_OCR_NUMBER = "1234";
	private static final String INVOICECACHE_DESCRIPTION = "invoiceDescription";
	private static final LocalDate INVOICECACHE_DUE_DATE = LocalDate.now().plusDays(30);
	private static final LocalDate INVOICECACHE_INVOICE_DATE = LocalDate.now();
	private static final BigDecimal INVOICECACHE_TOTAL_AMOUNT = BigDecimal.valueOf(13.40f);
	private static final BigDecimal INVOICECACHE_VAT = BigDecimal.valueOf(13.41d);
	private static final BigDecimal INVOICECACHE_AMOUNT_VAT_EXCLUDED = BigDecimal.valueOf(13.37f);
	private static final InvoiceTypeEnum INVOICECACHE_INVOICE_TYPE = InvoiceTypeEnum.INVOICE;
	private static final generated.se.sundsvall.invoicecache.Address INVOICECACHE_ADDRESS = new generated.se.sundsvall.invoicecache.Address()
		.careOf("careOf")
		.street("street")
		.city("city")
		.postcode("postalCode");

	private static final List<String> CUSTOMER_NUMBERS = List.of(DATAWAREHOUSEREADER_CUSTOMER_NUMBER);
	private static final String ORGANIZATION_GROUP = "organizationGroup";
	private static final String ORGANIZATION_NUMBER = "1234567890";
	private static final float AMOUNT_VAT_EXCLUDED = 13.37f;
	private static final float AMOUNT_VAT_INCLUDED = 26.779999f;
	private static final String CURRENCY = "SEK";
	private static final LocalDate DUE_DATE = LocalDate.now().plusDays(30);
	private static final LocalDate DUE_DATE_FROM = LocalDate.of(2022, 4, 1);
	private static final LocalDate DUE_DATE_TO = LocalDate.of(2022, 4, 30);
	private static final LocalDate INVOICE_DATE_FROM = LocalDate.of(2022, 3, 1);
	private static final LocalDate INVOICE_DATE_TO = LocalDate.of(2022, 3, 31);
	private static final List<String> FACILITY_IDS = List.of("facilityId");
	private static final LocalDate INVOICE_DATE = LocalDate.now();
	private static final String INVOICE_DESCRIPTION = "invoiceDescription";
	private static final String INVOICE_NAME = "invoiceName";
	private static final String INVOICE_NUMBER = "4321";
	private static final InvoiceStatus INVOICE_STATUS = InvoiceStatus.PAID;
	private static final InvoiceType INVOICE_TYPE = InvoiceType.INVOICE;
	private static final String OCR_NUMBER = "1234";
	private static final boolean REVERSED_VAT = false;
	private static final float ROUNDING = 13.39f;
	private static final float TOTAL_AMOUNT = 13.40f;
	private static final float VAT = 13.41f;
	private static final float VAT_ELIGIBLE_AMOUNT = 13.42f;
	private static final float AMOUNT = 13.43f;
	private static final LocalDate FROM_DATE = LocalDate.of(2022, 1, 1);
	private static final LocalDate TO_DATE = LocalDate.of(2022, 1, 31);
	private static final String PRODUCT_CODE = "7371";
	private static final String PRODUCT_NAME = "productName";
	private static final float QUANTITY = 13.44f;
	private static final String UNIT = "unit";
	private static final float UNIT_PRICE = 13.45f;
	private static final float VAT_RATE = 13.46f;
	private static final Address ADDRESS = Address.create().withCareOf("careOf")
		.withStreet("street")
		.withCity("city")
		.withPostcode("postalCode");
	private static final List<String> PARTY_IDS = List.of(UUID.randomUUID().toString());
	private static final boolean PDF_AVAILABLE = false;
	private static final Integer LIMIT = 50;
	private static final Integer PAGE = 5;

	private static Stream<Arguments> toInvoiceCacheInvoiceTypeArguments() {
		return Stream.of(
			Arguments.of(InvoiceType.CONSOLIDATED_INVOICE, generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum.CONSOLIDATED_INVOICE),
			Arguments.of(InvoiceType.CREDIT_INVOICE, generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum.CREDIT_INVOICE),
			Arguments.of(InvoiceType.DIRECT_DEBIT, generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum.DIRECT_DEBIT),
			Arguments.of(InvoiceType.FINAL_INVOICE, generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum.FINAL_INVOICE),
			Arguments.of(InvoiceType.INTERNAL_INVOICE, null),
			Arguments.of(InvoiceType.INVOICE, generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum.INVOICE),
			Arguments.of(InvoiceType.OFFSET_INVOICE, null),
			Arguments.of(InvoiceType.REMINDER, generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum.REMINDER),
			Arguments.of(InvoiceType.SELF_INVOICE, generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum.SELF_INVOICE),
			Arguments.of(InvoiceType.START_INVOICE, null),
			Arguments.of(InvoiceType.UNKNOWN, null),
			Arguments.of(null, null));
	}

	private static Stream<Arguments> toInvoiceTypeArguments() {
		return Stream.of(
			Arguments.of("Kreditfaktura", InvoiceType.CREDIT_INVOICE),
			Arguments.of("Faktura", InvoiceType.INVOICE),
			Arguments.of("Startfaktura", InvoiceType.START_INVOICE),
			Arguments.of("Slutfaktura", InvoiceType.FINAL_INVOICE),
			Arguments.of("Kvittning", InvoiceType.OFFSET_INVOICE),
			Arguments.of("Internfaktura", InvoiceType.INTERNAL_INVOICE),
			Arguments.of("Samlingsfaktura", InvoiceType.CONSOLIDATED_INVOICE),
			Arguments.of("Samlingsfaktura-unknown", InvoiceType.UNKNOWN),
			Arguments.of(null, null));
	}

	private static Stream<Arguments> toDataWarehouseReaderInvoiceTypeArguments() {
		return Stream.of(
			Arguments.of(InvoiceType.CREDIT_INVOICE, "Kreditfaktura"),
			Arguments.of(InvoiceType.INVOICE, "Faktura"),
			Arguments.of(InvoiceType.START_INVOICE, "Startfaktura"),
			Arguments.of(InvoiceType.FINAL_INVOICE, "Slutfaktura"),
			Arguments.of(InvoiceType.OFFSET_INVOICE, "Kvittning"),
			Arguments.of(InvoiceType.INTERNAL_INVOICE, "Internfaktura"),
			Arguments.of(InvoiceType.CONSOLIDATED_INVOICE, "Samlingsfaktura"),
			Arguments.of(InvoiceType.UNKNOWN, null),
			Arguments.of(null, null));
	}

	private static Stream<Arguments> toDataWarehouseReaderInvoiceStatusArguments() {
		return Stream.of(
			Arguments.of(InvoiceStatus.PAID, "Betalad"),
			Arguments.of(InvoiceStatus.CREDITED, "Krediterad"),
			Arguments.of(InvoiceStatus.DEBT_COLLECTION, "Inkasso"),
			Arguments.of(InvoiceStatus.REMINDER, "Påminnelse"),
			Arguments.of(InvoiceStatus.WRITTEN_OFF, "Avskriven"),
			Arguments.of(InvoiceStatus.SENT, "Skickad"),
			Arguments.of(InvoiceStatus.VOID, "Makulerad"),
			Arguments.of(InvoiceStatus.UNKNOWN, null),
			Arguments.of(null, null));
	}

	private static Stream<Arguments> toInvoiceStatusFromDatawarehouseReaderStatusArguments() {
		return Stream.of(
			Arguments.of("Betalad", InvoiceStatus.PAID),
			Arguments.of("Krediterad", InvoiceStatus.CREDITED),
			Arguments.of("Inkasso", InvoiceStatus.DEBT_COLLECTION),
			Arguments.of("Påminnelse", InvoiceStatus.REMINDER),
			Arguments.of("Avskriven", InvoiceStatus.WRITTEN_OFF),
			Arguments.of("Skickad", InvoiceStatus.SENT),
			Arguments.of("Makulerad", InvoiceStatus.VOID),
			Arguments.of("something-unknown", InvoiceStatus.UNKNOWN),
			Arguments.of(null, null));
	}

	private static Stream<Arguments> toInvoiceStatusFromInvoiceCacheStatusArguments() {
		return Stream.of(
			Arguments.of(InvoiceStatusEnum.DEBT_COLLECTION, InvoiceStatus.DEBT_COLLECTION),
			Arguments.of(InvoiceStatusEnum.PAID, InvoiceStatus.PAID),
			Arguments.of(InvoiceStatusEnum.PAID_TOO_MUCH, InvoiceStatus.PAID_TOO_MUCH),
			Arguments.of(InvoiceStatusEnum.PARTIALLY_PAID, InvoiceStatus.PARTIALLY_PAID),
			Arguments.of(InvoiceStatusEnum.REMINDER, InvoiceStatus.REMINDER),
			Arguments.of(InvoiceStatusEnum.SENT, InvoiceStatus.SENT),
			Arguments.of(InvoiceStatusEnum.UNKNOWN, InvoiceStatus.UNKNOWN),
			Arguments.of(InvoiceStatusEnum.UNPAID, InvoiceStatus.SENT),
			Arguments.of(InvoiceStatusEnum.VOID, InvoiceStatus.VOID),
			Arguments.of(null, null));
	}

	private static Stream<Arguments> toInvoiceTypeFromInvoiceCacheStatusArguments() {
		return Stream.of(
			Arguments.of(InvoiceTypeEnum.CONSOLIDATED_INVOICE, InvoiceType.CONSOLIDATED_INVOICE),
			Arguments.of(InvoiceTypeEnum.CREDIT_INVOICE, InvoiceType.CREDIT_INVOICE),
			Arguments.of(InvoiceTypeEnum.DIRECT_DEBIT, InvoiceType.DIRECT_DEBIT),
			Arguments.of(InvoiceTypeEnum.FINAL_INVOICE, InvoiceType.FINAL_INVOICE),
			Arguments.of(InvoiceTypeEnum.INVOICE, InvoiceType.INVOICE),
			Arguments.of(InvoiceTypeEnum.REMINDER, InvoiceType.REMINDER),
			Arguments.of(InvoiceTypeEnum.SELF_INVOICE, InvoiceType.SELF_INVOICE),
			Arguments.of(null, null));
	}

	@Test
	void toCommercialInvoices() {
		final var dataWarehouseReaderInvoice = new generated.se.sundsvall.datawarehousereader.Invoice();
		dataWarehouseReaderInvoice.setAdministration(DATAWAREHOUSEREADER_ADMINISTRATION);
		dataWarehouseReaderInvoice.setAmountVatExcluded(DATAWAREHOUSEREADER_AMOUNT_VAT_EXCLUDED);
		dataWarehouseReaderInvoice.setAmountVatIncluded(DATAWAREHOUSEREADER_AMOUNT_VAT_INCLUDED);
		dataWarehouseReaderInvoice.setCareOf(DATAWAREHOUSEREADER_CARE_OF);
		dataWarehouseReaderInvoice.setCity(DATAWAREHOUSEREADER_CITY);
		dataWarehouseReaderInvoice.setCurrency(DATAWAREHOUSEREADER_CURRENCY);
		dataWarehouseReaderInvoice.setCustomerNumber(DATAWAREHOUSEREADER_CUSTOMER_NUMBER);
		dataWarehouseReaderInvoice.setDueDate(DATAWAREHOUSEREADER_DUE_DATE);
		dataWarehouseReaderInvoice.setFacilityIds(DATAWAREHOUSEREADER_FACILITY_IDS);
		dataWarehouseReaderInvoice.setInvoiceDate(DATAWAREHOUSEREADER_INVOICE_DATE);
		dataWarehouseReaderInvoice.setInvoiceDescriptions(DATAWAREHOUSEREADER_INVOICE_DESCRIPTIONS);
		dataWarehouseReaderInvoice.setInvoiceName(DATAWAREHOUSEREADER_INVOICE_NAME);
		dataWarehouseReaderInvoice.setInvoiceNumber(DATAWAREHOUSEREADER_INVOICE_NUMBER);
		dataWarehouseReaderInvoice.setInvoiceStatus(DATAWAREHOUSEREADER_INVOICE_STATUS);
		dataWarehouseReaderInvoice.setInvoiceType(DATAWAREHOUSEREADER_INVOICE_TYPE);
		dataWarehouseReaderInvoice.setOcrNumber(DATAWAREHOUSEREADER_OCR_NUMBER);
		dataWarehouseReaderInvoice.setOrganizationGroup(DATAWAREHOUSEREADER_ORGANIZATION_GROUP);
		dataWarehouseReaderInvoice.setOrganizationNumber(DATAWAREHOUSEREADER_ORGANIZATION_NUMBER);
		dataWarehouseReaderInvoice.setPostCode(DATAWAREHOUSEREADER_POSTAL_CODE);
		dataWarehouseReaderInvoice.setReversedVat(DATAWAREHOUSEREADER_REVERSED_VAT);
		dataWarehouseReaderInvoice.setRounding(DATAWAREHOUSEREADER_ROUNDING);
		dataWarehouseReaderInvoice.setStreet(DATAWAREHOUSEREADER_STREET);
		dataWarehouseReaderInvoice.setTotalAmount(DATAWAREHOUSEREADER_TOTAL_AMOUNT);
		dataWarehouseReaderInvoice.setVat(DATAWAREHOUSEREADER_VAT);
		dataWarehouseReaderInvoice.setPdfAvailable(DATAWAREHOUSEREADER_PDF_AVAILABLE);
		dataWarehouseReaderInvoice.setVatEligibleAmount(DATAWAREHOUSEREADER_VAT_ELIGIBLE_AMOUNT);

		final var pagingAndSortingMetaData = new generated.se.sundsvall.datawarehousereader.PagingAndSortingMetaData();
		pagingAndSortingMetaData.setTotalRecords(10000L);
		pagingAndSortingMetaData.setPage(1);
		pagingAndSortingMetaData.setTotalPages(100);
		pagingAndSortingMetaData.setLimit(10);
		pagingAndSortingMetaData.setCount(1000);

		final var metaData = MetaData.create().withTotalRecords(10000)
			.withPage(1)
			.withTotalPages(100)
			.withLimit(10)
			.withCount(1000);

		final var dataWarehouseReaderInvoiceResponse = new generated.se.sundsvall.datawarehousereader.InvoiceResponse();
		dataWarehouseReaderInvoiceResponse.setInvoices(List.of(dataWarehouseReaderInvoice));
		dataWarehouseReaderInvoiceResponse.setMeta(pagingAndSortingMetaData);

		final var invoicesResponse = InvoiceMapper.toInvoicesResponse(dataWarehouseReaderInvoiceResponse);

		assertThat(invoicesResponse.getInvoices())
			.hasSize(1)
			.extracting(
				Invoice::getAmountVatExcluded,
				Invoice::getAmountVatIncluded,
				Invoice::getCurrency,
				Invoice::getDueDate,
				Invoice::getFacilityIds,
				Invoice::getInvoiceDate,
				Invoice::getInvoiceDescriptions,
				Invoice::getInvoiceName,
				Invoice::getInvoiceNumber,
				Invoice::getInvoiceStatus,
				Invoice::getInvoiceType,
				Invoice::getOcrNumber,
				Invoice::getReversedVat,
				Invoice::getRounding,
				Invoice::getTotalAmount,
				Invoice::getVat,
				Invoice::getVatEligibleAmount,
				Invoice::getInvoiceAddress,
				Invoice::getPdfAvailable,
				Invoice::getOrganizationNumber)
			.containsExactly(tuple(
				AMOUNT_VAT_EXCLUDED,
				AMOUNT_VAT_INCLUDED,
				CURRENCY,
				DUE_DATE,
				DATAWAREHOUSEREADER_FACILITY_IDS,
				INVOICE_DATE,
				DATAWAREHOUSEREADER_INVOICE_DESCRIPTIONS,
				INVOICE_NAME,
				INVOICE_NUMBER,
				INVOICE_STATUS,
				INVOICE_TYPE,
				OCR_NUMBER,
				REVERSED_VAT,
				ROUNDING,
				TOTAL_AMOUNT,
				VAT,
				VAT_ELIGIBLE_AMOUNT,
				ADDRESS,
				PDF_AVAILABLE,
				ORGANIZATION_NUMBER));

		assertThat(invoicesResponse.getMetaData()).isEqualTo(metaData);
	}

	@Test
	void toPublicAdministrationInvoices() {
		final var invoiceCacheInvoice = new generated.se.sundsvall.invoicecache.Invoice();

		invoiceCacheInvoice.setAmountVatExcluded(INVOICECACHE_AMOUNT_VAT_EXCLUDED);
		invoiceCacheInvoice.setVat(INVOICECACHE_VAT);
		invoiceCacheInvoice.setInvoiceAddress(INVOICECACHE_ADDRESS);

		invoiceCacheInvoice.setInvoiceDueDate(INVOICECACHE_DUE_DATE);
		invoiceCacheInvoice.setInvoiceDate(INVOICECACHE_INVOICE_DATE);
		invoiceCacheInvoice.setInvoiceDescription(INVOICECACHE_DESCRIPTION);
		invoiceCacheInvoice.setInvoiceNumber(INVOICECACHE_INVOICE_NUMBER);
		invoiceCacheInvoice.setInvoiceStatus(INVOICECACHE_INVOICE_STATUS);
		invoiceCacheInvoice.setInvoiceType(INVOICECACHE_INVOICE_TYPE);
		invoiceCacheInvoice.setOcrNumber(INVOICECACHE_OCR_NUMBER);
		invoiceCacheInvoice.setTotalAmount(INVOICECACHE_TOTAL_AMOUNT);
		invoiceCacheInvoice.setVat(INVOICECACHE_VAT);

		final var invoiceCacheMetaData = new generated.se.sundsvall.invoicecache.MetaData();
		invoiceCacheMetaData.setTotalRecords(10000L);
		invoiceCacheMetaData.setPage(1);
		invoiceCacheMetaData.setTotalPages(100);
		invoiceCacheMetaData.setLimit(10);
		invoiceCacheMetaData.setCount(1000);

		final var metaData = MetaData.create().withTotalRecords(10000)
			.withPage(1)
			.withTotalPages(100)
			.withLimit(10)
			.withCount(1000);

		final var invoiceCacheInvoicesResponse = new generated.se.sundsvall.invoicecache.InvoicesResponse();
		invoiceCacheInvoicesResponse.setInvoices(List.of(invoiceCacheInvoice));
		invoiceCacheInvoicesResponse.setMeta(invoiceCacheMetaData);

		final var invoicesResponse = InvoiceMapper.toInvoicesResponse(invoiceCacheInvoicesResponse);

		assertThat(invoicesResponse.getInvoices())
			.hasSize(1)
			.extracting(
				Invoice::getAmountVatExcluded,
				Invoice::getAmountVatIncluded,
				Invoice::getCurrency,
				Invoice::getDueDate,
				Invoice::getFacilityIds,
				Invoice::getInvoiceDate,
				Invoice::getInvoiceDescriptions,
				Invoice::getInvoiceNumber,
				Invoice::getInvoiceStatus,
				Invoice::getInvoiceType,
				Invoice::getOcrNumber,
				Invoice::getReversedVat,
				Invoice::getRounding,
				Invoice::getTotalAmount,
				Invoice::getVat,
				Invoice::getVatEligibleAmount,
				Invoice::getInvoiceAddress,
				Invoice::getPdfAvailable,
				Invoice::getOrganizationNumber)
			.containsExactly(tuple(
				AMOUNT_VAT_EXCLUDED,
				AMOUNT_VAT_INCLUDED,
				CURRENCY,
				DUE_DATE,
				null,
				INVOICE_DATE,
				DATAWAREHOUSEREADER_INVOICE_DESCRIPTIONS,
				INVOICE_NUMBER,
				INVOICE_STATUS,
				INVOICE_TYPE,
				OCR_NUMBER,
				null,
				0.0f,
				TOTAL_AMOUNT,
				VAT,
				0.0f,
				ADDRESS,
				null,
				null));

		assertThat(invoicesResponse.getMetaData()).isEqualTo(metaData);
	}

	@Test
	void toDetailsWithNull() {
		assertThat(InvoiceMapper.toInvoiceDetails(null)).isEmpty();
	}

	@Test
	void toDetailsWithEmptyList() {
		assertThat(InvoiceMapper.toInvoiceDetails(Collections.emptyList())).isEmpty();
	}

	@Test
	void toDetails() {
		final var dataWarehouseReaderInvoiceDetail = new generated.se.sundsvall.datawarehousereader.InvoiceDetail();
		dataWarehouseReaderInvoiceDetail.setAmount(DATAWAREHOUSEREADER_AMOUNT);
		dataWarehouseReaderInvoiceDetail.setAmountVatExcluded(DATAWAREHOUSEREADER_AMOUNT_VAT_EXCLUDED);
		dataWarehouseReaderInvoiceDetail.setDescription(DATAWAREHOUSEREADER_INVOICE_DESCRIPTIONS.iterator().next());
		dataWarehouseReaderInvoiceDetail.setInvoiceNumber(DATAWAREHOUSEREADER_INVOICE_NUMBER);
		dataWarehouseReaderInvoiceDetail.setPeriodFrom(DATAWAREHOUSEREADER_PERIOD_FROM);
		dataWarehouseReaderInvoiceDetail.setPeriodTo(DATAWAREHOUSEREADER_PERIOD_TO);
		dataWarehouseReaderInvoiceDetail.setProductCode(DATAWAREHOUSEREADER_PRODUCT_CODE);
		dataWarehouseReaderInvoiceDetail.setProductName(DATAWAREHOUSEREADER_PRODUCT_NAME);
		dataWarehouseReaderInvoiceDetail.setQuantity(DATAWAREHOUSEREADER_QUANTITY);
		dataWarehouseReaderInvoiceDetail.setUnit(DATAWAREHOUSEREADER_UNIT);
		dataWarehouseReaderInvoiceDetail.setUnitPrice(DATAWAREHOUSEREADER_UNIT_PRICE);
		dataWarehouseReaderInvoiceDetail.setVat(DATAWAREHOUSEREADER_VAT);
		dataWarehouseReaderInvoiceDetail.setVatRate(DATAWAREHOUSEREADER_VAT_RATE);

		final var result = InvoiceMapper.toInvoiceDetails(List.of(dataWarehouseReaderInvoiceDetail));

		assertThat(result)
			.hasSize(1)
			.extracting(
				InvoiceDetail::getAmount,
				InvoiceDetail::getAmountVatExcluded,
				InvoiceDetail::getDescription,
				InvoiceDetail::getFromDate,
				InvoiceDetail::getToDate,
				InvoiceDetail::getProductCode,
				InvoiceDetail::getProductName,
				InvoiceDetail::getQuantity,
				InvoiceDetail::getUnit,
				InvoiceDetail::getUnitPrice,
				InvoiceDetail::getVat,
				InvoiceDetail::getVatRate)
			.containsExactly(tuple(
				AMOUNT,
				AMOUNT_VAT_EXCLUDED,
				INVOICE_DESCRIPTION,
				FROM_DATE,
				TO_DATE,
				PRODUCT_CODE,
				PRODUCT_NAME,
				QUANTITY,
				UNIT,
				UNIT_PRICE,
				VAT,
				VAT_RATE));
	}

	@Test
	void toDataWarehouseReaderInvoiceParameters() {
		final var invoicesParameters = new InvoicesParameters()
			.withFacilityIds(FACILITY_IDS)
			.withInvoiceName(INVOICE_NAME)
			.withInvoiceNumber(INVOICE_NUMBER)
			.withInvoiceStatus(INVOICE_STATUS)
			.withInvoiceType(INVOICE_TYPE)
			.withOcrNumber(OCR_NUMBER)
			.withOrganizationGroup(ORGANIZATION_GROUP)
			.withOrganizationNumber(ORGANIZATION_NUMBER)
			.withInvoiceDateFrom(INVOICE_DATE_FROM)
			.withInvoiceDateTo(INVOICE_DATE_TO)
			.withDueDateFrom(DUE_DATE_FROM)
			.withDueDateTo(DUE_DATE_TO)
			.withLimit(LIMIT)
			.withPage(PAGE);

		final var invoiceParameters = InvoiceMapper.toDataWarehouseReaderInvoiceParameters(CUSTOMER_NUMBERS, invoicesParameters);

		assertThat(invoiceParameters).extracting(
			InvoiceParameters::getAdministration,
			InvoiceParameters::getCustomerNumber,
			InvoiceParameters::getCustomerType,
			InvoiceParameters::getDueDateFrom,
			InvoiceParameters::getDueDateTo,
			InvoiceParameters::getFacilityIds,
			InvoiceParameters::getInvoiceDateFrom,
			InvoiceParameters::getInvoiceDateTo,
			InvoiceParameters::getInvoiceName,
			InvoiceParameters::getInvoiceNumber,
			InvoiceParameters::getInvoiceStatus,
			InvoiceParameters::getInvoiceType,
			InvoiceParameters::getLimit,
			InvoiceParameters::getOcrNumber,
			InvoiceParameters::getOrganizationGroup,
			InvoiceParameters::getOrganizationNumber,
			InvoiceParameters::getPage,
			InvoiceParameters::getSortBy,
			InvoiceParameters::getSortDirection)
			.containsExactly(
				null,
				CUSTOMER_NUMBERS,
				null,
				DATAWAREHOUSEREADER_DUE_DATE_FROM,
				DATAWAREHOUSEREADER_DUE_DATE_TO,
				FACILITY_IDS,
				DATAWAREHOUSEREADER_INVOICE_DATE_FROM,
				DATAWAREHOUSEREADER_INVOICE_DATE_TO,
				DATAWAREHOUSEREADER_INVOICE_NAME,
				DATAWAREHOUSEREADER_INVOICE_NUMBER,
				DATAWAREHOUSEREADER_INVOICE_STATUS,
				DATAWAREHOUSEREADER_INVOICE_TYPE,
				DATAWAREHOUSEREADER_LIMIT,
				DATAWAREHOUSEREADER_OCR_NUMBER,
				DATAWAREHOUSEREADER_ORGANIZATION_GROUP,
				DATAWAREHOUSEREADER_ORGANIZATION_NUMBER,
				DATAWAREHOUSEREADER_PAGE,
				List.of(SORT_BY),
				Direction.DESC);
	}

	@ParameterizedTest
	@MethodSource("toInvoiceCacheInvoiceTypeArguments")
	void toInvoiceCacheInvoiceType(final InvoiceType source, final generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum target) {
		assertThat(InvoiceMapper.toInvoiceCacheInvoiceType(source)).isEqualTo(target);
	}

	@Test
	void toInvoiceCacheParameters() {
		final var invoicesParameters = new InvoicesParameters()
			.withInvoiceNumber(INVOICE_NUMBER)
			.withOcrNumber(OCR_NUMBER)
			.withInvoiceDateFrom(INVOICE_DATE_FROM)
			.withInvoiceDateTo(INVOICE_DATE_TO)
			.withDueDateFrom(DUE_DATE_FROM)
			.withDueDateTo(DUE_DATE_TO)
			.withLimit(LIMIT)
			.withPage(PAGE)
			.withPartyId(PARTY_IDS);

		final var invoiceCacheParameters = InvoiceMapper.toInvoiceCacheParameters(invoicesParameters);

		assertThat(invoiceCacheParameters).extracting(
			InvoiceFilterRequest::getDueDateFrom,
			InvoiceFilterRequest::getDueDateTo,
			InvoiceFilterRequest::getInvoiceDateFrom,
			InvoiceFilterRequest::getInvoiceDateTo,
			InvoiceFilterRequest::getInvoiceNumbers,
			InvoiceFilterRequest::getLimit,
			InvoiceFilterRequest::getOcrNumber,
			InvoiceFilterRequest::getPage,
			InvoiceFilterRequest::getPartyIds)
			.containsExactly(
				DUE_DATE_FROM,
				DUE_DATE_TO,
				INVOICE_DATE_FROM,
				INVOICE_DATE_TO,
				List.of(INVOICE_NUMBER),
				LIMIT,
				OCR_NUMBER,
				PAGE,
				PARTY_IDS);
	}

	@Test
	void toPdfInvoice() {
		final var name = "name";
		final var content = "filecontent".getBytes(StandardCharsets.UTF_8);
		final var invoicePdf = new InvoicePdf()
			.name(name)
			.content(Base64.getEncoder().encodeToString(content));

		final var pdfInvoice = InvoiceMapper.toPdfInvoice(invoicePdf);

		assertThat(pdfInvoice).isNotNull();
		assertThat(pdfInvoice.getFileName()).isEqualTo(name);
		assertThat(pdfInvoice.getFile()).isEqualTo(content);
	}

	@Test
	void toPdfInvoiceWithoutContent() {
		final var name = "name";
		final var invoicePdf = new InvoicePdf()
			.name(name);

		final var pdfInvoice = InvoiceMapper.toPdfInvoice(invoicePdf);

		assertThat(pdfInvoice).isNotNull();
		assertThat(pdfInvoice.getFileName()).isEqualTo(name);
		assertThat(pdfInvoice.getFile()).isNull();
	}

	@Test
	void toPdfInvoiceFromNullByteArray() {
		assertThat(InvoiceMapper.toPdfInvoice(null, "invoiceNumber")).isNull();
	}

	@Test
	void toPdfInvoiceFromNullInvoicePdf() {
		assertThat(InvoiceMapper.toPdfInvoice(null)).isNull();
	}

	@ParameterizedTest
	@MethodSource("toInvoiceTypeArguments")
	void toInvoiceType(final String source, final InvoiceType target) {
		assertThat(InvoiceMapper.toInvoiceType(source)).isEqualTo(target);
	}

	@ParameterizedTest
	@MethodSource("toDataWarehouseReaderInvoiceTypeArguments")
	void toDataWarehouseReaderInvoiceType(final InvoiceType source, final String target) {
		assertThat(InvoiceMapper.toDataWarehouseReaderInvoiceType(source)).isEqualTo(target);
	}

	@ParameterizedTest
	@MethodSource("toDataWarehouseReaderInvoiceStatusArguments")
	void toDataWarehouseReaderInvoiceStatus(final InvoiceStatus source, final String target) {
		assertThat(InvoiceMapper.toDataWarehouseReaderInvoiceStatus(source)).isEqualTo(target);
	}

	@ParameterizedTest
	@MethodSource("toInvoiceStatusFromDatawarehouseReaderStatusArguments")
	void toInvoiceStatusFromDatawarehouseReaderStatus(final String source, final InvoiceStatus target) {
		assertThat(InvoiceMapper.toInvoiceStatus(source)).isEqualTo(target);
	}

	@ParameterizedTest
	@MethodSource("toInvoiceStatusFromInvoiceCacheStatusArguments")
	void toInvoiceStatusFromInvoiceCacheStatus(final InvoiceStatusEnum source, final InvoiceStatus target) {
		assertThat(InvoiceMapper.toInvoiceStatus(source)).isEqualTo(target);
	}

	@ParameterizedTest
	@MethodSource("toInvoiceTypeFromInvoiceCacheStatusArguments")
	void toInvoiceTypeFromInvoiceCacheType(final InvoiceTypeEnum source, final InvoiceType target) {
		assertThat(InvoiceMapper.toInvoiceType(source)).isEqualTo(target);
	}
}

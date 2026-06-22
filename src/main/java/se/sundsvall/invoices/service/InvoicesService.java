package se.sundsvall.invoices.service;

import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import generated.se.sundsvall.datawarehousereader.Direction;
import generated.se.sundsvall.datawarehousereader.InvoiceResponse;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.sundsvall.dept44.problem.Problem;
import se.sundsvall.invoices.api.model.CustomerInvoicesParameters;
import se.sundsvall.invoices.api.model.CustomerInvoicesResponse;
import se.sundsvall.invoices.api.model.InvoiceDetail;
import se.sundsvall.invoices.api.model.InvoiceOrigin;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.api.model.PdfInvoice;
import se.sundsvall.invoices.integration.datawarehousereader.DataWarehouseReaderClient;
import se.sundsvall.invoices.integration.datawarehousereader.InvoicesQueryParameters;
import se.sundsvall.invoices.integration.invoicecache.InvoiceCacheClient;
import se.sundsvall.invoices.service.mapper.InvoiceMapper;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static se.sundsvall.invoices.service.Constants.ERROR_NO_ENGAGEMENT_FOUND;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toCustomerInvoicesResponse;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toDataWarehouseReaderDirection;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toDataWarehouseReaderInvoiceStatus;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toDataWarehouseReaderInvoiceType;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoiceCacheInvoiceType;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoiceFile;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoicesResponse;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toPdfInvoice;

@Service
public class InvoicesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoicesService.class);

	private final DataWarehouseReaderClient dataWarehouseReaderClient;
	private final InvoiceCacheClient invoiceCacheClient;

	public InvoicesService(final DataWarehouseReaderClient dataWarehouseReaderClient, final InvoiceCacheClient invoiceCacheClient) {
		this.dataWarehouseReaderClient = dataWarehouseReaderClient;
		this.invoiceCacheClient = invoiceCacheClient;
	}

	public InvoicesResponse getInvoices(final String municipalityId, final String invoiceOrigin, final InvoicesParameters invoiceParameters) {
		return switch (InvoiceOrigin.valueOf(invoiceOrigin.toUpperCase(Locale.ROOT))) {
			case COMMERCIAL -> toInvoicesResponse(getCommercialInvoices(municipalityId, invoiceParameters));
			case PUBLIC_ADMINISTRATION -> toInvoicesResponse(invoiceCacheClient.getInvoices(municipalityId, InvoiceMapper.toInvoiceCacheParameters(invoiceParameters)));
		};
	}

	private InvoiceResponse getCommercialInvoices(final String municipalityId, final InvoicesParameters invoiceParameters) {
		LOGGER.info("Getting commercial invoices via deprecated method");
		final var query = InvoicesQueryParameters.create()
			.withCustomerNumber(getCustomerNumbers(municipalityId, invoiceParameters.getPartyId()))
			.withFacilityIds(invoiceParameters.getFacilityIds())
			.withInvoiceNumber(ofNullable(invoiceParameters.getInvoiceNumber()).map(Long::parseLong).orElse(null))
			.withInvoiceDateFrom(invoiceParameters.getInvoiceDateFrom())
			.withInvoiceDateTo(invoiceParameters.getInvoiceDateTo())
			.withInvoiceName(invoiceParameters.getInvoiceName())
			.withInvoiceType(toDataWarehouseReaderInvoiceType(invoiceParameters.getInvoiceType()))
			.withInvoiceStatus(toDataWarehouseReaderInvoiceStatus(invoiceParameters.getInvoiceStatus()))
			.withOcrNumber(ofNullable(invoiceParameters.getOcrNumber()).map(Long::parseLong).orElse(null))
			.withDueDateFrom(invoiceParameters.getDueDateFrom())
			.withDueDateTo(invoiceParameters.getDueDateTo())
			.withOrganizationGroup(invoiceParameters.getOrganizationGroup())
			.withOrganizationNumbers(invoiceParameters.getOrganizationNumbers())
			.withSortBy(List.of("invoiceDate"))
			.withSortDirection(Direction.DESC)
			.withPage(invoiceParameters.getPage())
			.withLimit(invoiceParameters.getLimit());
		return dataWarehouseReaderClient.getInvoices(municipalityId, query);
	}

	private List<String> getCustomerNumbers(final String municipalityId, final List<String> partyIds) {
		return dataWarehouseReaderClient.getCustomerEngagements(municipalityId, partyIds).getCustomerEngagements().stream()
			.map(CustomerEngagement::getCustomerNumber)
			.distinct()
			.collect(collectingAndThen(toList(), Optional::of))
			.filter(ObjectUtils::isNotEmpty)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, format(ERROR_NO_ENGAGEMENT_FOUND, partyIds)));
	}

	/**
	 * Returns the customer numbers to query for. When partyIds are supplied they are resolved to customer numbers and
	 * merged with the
	 * explicitly provided customer numbers, deduplicating so the same customer number is never requested twice.
	 */
	private List<String> resolveCustomerNumbers(final String municipalityId, final CustomerInvoicesParameters parameters) {
		final var providedCustomerNumbers = ofNullable(parameters.getCustomerNumbers()).orElse(List.of());
		return ofNullable(parameters.getPartyIds())
			.filter(ObjectUtils::isNotEmpty)
			.map(partyIds -> Stream.concat(providedCustomerNumbers.stream(), getCustomerNumbers(municipalityId, partyIds).stream())
				.distinct()
				.toList())
			.orElse(providedCustomerNumbers);
	}

	public List<InvoiceDetail> getInvoiceDetails(final String municipalityId, final String organizationNumber, final String invoiceNumber) {
		return InvoiceMapper.toInvoiceDetails(dataWarehouseReaderClient.getInvoiceDetails(municipalityId, organizationNumber, parseLong(invoiceNumber)));
	}

	public PdfInvoice getPdfInvoice(final String organizationNumber, final String invoiceNumber, final String invoiceType, final String municipalityId) {
		return toPdfInvoice(invoiceCacheClient.getInvoicePdf(municipalityId, organizationNumber, invoiceNumber, toInvoiceCacheInvoiceType(invoiceType)));
	}

	public InvoiceFile downloadInvoicePdf(final String organizationNumber, final String invoiceNumber, final String invoiceType, final String municipalityId) {
		return toInvoiceFile(invoiceCacheClient.downloadInvoicePdfs(municipalityId, organizationNumber, invoiceNumber, toInvoiceCacheInvoiceType(invoiceType)), invoiceNumber);
	}

	public CustomerInvoicesResponse getInvoicesForCustomer(final String municipalityId, final CustomerInvoicesParameters parameters) {
		return toCustomerInvoicesResponse(dataWarehouseReaderClient.getInvoicesForCustomer(
			municipalityId,
			resolveCustomerNumbers(municipalityId, parameters),
			parameters.getOrganizationNumbers(),
			parameters.getFacilityIds(),
			toDataWarehouseReaderInvoiceStatus(parameters.getStatus()),
			parameters.getPeriodFrom(),
			parameters.getPeriodTo(),
			parameters.getSortBy(),
			toDataWarehouseReaderDirection(parameters.getSortDirection()),
			parameters.getPage(),
			parameters.getLimit()));
	}
}

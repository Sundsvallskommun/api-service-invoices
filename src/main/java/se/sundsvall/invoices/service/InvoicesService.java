package se.sundsvall.invoices.service;

import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import generated.se.sundsvall.datawarehousereader.Direction;
import generated.se.sundsvall.datawarehousereader.InvoiceResponse;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import se.sundsvall.dept44.problem.Problem;
import se.sundsvall.invoices.api.model.CustomerInvoicesParameters;
import se.sundsvall.invoices.api.model.CustomerInvoicesResponse;
import se.sundsvall.invoices.api.model.InvoiceDetail;
import se.sundsvall.invoices.api.model.InvoiceOrigin;
import se.sundsvall.invoices.api.model.InvoiceType;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.api.model.PdfInvoice;
import se.sundsvall.invoices.integration.datawarehousereader.DataWarehouseReaderClient;
import se.sundsvall.invoices.integration.idata.IdataIntegration;
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
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toDataWarehouseReaderInvoiceStatus;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toDataWarehouseReaderInvoiceType;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoiceCacheInvoiceType;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoicesResponse;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toPdfInvoice;

@Service
public class InvoicesService {

	private final DataWarehouseReaderClient dataWarehouseReaderClient;
	private final IdataIntegration idataIntegration;
	private final InvoiceCacheClient invoiceCacheClient;

	public InvoicesService(final DataWarehouseReaderClient dataWarehouseReaderClient, final IdataIntegration idataIntegration, final InvoiceCacheClient invoiceCacheClient) {
		this.dataWarehouseReaderClient = dataWarehouseReaderClient;
		this.idataIntegration = idataIntegration;
		this.invoiceCacheClient = invoiceCacheClient;
	}

	public InvoicesResponse getInvoices(final String municipalityId, final InvoiceOrigin invoiceOrigin, final InvoicesParameters invoiceParameters) {
		return switch (invoiceOrigin) {
			case COMMERCIAL -> toInvoicesResponse(getCommercialInvoices(municipalityId, invoiceParameters));
			case PUBLIC_ADMINISTRATION -> toInvoicesResponse(invoiceCacheClient.getInvoices(municipalityId, InvoiceMapper.toInvoiceCacheParameters(invoiceParameters)));
		};
	}

	private InvoiceResponse getCommercialInvoices(final String municipalityId, final InvoicesParameters invoiceParameters) {
		final var customerNumbers = getCustomerNumbers(municipalityId, invoiceParameters.getPartyId());
		return dataWarehouseReaderClient.getInvoices(
			municipalityId,
			customerNumbers,
			null,
			invoiceParameters.getFacilityIds(),
			ofNullable(invoiceParameters.getInvoiceNumber()).map(Long::parseLong).orElse(null),
			invoiceParameters.getInvoiceDateFrom(),
			invoiceParameters.getInvoiceDateTo(),
			invoiceParameters.getInvoiceName(),
			toDataWarehouseReaderInvoiceType(invoiceParameters.getInvoiceType()),
			toDataWarehouseReaderInvoiceStatus(invoiceParameters.getInvoiceStatus()),
			ofNullable(invoiceParameters.getOcrNumber()).map(Long::parseLong).orElse(null),
			invoiceParameters.getDueDateFrom(),
			invoiceParameters.getDueDateTo(),
			invoiceParameters.getOrganizationGroup(),
			invoiceParameters.getOrganizationNumbers(),
			null,
			List.of("invoiceDate"),
			Direction.DESC,
			invoiceParameters.getPage(),
			invoiceParameters.getLimit());
	}

	private List<String> getCustomerNumbers(final String municipalityId, final List<String> partyIds) {
		return dataWarehouseReaderClient.getCustomerEngagements(municipalityId, partyIds).getCustomerEngagements().stream()
			.map(CustomerEngagement::getCustomerNumber)
			.distinct()
			.collect(collectingAndThen(toList(), Optional::of))
			.filter(ObjectUtils::isNotEmpty)
			.orElseThrow(() -> Problem.valueOf(NOT_FOUND, format(ERROR_NO_ENGAGEMENT_FOUND, partyIds)));
	}

	public List<InvoiceDetail> getInvoiceDetails(final String municipalityId, final String organizationNumber, final String invoiceNumber) {
		return InvoiceMapper.toInvoiceDetails(dataWarehouseReaderClient.getInvoiceDetails(municipalityId, organizationNumber, parseLong(invoiceNumber)));
	}

	public PdfInvoice getPdfInvoice(final String organizationNumber, final String invoiceNumber, final InvoiceType invoiceType, final String municipalityId) {
		if (isInvoicesStoredAtIdata(organizationNumber)) {
			return toPdfInvoice(idataIntegration.getInvoice(invoiceNumber), invoiceNumber);
		}

		return toPdfInvoice(invoiceCacheClient.getInvoicePdf(municipalityId, organizationNumber, invoiceNumber, toInvoiceCacheInvoiceType(invoiceType)));
	}

	public CustomerInvoicesResponse getInvoicesForCustomer(final String municipalityId, final String customerNumber, final CustomerInvoicesParameters parameters) {
		return toCustomerInvoicesResponse(dataWarehouseReaderClient.getInvoicesForCustomer(
			municipalityId,
			customerNumber,
			parameters.getOrganizationIds(),
			parameters.getPeriodFrom(),
			parameters.getPeriodTo(),
			parameters.getSortBy(),
			parameters.getPage(),
			parameters.getLimit()));
	}

	boolean isInvoicesStoredAtIdata(final String organizationNumber) {
		return "5565027223".equals(organizationNumber);
	}
}

package se.sundsvall.invoices.service;

import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import se.sundsvall.invoices.api.model.InvoiceDetail;
import se.sundsvall.invoices.api.model.InvoiceOrigin;
import se.sundsvall.invoices.api.model.InvoiceType;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.api.model.PdfInvoice;
import se.sundsvall.invoices.integration.datawarehousereader.DataWarehouseReaderClient;
import se.sundsvall.invoices.integration.invoicecache.InvoiceCacheClient;
import se.sundsvall.invoices.service.mapper.InvoiceMapper;

import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.zalando.problem.Status.NOT_FOUND;
import static se.sundsvall.invoices.service.Constants.ERROR_NO_ENGAGEMENT_FOUND;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toDataWarehouseReaderInvoiceParameters;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoiceCacheInvoiceType;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoiceDetails;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoicesResponse;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toPdfInvoice;

@Service
public class InvoicesService {

	private final DataWarehouseReaderClient dataWarehouseReaderClient;

	private final InvoiceCacheClient invoiceCacheClient;

	public InvoicesService(final DataWarehouseReaderClient dataWarehouseReaderClient, final InvoiceCacheClient invoiceCacheClient) {
		this.dataWarehouseReaderClient = dataWarehouseReaderClient;
		this.invoiceCacheClient = invoiceCacheClient;
	}

	public InvoicesResponse getInvoices(final String municipalityId, final InvoiceOrigin invoiceOrigin, final InvoicesParameters invoiceParameters) {
		return switch (invoiceOrigin) {
			case COMMERCIAL -> toInvoicesResponse(dataWarehouseReaderClient.getInvoices(municipalityId, toDataWarehouseReaderInvoiceParameters(getCustomerNumbers(municipalityId, invoiceParameters.getPartyId()), invoiceParameters)));
			case PUBLIC_ADMINISTRATION -> toInvoicesResponse(invoiceCacheClient.getInvoices(municipalityId, InvoiceMapper.toInvoiceCacheParameters(invoiceParameters)));
		};
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
		return toInvoiceDetails(dataWarehouseReaderClient.getInvoiceDetails(municipalityId, organizationNumber, parseLong(invoiceNumber)));
	}

	public PdfInvoice getPdfInvoice(final String organizationNumber, final String invoiceNumber, final InvoiceType invoiceType, final String municipalityId) {
		return toPdfInvoice(invoiceCacheClient.getInvoicePdf(municipalityId, organizationNumber, invoiceNumber, toInvoiceCacheInvoiceType(invoiceType)));
	}
}

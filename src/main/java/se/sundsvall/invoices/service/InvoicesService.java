package se.sundsvall.invoices.service;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.zalando.problem.Problem.valueOf;
import static se.sundsvall.invoices.service.Constants.ERROR_NO_ENGAGEMENT_FOUND;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toDataWarehouseReaderInvoiceParameters;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoiceCacheInvoiceType;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoiceDetails;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoicesResponse;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toPdfInvoice;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import se.sundsvall.invoices.api.model.InvoiceDetail;
import se.sundsvall.invoices.api.model.InvoiceOrigin;
import se.sundsvall.invoices.api.model.InvoiceType;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.api.model.PdfInvoice;
import se.sundsvall.invoices.integration.datawarehousereader.DataWarehouseReaderClient;
import se.sundsvall.invoices.integration.invoicecache.InvoiceCacheClient;
import se.sundsvall.invoices.service.mapper.InvoiceMapper;

@Service
public class InvoicesService {

	@Autowired
	private DataWarehouseReaderClient dataWarehouseReaderClient;

	@Autowired
	private InvoiceCacheClient invoiceCacheClient;

	public InvoicesResponse getInvoices(final InvoiceOrigin invoiceOrigin, final InvoicesParameters invoiceParameters) {
		return switch (invoiceOrigin) {
			case COMMERCIAL -> toInvoicesResponse(dataWarehouseReaderClient.getInvoices(toDataWarehouseReaderInvoiceParameters(getCustomerNumbers(invoiceParameters.getPartyId()), invoiceParameters)));
			case PUBLIC_ADMINISTRATION -> toInvoicesResponse(invoiceCacheClient.getInvoices(InvoiceMapper.toInvoiceCacheParameters(invoiceParameters)));
		};
	}

	private List<String> getCustomerNumbers(final List<String> partyIds) {
		return dataWarehouseReaderClient.getCustomerEngagements(partyIds).getCustomerEngagements().stream()
			.map(CustomerEngagement::getCustomerNumber)
			.distinct()
			.collect(collectingAndThen(toList(), Optional::of))
			.filter(ObjectUtils::isNotEmpty)
			.orElseThrow(() -> valueOf(Status.NOT_FOUND, format(ERROR_NO_ENGAGEMENT_FOUND, partyIds)));
	}

	public List<InvoiceDetail> getInvoiceDetails(final String organizationNumber, final String invoiceNumber) {
		return toInvoiceDetails(dataWarehouseReaderClient.getInvoiceDetails(organizationNumber, parseLong(invoiceNumber)));
	}

	public PdfInvoice getPdfInvoice(final String organizationNumber, final String invoiceNumber, final InvoiceType invoiceType) {
		return toPdfInvoice(invoiceCacheClient.getInvoicePdf(organizationNumber, invoiceNumber, toInvoiceCacheInvoiceType(invoiceType)));
	}
}

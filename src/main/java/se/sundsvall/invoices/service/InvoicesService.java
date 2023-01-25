package se.sundsvall.invoices.service;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.zalando.problem.Problem.valueOf;
import static org.zalando.problem.Status.NOT_FOUND;
import static se.sundsvall.invoices.service.Constants.ERROR_NO_ENGAGEMENT_FOUND;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toDataWarehouseReaderInvoiceParameters;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoiceDetails;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoicesResponse;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import se.sundsvall.invoices.api.model.InvoiceDetail;
import se.sundsvall.invoices.api.model.InvoiceOrigin;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.integration.datawarehousereader.DataWarehouseReaderClient;
import se.sundsvall.invoices.integration.invoicecache.InvoiceCacheClient;
import se.sundsvall.invoices.service.mapper.InvoiceMapper;

@Service
public class InvoicesService {

	@Autowired
	private DataWarehouseReaderClient dataWarehouseReaderClient;

	@Autowired
	private InvoiceCacheClient invoiceCacheClient;

	public InvoicesResponse getInvoices(InvoiceOrigin invoiceOrigin, InvoicesParameters invoiceParameters) {
		return switch (invoiceOrigin) {
			case COMMERCIAL -> toInvoicesResponse(dataWarehouseReaderClient.getInvoices(toDataWarehouseReaderInvoiceParameters(getCustomerNumbers(invoiceParameters.getPartyId()), invoiceParameters)));
			case PUBLIC_ADMINISTRATION -> toInvoicesResponse(invoiceCacheClient.getInvoices(InvoiceMapper.toInvoiceCacheParameters(invoiceParameters)));
		};
	}

	private List<String> getCustomerNumbers(List<String> partyIds) {
		return dataWarehouseReaderClient.getCustomerEngagements(partyIds).getCustomerEngagements().stream()
			.map(CustomerEngagement::getCustomerNumber)
			.distinct()
			.collect(collectingAndThen(toList(), Optional::of))
			.filter(ObjectUtils::isNotEmpty)
			.orElseThrow(() -> valueOf(NOT_FOUND, format(ERROR_NO_ENGAGEMENT_FOUND, partyIds)));
	}

	public List<InvoiceDetail> getInvoiceDetails(String organizationNumber, String invoiceNumber) {
		if (isBlank(organizationNumber)) {
			return toInvoiceDetails(dataWarehouseReaderClient.getInvoiceDetails(parseLong(invoiceNumber)));
		}
		return toInvoiceDetails(dataWarehouseReaderClient.getInvoiceDetails(organizationNumber, parseLong(invoiceNumber)));
	}
}

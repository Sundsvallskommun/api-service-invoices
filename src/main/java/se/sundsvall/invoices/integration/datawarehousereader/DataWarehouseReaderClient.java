package se.sundsvall.invoices.integration.datawarehousereader;

import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;
import generated.se.sundsvall.datawarehousereader.InvoiceDetail;
import generated.se.sundsvall.datawarehousereader.InvoiceParameters;
import generated.se.sundsvall.datawarehousereader.InvoiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import se.sundsvall.invoices.integration.datawarehousereader.configuration.DataWarehouseReaderConfiguration;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static se.sundsvall.invoices.integration.datawarehousereader.configuration.DataWarehouseReaderConfiguration.CLIENT_ID;

@FeignClient(name = CLIENT_ID, url = "${integration.datawarehousereader.url}", configuration = DataWarehouseReaderConfiguration.class)
public interface DataWarehouseReaderClient {

	/**
	 * Get customer engagements matching sent in partyIds
	 *
	 * @param municipalityId a municipalityId.
	 * @param partyIds a List of partyIds.
	 * @return a customerEngagementResponse
	 */
	@GetMapping(path = "/{municipalityId}/customer/engagements", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	CustomerEngagementResponse getCustomerEngagements(@PathVariable("municipalityId") String municipalityId, @RequestParam(value = "partyId") List<String> partyIds);

	/**
	 * Get invoices found by searchParams.
	 *
	 * @param municipalityId a municipalityId.
	 * @param invoiceParameters with attributes for searching invoices.
	 * @return an invoiceResponse
	 */
	@GetMapping(path = "/{municipalityId}/invoices", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	InvoiceResponse getInvoices(@PathVariable("municipalityId") String municipalityId, @SpringQueryMap InvoiceParameters invoiceParameters);

	/**
	 * Get invoice-details of an invoice issued by a specific organization.
	 *
	 * @param municipalityId     a municipalityId.
	 * @param organizationNumber organizationNumber of invoice issuer
	 * @param invoiceNumber      id of invoice.
	 * @return a list of invoices
	 */
	@GetMapping(path = "/{municipalityId}/invoices/{organizationNumber}/{invoiceNumber}/details/", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	List<InvoiceDetail> getInvoiceDetails(
		@PathVariable("municipalityId") String municipalityId,
		@PathVariable(name = "organizationNumber") String organizationNumber,
		@PathVariable(name = "invoiceNumber") long invoiceNumber);
}

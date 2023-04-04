package se.sundsvall.invoices.integration.datawarehousereader;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static se.sundsvall.invoices.integration.datawarehousereader.configuration.DataWarehouseReaderConfiguration.CLIENT_ID;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;
import generated.se.sundsvall.datawarehousereader.InvoiceDetail;
import generated.se.sundsvall.datawarehousereader.InvoiceParameters;
import generated.se.sundsvall.datawarehousereader.InvoiceResponse;
import se.sundsvall.invoices.integration.datawarehousereader.configuration.DataWarehouseReaderConfiguration;

@FeignClient(name = CLIENT_ID, url = "${integration.datawarehousereader.url}", configuration = DataWarehouseReaderConfiguration.class)
public interface DataWarehouseReaderClient {

	/**
	 * Get customer engagements matching sent in partyIds
	 *
	 * @param partyIds
	 * @return a customerEngagementResponse
	 */
	@GetMapping(path = "customer/engagements", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	CustomerEngagementResponse getCustomerEngagements(@RequestParam(value = "partyId") List<String> partyIds);

	/**
	 * Get invoices found by searchParams.
	 *
	 * @param invoiceParameters with attributes for searching invoices.
	 * @return an invoiceResponse
	 */
	@GetMapping(path = "invoices", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	InvoiceResponse getInvoices(@SpringQueryMap InvoiceParameters invoiceParameters);

	/**
	 * Get invoice-details of an invoice issued by a specific organization.
	 *
	 * @param organizationNumber organizationNumber of invoice issuer
	 * @param invoiceNumber      id of invoice.
	 * @return a list of invoices
	 */
	@GetMapping(path = "/invoices/{organizationNumber}/{invoiceNumber}/details/", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	List<InvoiceDetail> getInvoiceDetails(@PathVariable(name = "organizationNumber") String organizationNumber, @PathVariable(name = "invoiceNumber") long invoiceNumber);
}

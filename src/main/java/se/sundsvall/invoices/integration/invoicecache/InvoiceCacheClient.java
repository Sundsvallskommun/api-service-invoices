package se.sundsvall.invoices.integration.invoicecache;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static se.sundsvall.invoices.integration.invoicecache.configuration.InvoiceCacheConfiguration.CLIENT_REGISTRATION_ID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import generated.se.sundsvall.invoicecache.InvoiceFilterRequest;
import generated.se.sundsvall.invoicecache.InvoicePdf;
import generated.se.sundsvall.invoicecache.InvoicesResponse;
import se.sundsvall.invoices.integration.invoicecache.configuration.InvoiceCacheConfiguration;

@FeignClient(name = CLIENT_REGISTRATION_ID, url = "${integration.invoicecache.url}", configuration = InvoiceCacheConfiguration.class)
public interface InvoiceCacheClient {

	/**
	 * Get invoices.
	 * 
	 * @param invoiceParameters with attributes for searching/filtering invoices.
	 * @return an invoicesResponse
	 */
	@GetMapping(path = "invoices", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	InvoicesResponse getInvoices(@SpringQueryMap InvoiceFilterRequest invoiceParameters);

	/**
	 * Get invoice pdf
	 * 
	 * @param issuerLegalId legal id for issuer of the invoice
	 * @param invoiceNumber invoicenumber för the invoice
	 * @return an invoicePdf
	 */
	@GetMapping(path = "/invoices/{issuerlegalid}/{invoicenumber}/pdf", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	InvoicePdf getInvoicePdf(@PathVariable(name = "issuerlegalid") String issuerLegalId, @PathVariable(name = "invoicenumber") String invoiceNumber);
}

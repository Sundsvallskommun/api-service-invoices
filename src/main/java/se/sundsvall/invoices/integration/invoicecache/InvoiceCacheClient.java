package se.sundsvall.invoices.integration.invoicecache;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static se.sundsvall.invoices.integration.invoicecache.configuration.InvoiceCacheConfiguration.CLIENT_REGISTRATION_ID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import generated.se.sundsvall.invoicecache.InvoiceRequest;
import generated.se.sundsvall.invoicecache.InvoicesResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import se.sundsvall.invoices.integration.invoicecache.configuration.InvoiceCacheConfiguration;

@FeignClient(name = CLIENT_REGISTRATION_ID, url = "${integration.invoicecache.url}", configuration = InvoiceCacheConfiguration.class)
@CircuitBreaker(name = CLIENT_REGISTRATION_ID)
public interface InvoiceCacheClient {

	/**
	 * Get invoices.
	 * 
	 * @param invoiceParameters with attributes for searching/filtering invoices.
	 * @return an invoicesResponse
	 */
	@GetMapping(path = "getInvoices", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	InvoicesResponse getInvoices(@SpringQueryMap InvoiceRequest invoiceParameters);
}

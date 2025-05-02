package se.sundsvall.invoices.integration.idata;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static se.sundsvall.invoices.integration.idata.configuration.IdataConfiguration.CLIENT_ID;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.sundsvall.invoices.integration.idata.configuration.IdataConfiguration;

@FeignClient(name = CLIENT_ID, url = "${integration.idata.url}", configuration = IdataConfiguration.class)
@CircuitBreaker(name = CLIENT_ID)
public interface IdataClient {

	@GetMapping(produces = APPLICATION_JSON_VALUE)
	byte[] getInvoice(
		@RequestParam(name = "customerno") String customerNumber,
		@RequestParam(name = "invoiceno") String invoiceNumber);

}

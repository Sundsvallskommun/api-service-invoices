package se.sundsvall.invoices.integration.idata;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.sundsvall.invoices.integration.idata.configuration.IdataConfiguration;

import static org.springframework.http.MediaType.ALL_VALUE;
import static se.sundsvall.invoices.integration.idata.configuration.IdataConfiguration.CLIENT_ID;

@FeignClient(name = CLIENT_ID, url = "${integration.idata.url}", configuration = IdataConfiguration.class)
@CircuitBreaker(name = CLIENT_ID)
public interface IdataClient {

	@GetMapping(produces = ALL_VALUE)
	byte[] getInvoice(
		@RequestParam(name = "customerno") String customerNumber,
		@RequestParam(name = "invoiceno") String invoiceNumber);

}

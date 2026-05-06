package se.sundsvall.invoices.integration.datawarehousereader;

import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;
import generated.se.sundsvall.datawarehousereader.CustomerInvoiceResponse;
import generated.se.sundsvall.datawarehousereader.CustomerType;
import generated.se.sundsvall.datawarehousereader.Direction;
import generated.se.sundsvall.datawarehousereader.InvoiceDetail;
import generated.se.sundsvall.datawarehousereader.InvoiceResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.time.LocalDate;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import se.sundsvall.invoices.integration.datawarehousereader.configuration.DataWarehouseReaderConfiguration;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static se.sundsvall.invoices.integration.datawarehousereader.configuration.DataWarehouseReaderConfiguration.CLIENT_ID;

@FeignClient(
	name = CLIENT_ID,
	url = "${integration.datawarehousereader.url}",
	configuration = DataWarehouseReaderConfiguration.class)
@CircuitBreaker(name = CLIENT_ID)
public interface DataWarehouseReaderClient {

	/**
	 * Get customer engagements matching sent in partyIds
	 *
	 * @param  municipalityId a municipalityId.
	 * @param  partyIds       a List of partyIds.
	 * @return                a customerEngagementResponse
	 */
	@GetMapping(path = "/{municipalityId}/customer/engagements", produces = {
		APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE
	})
	CustomerEngagementResponse getCustomerEngagements(@PathVariable String municipalityId, @RequestParam(value = "partyId") List<String> partyIds);

	/**
	 * Get invoices found by searchParams.
	 *
	 * @param  municipalityId a municipalityId.
	 * @return                an invoiceResponse
	 */
	@GetMapping(path = "/{municipalityId}/invoices", produces = {
		APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE
	})
	InvoiceResponse getInvoices(
		@PathVariable String municipalityId,
		@RequestParam(value = "customerNumber", required = false) List<String> customerNumber,
		@RequestParam(value = "customerType", required = false) CustomerType customerType,
		@RequestParam(value = "facilityIds", required = false) List<String> facilityIds,
		@RequestParam(value = "invoiceNumber", required = false) Long invoiceNumber,
		@RequestParam(value = "invoiceDateFrom", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate invoiceDateFrom,
		@RequestParam(value = "invoiceDateTo", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate invoiceDateTo,
		@RequestParam(value = "invoiceName", required = false) String invoiceName,
		@RequestParam(value = "invoiceType", required = false) String invoiceType,
		@RequestParam(value = "invoiceStatus", required = false) String invoiceStatus,
		@RequestParam(value = "ocrNumber", required = false) Long ocrNumber,
		@RequestParam(value = "dueDateFrom", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dueDateFrom,
		@RequestParam(value = "dueDateTo", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dueDateTo,
		@RequestParam(value = "organizationGroup", required = false) String organizationGroup,
		@RequestParam(value = "organizationNumbers", required = false) List<String> organizationNumbers,
		@RequestParam(value = "administration", required = false) String administration,
		@RequestParam(value = "sortBy", required = false) List<String> sortBy,
		@RequestParam(value = "sortDirection", required = false) Direction sortDirection,
		@RequestParam(value = "page", required = false) Integer page,
		@RequestParam(value = "limit", required = false) Integer limit);

	/**
	 * Get invoice-details of an invoice issued by a specific organization.
	 *
	 * @param  municipalityId     a municipalityId.
	 * @param  organizationNumber organizationNumber of invoice issuer
	 * @param  invoiceNumber      id of invoice.
	 * @return                    a list of invoices
	 */
	@GetMapping(path = "/{municipalityId}/invoices/{organizationNumber}/{invoiceNumber}/details", produces = {
		APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE
	})
	List<InvoiceDetail> getInvoiceDetails(
		@PathVariable String municipalityId,
		@PathVariable String organizationNumber,
		@PathVariable long invoiceNumber);

	/**
	 * Get invoices for a customer.
	 *
	 * @param  municipalityId  a municipalityId.
	 * @param  customerNumber  the customer number.
	 * @param  organizationIds optional list of organization ids of invoice issuers.
	 * @param  periodFrom      optional earliest invoice period start.
	 * @param  periodTo        optional latest invoice period end.
	 * @param  sortBy          optional column to sort by.
	 * @param  page            optional page number.
	 * @param  limit           optional result size per page.
	 * @return                 a customerInvoiceResponse
	 */
	@GetMapping(path = "/{municipalityId}/invoices/customers/{customerNumber}", produces = {
		APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE
	})
	CustomerInvoiceResponse getInvoicesForCustomer(
		@PathVariable String municipalityId,
		@PathVariable String customerNumber,
		@RequestParam(value = "organizationIds", required = false) List<String> organizationIds,
		@RequestParam(value = "periodFrom", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate periodFrom,
		@RequestParam(value = "periodTo", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate periodTo,
		@RequestParam(value = "sortBy", required = false) String sortBy,
		@RequestParam(value = "page", required = false) Integer page,
		@RequestParam(value = "limit", required = false) Integer limit);
}

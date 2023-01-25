package se.sundsvall.invoices.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.violations.ConstraintViolationProblem;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import se.sundsvall.dept44.common.validators.annotation.ValidOrganizationNumber;
import se.sundsvall.invoices.api.model.InvoiceDetailsResponse;
import se.sundsvall.invoices.api.model.InvoiceOrigin;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.api.model.PdfInvoice;
import se.sundsvall.invoices.service.InvoicesService;

@RestController
@Validated
@Tag(name = "Invoices", description = "Service that delivers invoice information")
public class InvoicesResource {

	@Autowired
	private InvoicesService invoicesService;

	@GetMapping(value = "/{invoiceOrigin}", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	@Operation(summary = "Returns invoices matching sent in search parameters")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvoicesResponse.class)))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "502", description = "Bad Gateway", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<InvoicesResponse> getInvoices(
		@PathVariable(name = "invoiceOrigin") InvoiceOrigin invoiceOrigin,
		@Valid InvoicesParameters searchParams) {

		return ok(invoicesService.getInvoices(invoiceOrigin, searchParams));
	}

	@Deprecated(since = "2022-11-10", forRemoval = true)
	@Hidden
	@GetMapping(value = "/details/{invoiceNumber}", produces = APPLICATION_JSON_VALUE)
	@Operation(summary = "Returns invoice-details of an invoice")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvoiceDetailsResponse.class)))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "502", description = "Bad Gateway", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<InvoiceDetailsResponse> getDeprecatedInvoiceDetails(
		@Parameter(name = "invoiceNumber", description = "Id of invoice", example = "333444", required = true) @NotBlank @PathVariable("invoiceNumber") String invoiceNumber) {

		return ok(InvoiceDetailsResponse.create().withDetails(invoicesService.getInvoiceDetails(null, invoiceNumber)));
	}

	@GetMapping(value = "/COMMERCIAL/{organizationNumber}/{invoiceNumber}/details", produces = APPLICATION_JSON_VALUE)
	@Operation(summary = "Returns invoice-details of an invoice")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvoiceDetailsResponse.class)))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "502", description = "Bad Gateway", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<InvoiceDetailsResponse> getInvoiceDetails(
		@Parameter(name = "organizationNumber", description = "Organization number of invoice issuer", example = "5565272223", required = true) @PathVariable(name = "organizationNumber") @ValidOrganizationNumber String organizationNumber,
		@Parameter(name = "invoiceNumber", description = "Id of invoice", example = "333444", required = true) @NotBlank @PathVariable("invoiceNumber") String invoiceNumber) {

		return ok(InvoiceDetailsResponse.create().withDetails(invoicesService.getInvoiceDetails(organizationNumber, invoiceNumber)));
	}

	@GetMapping(value = "/{invoiceOrigin}/{organizationNumber}/{invoiceNumber}/pdf", produces = APPLICATION_JSON_VALUE)
	@Operation(summary = "Returns invoice in pdf-format")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = PdfInvoice.class)))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "502", description = "Bad Gateway", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<PdfInvoice> getPdfInvoice(
		@Parameter(name = "organizationNumber", description = "Organization number of invoice issuer", example = "5565272223", required = true) @PathVariable(name = "organizationNumber") @ValidOrganizationNumber String organizationNumber,
		@PathVariable(name = "invoiceOrigin") InvoiceOrigin invoiceOrigin,
		@Parameter(name = "invoiceNumber", description = "Id of invoice", example = "333444", required = true) @PathVariable("invoiceNumber") String invoiceNumber) {

		// TODO Implement Service layer and integration towards db-layer
		throw Problem.valueOf(Status.NOT_IMPLEMENTED, "Get invoice in pdf-format is not implemented yet");
	}

}

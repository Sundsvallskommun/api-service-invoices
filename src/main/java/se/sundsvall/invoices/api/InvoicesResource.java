package se.sundsvall.invoices.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;
import se.sundsvall.dept44.common.validators.annotation.ValidMunicipalityId;
import se.sundsvall.dept44.common.validators.annotation.ValidOrganizationNumber;
import se.sundsvall.invoices.api.model.InvoiceDetailsResponse;
import se.sundsvall.invoices.api.model.InvoiceOrigin;
import se.sundsvall.invoices.api.model.InvoiceType;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.api.model.PdfInvoice;
import se.sundsvall.invoices.service.InvoicesService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/{municipalityId}")
@Validated
@Tag(name = "Invoices", description = "Service that delivers invoice information")
public class InvoicesResource {

	private final InvoicesService invoicesService;

	public InvoicesResource(final InvoicesService invoicesService) {
		this.invoicesService = invoicesService;
	}

	@GetMapping(value = "/{invoiceOrigin}", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	@Operation(summary = "Returns invoices matching sent in search parameters")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvoicesResponse.class)))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "502", description = "Bad Gateway", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<InvoicesResponse> getInvoices(
		@Parameter(name = "municipalityId", description = "Municipality ID", example = "2281") @ValidMunicipalityId @PathVariable final String municipalityId,
		@PathVariable(name = "invoiceOrigin") final InvoiceOrigin invoiceOrigin,
		@Valid final InvoicesParameters searchParams) {

		return ok(invoicesService.getInvoices(municipalityId, invoiceOrigin, searchParams));
	}

	@GetMapping(value = "/COMMERCIAL/{organizationNumber}/{invoiceNumber}/details", produces = APPLICATION_JSON_VALUE)
	@Operation(summary = "Returns invoice-details of an invoice")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvoiceDetailsResponse.class)))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "502", description = "Bad Gateway", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<InvoiceDetailsResponse> getInvoiceDetails(
		@Parameter(name = "municipalityId", description = "Municipality ID", example = "2281") @ValidMunicipalityId @PathVariable final String municipalityId,
		@Parameter(name = "organizationNumber", description = "Organization number of invoice issuer", example = "5565272223", required = true) @PathVariable(name = "organizationNumber") @ValidOrganizationNumber final String organizationNumber,
		@Parameter(name = "invoiceNumber", description = "Id of invoice", example = "333444", required = true) @NotBlank @PathVariable("invoiceNumber") final String invoiceNumber) {

		return ok(InvoiceDetailsResponse.create().withDetails(invoicesService.getInvoiceDetails(municipalityId, organizationNumber, invoiceNumber)));
	}

	@GetMapping(value = "/{invoiceOrigin}/{organizationNumber}/{invoiceNumber}/pdf", produces = APPLICATION_JSON_VALUE)
	@Operation(summary = "Returns invoice in pdf-format")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = PdfInvoice.class)))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = { Problem.class, ConstraintViolationProblem.class })))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "502", description = "Bad Gateway", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<PdfInvoice> getPdfInvoice(
		@Parameter(name = "municipalityId", description = "Municipality ID", example = "2281") @ValidMunicipalityId @PathVariable final String municipalityId,
		@Parameter(name = "organizationNumber", description = "Organization number of invoice issuer", example = "5565272223", required = true) @PathVariable(name = "organizationNumber") @ValidOrganizationNumber final String organizationNumber,
		@PathVariable(name = "invoiceOrigin") final InvoiceOrigin invoiceOrigin,
		@Parameter(name = "invoiceNumber", description = "Id of invoice", example = "333444", required = true) @NotBlank @PathVariable("invoiceNumber") final String invoiceNumber,
		@Parameter(name = "invoiceType", description = "InvoiceType filter parameter", required = false) @RequestParam(value = "invoiceType", required = false) final InvoiceType invoiceType) {

		return ok(invoicesService.getPdfInvoice(organizationNumber, invoiceNumber, invoiceType));
	}
}

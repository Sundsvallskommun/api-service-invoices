package se.sundsvall.invoices.api;

import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.zalando.problem.Status;

import se.sundsvall.invoices.Application;
import se.sundsvall.invoices.api.model.InvoiceOrigin;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.service.InvoicesService;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("junit")
class InvoicesResourceFailureTest {

	private static final String INVOICES_PATH = "/{invoiceOrigin}";
	private static final String DEPRECATED_DETAILS_PATH = "/details/{invoiceNumber}";
	private static final String DETAILS_PATH = "/COMMERCIAL/{organizationNumber}/{invoiceNumber}/details";
	private static final String PDF_PATH = "/{invoiceOrigin}/{organizationNumber}/{invoiceNumber}/pdf";

	private static final String INVOICE_NUMBER = "333";
	private static final String ORGANIZATION_NUMBER = "5565732223";
	private static final List<String> PARTY_IDS = List.of(randomUUID().toString());

	@MockBean
	private InvoicesService invoicesServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getInvoicesMissingPartyId() {

		final var invoicesParameters = InvoicesParameters.create();
		final var invoiceOrigin = InvoiceOrigin.COMMERCIAL;

		when(invoicesServiceMock.getInvoices(invoiceOrigin, invoicesParameters)).thenReturn(InvoicesResponse.create());

		webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, null, null, null, null))
				.build(Map.of("invoiceOrigin", InvoiceOrigin.COMMERCIAL)))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(Status.BAD_REQUEST.getStatusCode())
			.jsonPath("$.violations[0].field").isEqualTo("partyId")
			.jsonPath("$.violations[0].message").isEqualTo("must not be empty");

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesInvalidPartyId() {
		webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, null, null, null, List.of("invalid-uuid")))
				.build(Map.of("invoiceOrigin", InvoiceOrigin.COMMERCIAL)))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("partyId[0]")
			.jsonPath("$.violations[0].message").isEqualTo("not a valid UUID");

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesInvalidOrganizationNumber() {
		webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, null, null, "190010301234", PARTY_IDS))
				.build(Map.of("invoiceOrigin", InvoiceOrigin.COMMERCIAL)))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("organizationNumber")
			.jsonPath("$.violations[0].message").isEqualTo("must match the regular expression ^([1235789][\\d][2-9]\\d{7})$");

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesWrongFormatOfInvoiceDate() {
		webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap("22-01-01", null, null, null, PARTY_IDS))
				.build(Map.of("invoiceOrigin", InvoiceOrigin.COMMERCIAL)))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("invoiceDateFrom")
			.jsonPath("$.violations[0].message").isEqualTo("""
				Failed to convert property value of type 'java.lang.String' to required type 'java.time.LocalDate' \
				for property 'invoiceDateFrom'; nested exception is org.springframework.core.convert.ConversionFailedException: \
				Failed to convert from type [java.lang.String] to type [@org.springframework.format.annotation.DateTimeFormat \
				@io.swagger.v3.oas.annotations.media.Schema java.time.LocalDate] for value '22-01-01'; nested exception is \
				java.lang.IllegalArgumentException: Parse attempt failed for value [22-01-01]""");

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesNotValidInvoiceType() {
		webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, "Not valid", null, null, PARTY_IDS))
				.build(Map.of("invoiceOrigin", InvoiceOrigin.COMMERCIAL)))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("invoiceType")
			.jsonPath("$.violations[0].message").isEqualTo("""
				Failed to convert property value of type 'java.lang.String' to required type \
				'se.sundsvall.invoices.api.model.InvoiceType' for property 'invoiceType'; \
				nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type \
				[java.lang.String] to type [@io.swagger.v3.oas.annotations.media.Schema se.sundsvall.invoices.api.model.InvoiceType] \
				for value 'Not valid'; nested exception is java.lang.IllegalArgumentException: \
				No enum constant se.sundsvall.invoices.api.model.InvoiceType.Not valid""");

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesNotValidInvoiceStatus() {
		webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, null, "Not valid", null, PARTY_IDS))
				.build(Map.of("invoiceOrigin", InvoiceOrigin.COMMERCIAL)))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("invoiceStatus")
			.jsonPath("$.violations[0].message").isEqualTo("""
				Failed to convert property value of type 'java.lang.String' to required type 'se.sundsvall.invoices.api.model.InvoiceStatus' \
				for property 'invoiceStatus'; nested exception is org.springframework.core.convert.ConversionFailedException: \
				Failed to convert from type [java.lang.String] to type [@io.swagger.v3.oas.annotations.media.Schema \
				se.sundsvall.invoices.api.model.InvoiceStatus] for value 'Not valid'; nested exception is java.lang.IllegalArgumentException: \
				No enum constant se.sundsvall.invoices.api.model.InvoiceStatus.Not valid""");

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getDeprecatedInvoiceDetailsNoInvoiceNumber() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path(DEPRECATED_DETAILS_PATH)
			.build(Map.of("organizationNumber", ORGANIZATION_NUMBER, "invoiceNumber", " ")))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("getDeprecatedInvoiceDetails.invoiceNumber")
			.jsonPath("$.violations[0].message").isEqualTo("must not be blank");

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoiceDetailsNoInvoiceNumber() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path(DETAILS_PATH)
			.build(Map.of("organizationNumber", ORGANIZATION_NUMBER, "invoiceNumber", " ")))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("getInvoiceDetails.invoiceNumber")
			.jsonPath("$.violations[0].message").isEqualTo("must not be blank");

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoiceDetailsInvalidOrganizationNumber() {
		webTestClient.get().uri(uriBuilder -> uriBuilder.path(DETAILS_PATH)
			.build(Map.of("organizationNumber", " ", "invoiceNumber", INVOICE_NUMBER)))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo("Constraint Violation")
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.violations[0].field").isEqualTo("getInvoiceDetails.organizationNumber")
			.jsonPath("$.violations[0].message").isEqualTo("must match the regular expression ^([1235789][\\d][2-9]\\d{7})$");

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getPdfInvoiceNotValidInvoiceOrigin() {
		webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(PDF_PATH)
				.build(Map.of(
					"invoiceOrigin", "not-valid",
					"organizationNumber", ORGANIZATION_NUMBER,
					"invoiceNumber", INVOICE_NUMBER)))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody()
			.jsonPath("$.title").isEqualTo(BAD_REQUEST.getReasonPhrase())
			.jsonPath("$.status").isEqualTo(BAD_REQUEST.value())
			.jsonPath("$.detail").isEqualTo("""
				Failed to convert value of type 'java.lang.String' to required type 'se.sundsvall.invoices.api.model.InvoiceOrigin'; \
				nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] \
				to type [@org.springframework.web.bind.annotation.PathVariable se.sundsvall.invoices.api.model.InvoiceOrigin] \
				for value 'not-valid'; nested exception is about:blank{400, Bad Request, Invalid value for enum InvoiceOrigin: 'not-valid'}""");

		verifyNoInteractions(invoicesServiceMock);
	}

	private MultiValueMap<String, String> createParameterMap(String invoiceDateFrom, String invoiceType, String invoiceStatus, String organizationNumber, List<String> partyIds) {

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		ofNullable(invoiceDateFrom).ifPresent(p -> parameters.add("invoiceDateFrom", p));
		ofNullable(invoiceType).ifPresent(p -> parameters.add("invoiceType", p));
		ofNullable(invoiceStatus).ifPresent(p -> parameters.add("invoiceStatus", p));
		ofNullable(organizationNumber).ifPresent(p -> parameters.add("organizationNumber", p));
		ofNullable(partyIds).ifPresent(p -> parameters.addAll("partyId", p));

		return parameters;
	}
}

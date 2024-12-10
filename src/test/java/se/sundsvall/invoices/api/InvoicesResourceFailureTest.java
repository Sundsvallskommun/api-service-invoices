package se.sundsvall.invoices.api;

import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;
import static org.zalando.problem.Status.BAD_REQUEST;
import static se.sundsvall.invoices.api.model.InvoiceOrigin.COMMERCIAL;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;
import se.sundsvall.invoices.Application;
import se.sundsvall.invoices.service.InvoicesService;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("junit")
class InvoicesResourceFailureTest {

	private static final String INVOICES_PATH = "/{municipalityId}/{invoiceOrigin}";
	private static final String DETAILS_PATH = "/{municipalityId}/COMMERCIAL/{organizationNumber}/{invoiceNumber}/details";
	private static final String PDF_PATH = "/{municipalityId}/{invoiceOrigin}/{organizationNumber}/{invoiceNumber}/pdf";
	private static final String INVOICE_NUMBER = "333";
	private static final String ORGANIZATION_NUMBER = "5565732223";
	private static final List<String> PARTY_IDS = List.of(randomUUID().toString());
	private static final String MUNICIPALITY_ID = "2281";

	@MockitoBean
	private InvoicesService invoicesServiceMock;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getInvoicesMissingPartyId() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, null, null, null, null))
				.build(MUNICIPALITY_ID, COMMERCIAL))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("partyId", "must not be empty"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesInvalidPartyId() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, null, null, null, List.of("invalid-uuid")))
				.build(MUNICIPALITY_ID, COMMERCIAL))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("partyId[0]", "not a valid UUID"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesInvalidOrganizationNumber() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, null, null, "190010301234", PARTY_IDS))
				.build(MUNICIPALITY_ID, COMMERCIAL))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("organizationNumber", "must match the regular expression ^([1235789][\\d][2-9]\\d{7})$"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesInvalidMunicipalityId() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, null, null, null, PARTY_IDS))
				.build("invalid-municipality-id", COMMERCIAL))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("getInvoices.municipalityId", "not a valid municipality ID"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesWrongFormatOfInvoiceDate() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap("22-01-01", null, null, null, PARTY_IDS))
				.build(MUNICIPALITY_ID, COMMERCIAL))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("invoiceDateFrom",
				"Failed to convert property value of type 'java.lang.String' to required type 'java.time.LocalDate' for property 'invoiceDateFrom'; Failed to convert from type [java.lang.String] to type [@org.springframework.format.annotation.DateTimeFormat @io.swagger.v3.oas.annotations.media.Schema java.time.LocalDate] for value [22-01-01]"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesNotValidInvoiceType() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, "Not valid", null, null, PARTY_IDS))
				.build(MUNICIPALITY_ID, COMMERCIAL))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("invoiceType",
				"Failed to convert property value of type 'java.lang.String' to required type 'se.sundsvall.invoices.api.model.InvoiceType' for property 'invoiceType'; Failed to convert from type [java.lang.String] to type [@io.swagger.v3.oas.annotations.media.Schema se.sundsvall.invoices.api.model.InvoiceType] for value [Not valid]"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoicesNotValidInvoiceStatus() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, null, "Not valid", null, PARTY_IDS))
				.build(MUNICIPALITY_ID, COMMERCIAL))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("invoiceStatus",
				"Failed to convert property value of type 'java.lang.String' to required type 'se.sundsvall.invoices.api.model.InvoiceStatus' for property 'invoiceStatus'; Failed to convert from type [java.lang.String] to type [@io.swagger.v3.oas.annotations.media.Schema se.sundsvall.invoices.api.model.InvoiceStatus] for value [Not valid]"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoiceDetailsNoInvoiceNumber() {

		// Act
		final var response = webTestClient.get().uri(uriBuilder -> uriBuilder.path(DETAILS_PATH)
			.build(MUNICIPALITY_ID, ORGANIZATION_NUMBER, " "))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("getInvoiceDetails.invoiceNumber", "must not be blank"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoiceDetailsInvalidOrganizationNumber() {

		// Act
		final var response = webTestClient.get().uri(uriBuilder -> uriBuilder.path(DETAILS_PATH)
			.build(MUNICIPALITY_ID, " ", INVOICE_NUMBER))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("getInvoiceDetails.organizationNumber", "must match the regular expression ^([1235789][\\d][2-9]\\d{7})$"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getInvoiceDetailsInvalidMunicipalityId() {

		// Act
		final var response = webTestClient.get().uri(uriBuilder -> uriBuilder.path(DETAILS_PATH)
			.build("invalid-municipality-id", ORGANIZATION_NUMBER, INVOICE_NUMBER))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("getInvoiceDetails.municipalityId", "not a valid municipality ID"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getPdfInvoiceNotValidInvoiceOrigin() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(PDF_PATH)
				.build(MUNICIPALITY_ID, "not-valid", ORGANIZATION_NUMBER, INVOICE_NUMBER))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(Problem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getDetail()).isEqualTo(
			"Method parameter 'invoiceOrigin': Failed to convert value of type 'java.lang.String' to required type 'se.sundsvall.invoices.api.model.InvoiceOrigin'; Failed to convert from type [java.lang.String] to type [@org.springframework.web.bind.annotation.PathVariable se.sundsvall.invoices.api.model.InvoiceOrigin] for value [not-valid]");

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getPdfInvoiceDetailsInvalidOrganizationNumber() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(PDF_PATH)
				.build(MUNICIPALITY_ID, COMMERCIAL, "not-valid", INVOICE_NUMBER))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("getPdfInvoice.organizationNumber", "must match the regular expression ^([1235789][\\d][2-9]\\d{7})$"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getPdfInvoiceDetailsNoInvoiceNumber() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(PDF_PATH)
				.build(MUNICIPALITY_ID, COMMERCIAL, ORGANIZATION_NUMBER, " "))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("getPdfInvoice.invoiceNumber", "must not be blank"));

		verifyNoInteractions(invoicesServiceMock);
	}

	@Test
	void getPdfInvoiceDetailsInvalidMunicipalityId() {

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(PDF_PATH)
				.build("invalid-municipality-id", COMMERCIAL, ORGANIZATION_NUMBER, INVOICE_NUMBER))
			.exchange()
			.expectStatus().isBadRequest()
			.expectHeader().contentType(APPLICATION_PROBLEM_JSON_VALUE)
			.expectBody(ConstraintViolationProblem.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response.getTitle()).isEqualTo("Constraint Violation");
		assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(response.getViolations())
			.extracting(Violation::getField, Violation::getMessage)
			.containsExactly(tuple("getPdfInvoice.municipalityId", "not a valid municipality ID"));

		verifyNoInteractions(invoicesServiceMock);
	}

	private MultiValueMap<String, String> createParameterMap(final String invoiceDateFrom, final String invoiceType, final String invoiceStatus, final String organizationNumber, final List<String> partyIds) {

		final MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		ofNullable(invoiceDateFrom).ifPresent(p -> parameters.add("invoiceDateFrom", p));
		ofNullable(invoiceType).ifPresent(p -> parameters.add("invoiceType", p));
		ofNullable(invoiceStatus).ifPresent(p -> parameters.add("invoiceStatus", p));
		ofNullable(organizationNumber).ifPresent(p -> parameters.add("organizationNumber", p));
		ofNullable(partyIds).ifPresent(p -> parameters.addAll("partyId", p));

		return parameters;
	}
}

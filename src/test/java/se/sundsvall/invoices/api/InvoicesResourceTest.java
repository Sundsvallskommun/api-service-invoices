package se.sundsvall.invoices.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import se.sundsvall.invoices.Application;
import se.sundsvall.invoices.api.model.InvoiceDetail;
import se.sundsvall.invoices.api.model.InvoiceDetailsResponse;
import se.sundsvall.invoices.api.model.InvoiceOrigin;
import se.sundsvall.invoices.api.model.InvoiceStatus;
import se.sundsvall.invoices.api.model.InvoiceType;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.api.model.InvoicesResponse;
import se.sundsvall.invoices.api.model.PdfInvoice;
import se.sundsvall.invoices.service.InvoicesService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static se.sundsvall.invoices.api.model.InvoiceOrigin.COMMERCIAL;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("junit")
class InvoicesResourceTest {

	private static final String INVOICES_PATH = "/{municipalityId}/{invoiceOrigin}";
	private static final String DETAILS_PATH = "/{municipalityId}/COMMERCIAL/{organizationNumber}/{invoiceNumber}/details";
	private static final String PDF_PATH = "/{municipalityId}/{invoiceOrigin}/{organizationNumber}/{invoiceNumber}/pdf";

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_LIMIT = 100;
	private static final int PAGE = 3;
	private static final int LIMIT = 17;
	private static final List<String> PARTY_IDS = List.of(randomUUID().toString(), randomUUID().toString());
	private static final List<String> FACILITY_IDS = List.of("facilityId-1", "facilityId-2");
	private static final String INVOICE_NUMBER = "333";
	private static final LocalDate INVOICE_DATE_FROM = LocalDate.now().minusYears(2);
	private static final LocalDate INVOICE_DATE_TO = LocalDate.now().minusYears(1);
	private static final String INVOICE_NAME = "invoiceName";
	private static final InvoiceType INVOICE_TYPE = InvoiceType.INVOICE;
	private static final InvoiceStatus INVOICE_STATUS = InvoiceStatus.PAID;
	private static final String OCR_NUMBER = "444";
	private static final LocalDate DUE_DATE_FROM = LocalDate.now().minusMonths(1);
	private static final LocalDate DUE_DATE_TO = LocalDate.now();
	private static final String ORGANIZATION_GROUP = "organizationGroup";
	private static final String ORGANIZATION_NUMBER = "5522345678";
	private static final String MUNICIPALITY_ID = "2281";

	@MockBean
	private InvoicesService invoicesServiceMock;

	@Captor
	private ArgumentCaptor<InvoicesParameters> parametersCaptor;

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void getInvoicesAllParameters() {

		// Arrange
		when(invoicesServiceMock.getInvoices(anyString(), any(), any())).thenReturn(InvoicesResponse.create());

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(PAGE, LIMIT, FACILITY_IDS, INVOICE_NUMBER, INVOICE_DATE_FROM, INVOICE_DATE_TO, INVOICE_NAME,
					INVOICE_TYPE, INVOICE_STATUS, OCR_NUMBER, DUE_DATE_FROM, DUE_DATE_TO, ORGANIZATION_GROUP, ORGANIZATION_NUMBER, PARTY_IDS))
				.build(MUNICIPALITY_ID, COMMERCIAL))
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(InvoicesResponse.class)
			.returnResult()
			.getResponseBody();

		// Assert
		verify(invoicesServiceMock).getInvoices(eq(MUNICIPALITY_ID), eq(COMMERCIAL), parametersCaptor.capture());
		final InvoicesParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getDueDateFrom()).isEqualTo(DUE_DATE_FROM);
		assertThat(parameters.getDueDateTo()).isEqualTo(DUE_DATE_TO);
		assertThat(parameters.getFacilityId()).isEqualTo(FACILITY_IDS);
		assertThat(parameters.getInvoiceDateFrom()).isEqualTo(INVOICE_DATE_FROM);
		assertThat(parameters.getInvoiceDateTo()).isEqualTo(INVOICE_DATE_TO);
		assertThat(parameters.getInvoiceName()).isEqualTo(INVOICE_NAME);
		assertThat(parameters.getInvoiceNumber()).isEqualTo(INVOICE_NUMBER);
		assertThat(parameters.getInvoiceStatus()).isEqualTo(INVOICE_STATUS);
		assertThat(parameters.getInvoiceType()).isEqualTo(INVOICE_TYPE);
		assertThat(parameters.getLimit()).isEqualTo(LIMIT);
		assertThat(parameters.getOcrNumber()).isEqualTo(OCR_NUMBER);
		assertThat(parameters.getOrganizationGroup()).isEqualTo(ORGANIZATION_GROUP);
		assertThat(parameters.getOrganizationNumber()).isEqualTo(ORGANIZATION_NUMBER);
		assertThat(parameters.getPage()).isEqualTo(PAGE);
		assertThat(parameters.getPartyId()).isEqualTo(PARTY_IDS);
		assertThat(response).isNotNull().isEqualTo(InvoicesResponse.create());
	}

	@Test
	void getInvoicesOnlyMandatoryParameters() {

		// Arrange
		when(invoicesServiceMock.getInvoices(anyString(), any(), any())).thenReturn(InvoicesResponse.create());

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(INVOICES_PATH)
				.queryParams(createParameterMap(null, null, null, null, null, null, null, null, null, null, null, null, null, null, PARTY_IDS))
				.build(MUNICIPALITY_ID, COMMERCIAL))
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(InvoicesResponse.class)
			.returnResult()
			.getResponseBody();

		// Assert
		verify(invoicesServiceMock).getInvoices(eq(MUNICIPALITY_ID), eq(COMMERCIAL), parametersCaptor.capture());
		final InvoicesParameters parameters = parametersCaptor.getValue();
		assertThat(parameters.getPage()).isEqualTo(DEFAULT_PAGE);
		assertThat(parameters.getLimit()).isEqualTo(DEFAULT_LIMIT);
		assertThat(parameters.getPartyId()).isEqualTo(PARTY_IDS);
		assertThat(parameters).hasAllNullFieldsOrPropertiesExcept("limit", "page", "partyId");
		assertThat(response).isNotNull().isEqualTo(InvoicesResponse.create());
	}

	@Test
	void getInvoiceDetails() {

		// Arrange
		when(invoicesServiceMock.getInvoiceDetails(MUNICIPALITY_ID, ORGANIZATION_NUMBER, INVOICE_NUMBER)).thenReturn(List.of(InvoiceDetail.create()));

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(DETAILS_PATH)
				.build(MUNICIPALITY_ID, ORGANIZATION_NUMBER, INVOICE_NUMBER))
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(InvoiceDetailsResponse.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull().isEqualTo(InvoiceDetailsResponse.create().withDetails(List.of(InvoiceDetail.create())));
		verify(invoicesServiceMock).getInvoiceDetails(MUNICIPALITY_ID, ORGANIZATION_NUMBER, INVOICE_NUMBER);
	}

	@ParameterizedTest
	@EnumSource(value = InvoiceOrigin.class)
	void getPdfInvoice(final InvoiceOrigin origin) {

		// Arrange
		when(invoicesServiceMock.getPdfInvoice(ORGANIZATION_NUMBER, INVOICE_NUMBER, INVOICE_TYPE, MUNICIPALITY_ID)).thenReturn(PdfInvoice.create());

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(PDF_PATH).queryParam("invoiceType", INVOICE_TYPE)
				.build(MUNICIPALITY_ID, origin, ORGANIZATION_NUMBER, INVOICE_NUMBER))
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(PdfInvoice.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull().isEqualTo(PdfInvoice.create());
		verify(invoicesServiceMock).getPdfInvoice(ORGANIZATION_NUMBER, INVOICE_NUMBER, INVOICE_TYPE, MUNICIPALITY_ID);
	}

	@ParameterizedTest
	@EnumSource(value = InvoiceOrigin.class)
	void getPdfInvoiceInvoiceTypeMissing(final InvoiceOrigin origin) {

		// Arrange
		when(invoicesServiceMock.getPdfInvoice(ORGANIZATION_NUMBER, INVOICE_NUMBER, null, MUNICIPALITY_ID)).thenReturn(PdfInvoice.create());

		// Act
		final var response = webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path(PDF_PATH)
				.build(MUNICIPALITY_ID, origin, ORGANIZATION_NUMBER, INVOICE_NUMBER))
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody(PdfInvoice.class)
			.returnResult()
			.getResponseBody();

		// Assert
		assertThat(response).isNotNull().isEqualTo(PdfInvoice.create());
		verify(invoicesServiceMock).getPdfInvoice(ORGANIZATION_NUMBER, INVOICE_NUMBER, null, MUNICIPALITY_ID);
	}

	private MultiValueMap<String, String> createParameterMap(final Integer page, final Integer limit, final List<String> facilityIds, final String invoiceNumber, final LocalDate invoiceDateFrom,
		final LocalDate invoiceDateTo, final String invoiceName, final InvoiceType invoiceType, final InvoiceStatus invoiceStatus,
		final String ocrNumber, final LocalDate dueDateFrom, final LocalDate dueDateTo, final String organizationGroup,
		final String organizationNumber, final List<String> partyIds) {

		final MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

		ofNullable(page).ifPresent(p -> parameters.add("page", valueOf(p)));
		ofNullable(limit).ifPresent(p -> parameters.add("limit", valueOf(p)));
		ofNullable(facilityIds).ifPresent(p -> parameters.addAll("facilityId", p));
		ofNullable(invoiceNumber).ifPresent(p -> parameters.add("invoiceNumber", p));
		ofNullable(invoiceDateFrom).ifPresent(p -> parameters.add("invoiceDateFrom", p.format(DateTimeFormatter.ISO_LOCAL_DATE)));
		ofNullable(invoiceDateTo).ifPresent(p -> parameters.add("invoiceDateTo", p.format(DateTimeFormatter.ISO_LOCAL_DATE)));
		ofNullable(invoiceName).ifPresent(p -> parameters.add("invoiceName", p));
		ofNullable(invoiceType).ifPresent(p -> parameters.add("invoiceType", p.toString()));
		ofNullable(invoiceStatus).ifPresent(p -> parameters.add("invoiceStatus", p.toString()));
		ofNullable(ocrNumber).ifPresent(p -> parameters.add("ocrNumber", p));
		ofNullable(dueDateFrom).ifPresent(p -> parameters.add("dueDateFrom", p.format(DateTimeFormatter.ISO_LOCAL_DATE)));
		ofNullable(dueDateTo).ifPresent(p -> parameters.add("dueDateTo", p.format(DateTimeFormatter.ISO_LOCAL_DATE)));
		ofNullable(organizationGroup).ifPresent(p -> parameters.add("organizationGroup", p));
		ofNullable(organizationNumber).ifPresent(p -> parameters.add("organizationNumber", p));
		ofNullable(partyIds).ifPresent(p -> parameters.addAll("partyId", p));

		return parameters;
	}
}

package se.sundsvall.invoices.service;

import generated.se.sundsvall.datawarehousereader.CustomerEngagement;
import generated.se.sundsvall.datawarehousereader.CustomerEngagementResponse;
import generated.se.sundsvall.datawarehousereader.Direction;
import generated.se.sundsvall.datawarehousereader.Invoice;
import generated.se.sundsvall.datawarehousereader.InvoiceParameters;
import generated.se.sundsvall.datawarehousereader.InvoiceResponse;
import generated.se.sundsvall.invoicecache.Invoice.InvoiceStatusEnum;
import generated.se.sundsvall.invoicecache.Invoice.InvoiceTypeEnum;
import generated.se.sundsvall.invoicecache.InvoiceFilterRequest;
import generated.se.sundsvall.invoicecache.InvoicePdf;
import generated.se.sundsvall.invoicecache.InvoicesResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.problem.ThrowableProblem;
import se.sundsvall.invoices.api.model.InvoiceDetail;
import se.sundsvall.invoices.api.model.InvoicesParameters;
import se.sundsvall.invoices.integration.datawarehousereader.DataWarehouseReaderClient;
import se.sundsvall.invoices.integration.invoicecache.InvoiceCacheClient;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.zalando.problem.Status.NOT_FOUND;
import static se.sundsvall.invoices.api.model.InvoiceOrigin.COMMERCIAL;
import static se.sundsvall.invoices.api.model.InvoiceOrigin.PUBLIC_ADMINISTRATION;
import static se.sundsvall.invoices.api.model.InvoiceType.CREDIT_INVOICE;
import static se.sundsvall.invoices.api.model.InvoiceType.INVOICE;
import static se.sundsvall.invoices.service.mapper.InvoiceMapper.toInvoiceCacheInvoiceType;

@ExtendWith(MockitoExtension.class)
class InvoicesServiceTest {

	@Mock
	private DataWarehouseReaderClient dataWarehouseReaderClientMock;

	@Mock
	private InvoiceCacheClient invoiceCacheClientMock;

	@Mock
	private CustomerEngagementResponse customerEngagementResponseMock;

	@Mock
	private CustomerEngagement customerEngagementMock;

	@InjectMocks
	private InvoicesService invoicesService;

	@Test
	void getCommercialInvoicesSuccess() {

		final var municipalityId = "municipalityId";
		final var partyIds = List.of(randomUUID().toString(), randomUUID().toString());
		final var customerNumber_1 = "111111";
		final var customerNumber_2 = "222222";
		final var customerNumbers = List.of(customerNumber_1, customerNumber_2);
		final var organizationNumber = "5565027223";
		final var invoiceName = "invoiceName";
		final var invoiceDate = "invoiceDate";
		final var dataWarehouseReaderParameters = new InvoiceParameters()
			.page(1)
			.limit(100)
			.invoiceName(invoiceName)
			.customerNumber(customerNumbers)
			.organizationNumber(organizationNumber)
			.facilityId(null)
			.sortBy(List.of(invoiceDate))
			.sortDirection(Direction.DESC);

		when(dataWarehouseReaderClientMock.getCustomerEngagements(municipalityId, partyIds)).thenReturn(customerEngagementResponseMock);
		when(customerEngagementResponseMock.getCustomerEngagements()).thenReturn(List.of(customerEngagementMock, customerEngagementMock));
		when(customerEngagementMock.getCustomerNumber()).thenReturn(customerNumber_1, customerNumber_2);
		when(dataWarehouseReaderClientMock.getInvoices(municipalityId, dataWarehouseReaderParameters)).thenReturn(createDataWarehouseReaderInvoiceResponse());
		final var invoicesResponse = invoicesService.getInvoices(municipalityId, COMMERCIAL, InvoicesParameters.create().withInvoiceName(invoiceName).withOrganizationNumber(organizationNumber).withPartyId(partyIds));

		assertThat(invoicesResponse.getInvoices()).hasSize(2);
		assertThat(invoicesResponse.getInvoices().getFirst().getInvoiceType()).isEqualTo(INVOICE);
		assertThat(invoicesResponse.getInvoices().getLast().getInvoiceType()).isEqualTo(CREDIT_INVOICE);
		verify(dataWarehouseReaderClientMock).getCustomerEngagements(municipalityId, partyIds);
		verify(dataWarehouseReaderClientMock).getInvoices(municipalityId, dataWarehouseReaderParameters);
		verifyNoInteractions(invoiceCacheClientMock);
	}

	@Test
	void getCommercialInvoicesNoHits() {

		final var municipalityId = "municipalityId";
		final var partyIds = List.of(randomUUID().toString());
		final var customerNumber = "111111";
		final var customerNumbers = List.of(customerNumber);
		final var organizationNumber = "5565027223";
		final var invoiceName = "invoiceName";
		final var invoiceDate = "invoiceDate";
		final var dataWarehouseReaderParameters = new InvoiceParameters()
			.page(1)
			.limit(100)
			.invoiceName(invoiceName)
			.customerNumber(customerNumbers)
			.organizationNumber(organizationNumber)
			.facilityId(null)
			.sortBy(List.of(invoiceDate))
			.sortDirection(Direction.DESC);

		when(dataWarehouseReaderClientMock.getCustomerEngagements(municipalityId, partyIds)).thenReturn(customerEngagementResponseMock);
		when(customerEngagementResponseMock.getCustomerEngagements()).thenReturn(List.of(customerEngagementMock));
		when(customerEngagementMock.getCustomerNumber()).thenReturn(customerNumber);
		when(dataWarehouseReaderClientMock.getInvoices(municipalityId, dataWarehouseReaderParameters)).thenReturn(new InvoiceResponse().invoices(emptyList()).meta(createPagingAndSortingMetaData()));

		final var invoicesResponse = invoicesService.getInvoices(municipalityId, COMMERCIAL, InvoicesParameters.create().withInvoiceName(invoiceName).withOrganizationNumber(organizationNumber).withPartyId(partyIds));

		assertThat(invoicesResponse).isNotNull();
		assertThat(invoicesResponse.getInvoices()).isEmpty();
		verify(dataWarehouseReaderClientMock).getCustomerEngagements(municipalityId, partyIds);
		verify(dataWarehouseReaderClientMock).getInvoices(municipalityId, dataWarehouseReaderParameters);
		verifyNoInteractions(invoiceCacheClientMock);
	}

	@Test
	void getCommercialInvoicesNoEngagements() {

		final var municipalityId = "municipalityId";
		final var partyIds = List.of(randomUUID().toString(), randomUUID().toString());
		final var organizationNumber = "5565027223";
		final var invoiceName = "invoiceName";
		final var invoiceParameters = InvoicesParameters.create()
			.withInvoiceName(invoiceName)
			.withOrganizationNumber(organizationNumber)
			.withPartyId(partyIds);

		when(dataWarehouseReaderClientMock.getCustomerEngagements(municipalityId, partyIds)).thenReturn(customerEngagementResponseMock);
		when(customerEngagementResponseMock.getCustomerEngagements()).thenReturn(emptyList());

		final ThrowableProblem e = assertThrows(ThrowableProblem.class, () -> invoicesService.getInvoices(municipalityId, COMMERCIAL, invoiceParameters));

		assertThat(e.getStatus()).isEqualTo(NOT_FOUND);
		assertThat(e.getMessage()).isEqualTo("Not Found: No engagements found for partyIds: '" + partyIds + "'");
		verify(dataWarehouseReaderClientMock).getCustomerEngagements(municipalityId, partyIds);
		verify(dataWarehouseReaderClientMock, never()).getInvoices(anyString(), any());
		verifyNoInteractions(invoiceCacheClientMock);
	}

	@Test
	void getPublicAdministrationInvoicesSuccess() {

		final var municipalityId = "municipalityId";
		final var partyIds = List.of(randomUUID().toString(), randomUUID().toString());
		final var ocrNumber = "111111";
		final var invoiceNumber = "123";
		final var invoiceCacheParameters = new InvoiceFilterRequest().invoiceNumbers(List.of(invoiceNumber)).ocrNumber(ocrNumber).partyIds(partyIds);

		when(invoiceCacheClientMock.getInvoices(municipalityId, invoiceCacheParameters)).thenReturn(createInvoiceCacheInvoicesResponse());

		final var invoicesResponse = invoicesService.getInvoices(municipalityId, PUBLIC_ADMINISTRATION, InvoicesParameters.create().withOcrNumber(ocrNumber).withInvoiceNumber(invoiceNumber).withPartyId(partyIds));

		assertThat(invoicesResponse.getInvoices()).hasSize(2);
		assertThat(invoicesResponse.getInvoices().getFirst().getInvoiceType()).isEqualTo(CREDIT_INVOICE);
		assertThat(invoicesResponse.getInvoices().getLast().getInvoiceType()).isEqualTo(INVOICE);
		verify(invoiceCacheClientMock).getInvoices(municipalityId, invoiceCacheParameters);
		verifyNoInteractions(dataWarehouseReaderClientMock);
	}

	@Test
	void getPublicAdministrationInvoicesNoHits() {

		final var municipalityId = "municipalityId";
		final var partyIds = List.of(randomUUID().toString(), randomUUID().toString());
		final var ocrNumber = "111111";
		final var invoiceNumber = "123";
		final var invoiceCacheParameters = new InvoiceFilterRequest().invoiceNumbers(List.of(invoiceNumber)).ocrNumber(ocrNumber).partyIds(partyIds);

		when(invoiceCacheClientMock.getInvoices(municipalityId, invoiceCacheParameters)).thenReturn(createInvoiceCacheInvoicesResponse().invoices(emptyList()));

		final var invoicesResponse = invoicesService.getInvoices(municipalityId, PUBLIC_ADMINISTRATION, InvoicesParameters.create().withOcrNumber(ocrNumber).withInvoiceNumber(invoiceNumber).withPartyId(partyIds));

		assertThat(invoicesResponse.getInvoices()).isEmpty();
		verify(invoiceCacheClientMock).getInvoices(municipalityId, invoiceCacheParameters);
		verifyNoInteractions(dataWarehouseReaderClientMock);
	}

	@Test
	void getInvoiceDetailsSuccess() {

		final var municipalityId = "municipalityId";
		final var organizationNumber = "5523456789";
		final var invoiceNumber = "111222";
		final var expectedInvoiceDetail = new InvoiceDetail();
		expectedInvoiceDetail.setAmount(10.45f);
		expectedInvoiceDetail.setQuantity(2);

		when(dataWarehouseReaderClientMock.getInvoiceDetails(municipalityId, organizationNumber, Long.parseLong(invoiceNumber))).thenReturn(List.of(createDataWarehouseReaderInvoiceDetail(invoiceNumber)));

		final var invoiceDetails = invoicesService.getInvoiceDetails(municipalityId, organizationNumber, invoiceNumber);

		assertThat(invoiceDetails).hasSize(1);
		assertThat(invoiceDetails.getFirst()).hasFieldOrPropertyWithValue("amount", expectedInvoiceDetail.getAmount());
		assertThat(invoiceDetails.getFirst()).hasFieldOrPropertyWithValue("quantity", expectedInvoiceDetail.getQuantity());
		verify(dataWarehouseReaderClientMock).getInvoiceDetails(municipalityId, organizationNumber, Long.parseLong(invoiceNumber));
	}

	@Test
	void getPdfInvoice() {
		final var organizationNumber = "5523456789";
		final var invoiceNumber = "111222";
		final var invoiceName = "invoiceName";
		final var invoiceType = CREDIT_INVOICE;
		final var content = "content".getBytes(StandardCharsets.UTF_8);
		final var municipalityId = "municipalityId";

		when(invoiceCacheClientMock.getInvoicePdf(municipalityId, organizationNumber, invoiceNumber, toInvoiceCacheInvoiceType(invoiceType))).thenReturn(new InvoicePdf().name(invoiceName).content(Base64.getEncoder().encodeToString(content)));

		final var pdfInvoice = invoicesService.getPdfInvoice(organizationNumber, invoiceNumber, invoiceType, municipalityId);

		assertThat(pdfInvoice).isNotNull();
		assertThat(pdfInvoice.getFileName()).isEqualTo(invoiceName);
		assertThat(pdfInvoice.getFile()).isEqualTo(content);
		verify(invoiceCacheClientMock).getInvoicePdf(municipalityId, organizationNumber, invoiceNumber, toInvoiceCacheInvoiceType(invoiceType));
	}

	private InvoiceResponse createDataWarehouseReaderInvoiceResponse() {
		final var invoiceName = "invoiceName";
		return new InvoiceResponse()
			.invoices(List.of(
				new Invoice()
					.invoiceName(invoiceName)
					.invoiceType("Faktura")
					.invoiceStatus("Betalad"),
				new Invoice()
					.invoiceName(invoiceName)
					.invoiceType("Kreditfaktura")
					.invoiceStatus("Inkasso")))
			.meta(createPagingAndSortingMetaData());
	}

	private InvoicesResponse createInvoiceCacheInvoicesResponse() {
		return new generated.se.sundsvall.invoicecache.InvoicesResponse()
			.invoices(List.of(
				new generated.se.sundsvall.invoicecache.Invoice()
					.invoiceType(InvoiceTypeEnum.CREDIT_INVOICE)
					.invoiceStatus(InvoiceStatusEnum.PAID),
				new generated.se.sundsvall.invoicecache.Invoice()
					.invoiceType(InvoiceTypeEnum.INVOICE)
					.invoiceStatus(InvoiceStatusEnum.PAID)))
			.meta(createInvoiceCacheMetaData());
	}

	private generated.se.sundsvall.invoicecache.MetaData createInvoiceCacheMetaData() {
		return new generated.se.sundsvall.invoicecache.MetaData()
			.count(10)
			.limit(100)
			.page(1)
			.totalPages(100)
			.totalRecords(1000L);
	}

	private generated.se.sundsvall.datawarehousereader.PagingAndSortingMetaData createPagingAndSortingMetaData() {
		return new generated.se.sundsvall.datawarehousereader.PagingAndSortingMetaData()
			.count(10)
			.limit(100)
			.page(1)
			.totalPages(100)
			.totalRecords(1000L);
	}

	private generated.se.sundsvall.datawarehousereader.InvoiceDetail createDataWarehouseReaderInvoiceDetail(final String invoiceNumber) {
		return new generated.se.sundsvall.datawarehousereader.InvoiceDetail()
			.invoiceNumber(Long.valueOf(invoiceNumber))
			.amount(BigDecimal.valueOf(10.45d))
			.quantity(2d);
	}
}

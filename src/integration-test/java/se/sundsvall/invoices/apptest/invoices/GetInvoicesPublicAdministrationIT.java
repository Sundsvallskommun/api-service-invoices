package se.sundsvall.invoices.apptest.invoices;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;

import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.invoices.Application;

@WireMockAppTestSuite(files = "classpath:/GetInvoicesPublicAdministration/", classes = Application.class)
class GetInvoicesPublicAdministrationIT extends AbstractAppTest {
	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test01_getInvoicesOnlyRequired() {
		setupCall()
			.withServicePath("/public_administration?partyId=AC653C32-B26C-47E8-8C2E-3B18C1B5879C")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_getInvoicesAllAttributes() {
		setupCall()
			.withServicePath("/public_administration?partyId=EC114D35-A46C-48EF-599E-7E32B5B5939C" +
				"&page=1" +
				"&limit=100" +
				"&invoiceNumber=766763197" +
				"&invoiceDateFrom=2022-01-01" +
				"&invoiceDateTo=2022-12-21" +
				"&invoiceName=766763197.pdf" +
				"&ocrNumber=766763197" +
				"&dueDateFrom=2022-01-02" +
				"&dueDateTo=2022-12-22")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_getInvoicesForMultiplePartyIds() {
		setupCall()
			.withServicePath("/public_administration?partyId=88c8c596-e545-4c42-aa2f-2f61a150613b&partyId=5256b101-4df3-4990-8ac8-6e49478bde4f")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}

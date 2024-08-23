package se.sundsvall.invoices.apptest.details;

import org.junit.jupiter.api.Test;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.invoices.Application;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.OK;

@WireMockAppTestSuite(files = "classpath:/GetInvoiceDetails/", classes = Application.class)
class GetInvoiceDetailsIT extends AbstractAppTest {
	private static final String DETAILS_RESOURCE = "/details";
	private static final String DETAILS_PATH = "/2281/COMMERCIAL/";
	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test1_getInvoiceDetailsSuccess() {
		final var organizationNumber = "5565257545";
		final var invoiceNumber = "111222";

		setupCall()
			.withServicePath(DETAILS_PATH + organizationNumber + "/" + invoiceNumber + DETAILS_RESOURCE)
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test2_getInvoiceDetailsNotFound() {
		final var organizationNumber = "5565257545";
		final var invoiceNumber = "666";

		setupCall()
			.withServicePath(DETAILS_PATH + organizationNumber + "/" + invoiceNumber + DETAILS_RESOURCE)
			.withHttpMethod(GET)
			.withExpectedResponseStatus(BAD_GATEWAY)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}

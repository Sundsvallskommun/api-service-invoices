package se.sundsvall.invoices.apptest.pdf;

import org.junit.jupiter.api.Test;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.invoices.Application;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.OK;

@WireMockAppTestSuite(files = "classpath:/GetInvoicePdf/", classes = Application.class)
class GetInvoicePdfIT extends AbstractAppTest {
	private static final String PATH_PREFIX = "/2281/COMMERCIAL/";
	private static final String PATH_SUFFIX = "/pdf";
	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test01_getInvoicePdf() {
		final var organizationNumber = "5565257545";
		final var invoiceNumber = "111222";

		setupCall()
			.withServicePath(PATH_PREFIX + organizationNumber + "/" + invoiceNumber + PATH_SUFFIX)
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_getInvoicePdfNotFound() {
		final var organizationNumber = "5565257545";
		final var invoiceNumber = "666";

		setupCall()
			.withServicePath(PATH_PREFIX + organizationNumber + "/" + invoiceNumber + PATH_SUFFIX)
			.withHttpMethod(GET)
			.withExpectedResponseStatus(BAD_GATEWAY)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_getInvoicePdfFilteredByInvoiceType() {
		final var organizationNumber = "5565257545";
		final var invoiceNumber = "111222";

		setupCall()
			.withServicePath(PATH_PREFIX + organizationNumber + "/" + invoiceNumber + PATH_SUFFIX + "?invoiceType=INVOICE")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}

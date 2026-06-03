package se.sundsvall.invoices.apptest.invoices;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.invoices.Application;

@WireMockAppTestSuite(files = "classpath:/GetInvoicesForCustomer/", classes = Application.class)
class GetInvoicesForCustomerIT extends AbstractAppTest {
	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test01_getInvoicesForCustomerOnlyRequired() {
		setupCall()
			.withServicePath("/2281/COMMERCIAL/customers/invoices?customerNumbers=600606")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	// All query parameters populated, including multiple customer/facility ids. Upstream detail intentionally
	// omits administration/facilityId to verify the mapper passes nulls through cleanly.
	void test02_getInvoicesForCustomerAllParameters() {
		setupCall()
			.withServicePath("/2281/COMMERCIAL/customers/invoices" +
				"?customerNumbers=600606" +
				"&customerNumbers=216870" +
				"&organizationNumbers=5565027223" +
				"&facilityIds=735999109425048010" +
				"&status=PAID" +
				"&periodFrom=2025-01-01" +
				"&periodTo=2025-12-31" +
				"&sortBy=periodFrom" +
				"&page=1" +
				"&limit=100")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_getInvoicesForCustomerUpstreamError() {
		setupCall()
			.withServicePath("/2281/COMMERCIAL/customers/invoices?customerNumbers=999999")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(BAD_GATEWAY)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	// DataWarehouseReader's Feign decoder is configured to pass 404 through unchanged (rather than wrap as 502).
	// This test pins that behavior: an upstream 404 surfaces as a 404 to the caller.
	void test04_getInvoicesForCustomerNotFound() {
		setupCall()
			.withServicePath("/2281/COMMERCIAL/customers/invoices?customerNumbers=777777")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(NOT_FOUND)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}

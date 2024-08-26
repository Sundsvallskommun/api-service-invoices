package se.sundsvall.invoices.apptest.invoices;

import org.junit.jupiter.api.Test;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.invoices.Application;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@WireMockAppTestSuite(files = "classpath:/GetInvoicesCommercial/", classes = Application.class)
class GetInvoicesCommercialIT extends AbstractAppTest {
	private static final String RESPONSE_FILE = "response.json";

	@Test
	void test01_getInvoicesOnlyRequired() {
		setupCall()
			.withServicePath("/2281/commercial?partyId=AC653C32-B26C-47E8-8C2E-3B18C1B5879C")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test02_getInvoicesAllAttributes() {
		setupCall()
			.withServicePath("/2281/commercial?partyId=EC114D35-A46C-48EF-599E-7E32B5B5939C" +
				"&page=1" +
				"&limit=100" +
				"&facilityId=735999226000" +
				"&invoiceNumber=766763197" +
				"&invoiceDateFrom=2019-10-01" +
				"&invoiceDateTo=2019-10-31" +
				"&invoiceName=766763197.pdf" +
				"&invoiceType=INVOICE" +
				"&invoiceStatus=SENT" +
				"&ocrNumber=766763197" +
				"&dueDateFrom=2019-10-01" +
				"&dueDateTo=2019-11-01" +
				"&organizationGroup=stadsbacken" +
				"&organizationNumber=5564786647")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test03_customerWithNoEngagements() {
		setupCall()
			.withServicePath("/2281/commercial?partyId=EC114D35-A46C-48EF-599E-7E32B5B5939D")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(NOT_FOUND)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test04_getInvoicesForMultiplePartyIds() {
		setupCall()
			.withServicePath("/2281/commercial?partyId=AC653C32-B26C-47E8-8C2E-3B18C1B5879C" +
				"&partyId=174BB37E-1665-C25B-7CA5-1D18C1B5879D")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test05_getInvoicesForMultipleFacilityIds() {
		setupCall()
			.withServicePath("/2281/commercial?partyId=AC653C32-B26C-47E8-8C2E-4D18C1B5879C" +
				"&facilityId=735999109242413886" +
				"&facilityId=735999109215204886")
			.withHttpMethod(GET)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(RESPONSE_FILE)
			.sendRequestAndVerifyResponse();
	}
}

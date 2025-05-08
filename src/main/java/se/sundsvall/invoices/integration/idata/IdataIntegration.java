package se.sundsvall.invoices.integration.idata;

import org.springframework.stereotype.Component;
import se.sundsvall.invoices.integration.idata.configuration.IdataProperties;

@Component
public class IdataIntegration {

	private final IdataProperties properties;
	private final IdataClient idataClient;

	public IdataIntegration(IdataProperties properties, IdataClient idataClient) {
		this.properties = properties;
		this.idataClient = idataClient;
	}

	public byte[] getInvoice(String invoiceNumber) {
		return idataClient.getInvoice(properties.customerNumber(), invoiceNumber);
	}
}

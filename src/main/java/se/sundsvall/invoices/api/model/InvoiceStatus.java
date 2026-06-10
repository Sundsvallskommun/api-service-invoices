package se.sundsvall.invoices.api.model;

// Internal backing enum for the API's invoiceStatus String field; validated against via @MemberOf and used for upstream value mapping.
public enum InvoiceStatus {
	PAID,
	SENT,
	PARTIALLY_PAID,
	DEBT_COLLECTION,
	PAID_TOO_MUCH,
	REMINDER,
	VOID,
	CREDITED,
	WRITTEN_OFF,
	UNKNOWN
}

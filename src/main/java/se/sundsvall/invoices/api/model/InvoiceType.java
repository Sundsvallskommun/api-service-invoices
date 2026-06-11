package se.sundsvall.invoices.api.model;

// Internal backing enum for the API's invoiceType String field; validated against via @MemberOf and used for upstream value mapping.
public enum InvoiceType {
	INVOICE,
	CREDIT_INVOICE,
	START_INVOICE,
	FINAL_INVOICE,
	DIRECT_DEBIT,
	SELF_INVOICE,
	REMINDER,
	CONSOLIDATED_INVOICE,
	INTERNAL_INVOICE,
	OFFSET_INVOICE,
	UNKNOWN
}

package se.sundsvall.invoices.api.model;

// Internal backing enum for the API's invoiceOrigin String path variable; validated against via @MemberOf and used to route commercial vs public-administration requests.
public enum InvoiceOrigin {
	COMMERCIAL,
	PUBLIC_ADMINISTRATION
}

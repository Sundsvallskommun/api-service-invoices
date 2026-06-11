package se.sundsvall.invoices.api.model;

// Internal backing enum for the API's customerType String field; validated against via @MemberOf and used for upstream value mapping.
public enum CustomerType {
	ENTERPRISE,
	PRIVATE
}

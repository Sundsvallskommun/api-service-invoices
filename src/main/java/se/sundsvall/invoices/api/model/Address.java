package se.sundsvall.invoices.api.model;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

public class Address {

	@Schema(example = "Storgatan 1", description = "Street-address")
	private String street;

	@Schema(example = "11122", description = "Post-code")
	private String postcode;

	@Schema(example = "Sundsvall", description = "City")
	private String city;

	@Schema(example = "Kalle", description = "Care-of")
	private String careOf;

	public static Address create() {
		return new Address();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Address withStreet(String street) {
		this.street = street;
		return this;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Address withPostcode(String postcode) {
		this.postcode = postcode;
		return this;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Address withCity(String city) {
		this.city = city;
		return this;
	}

	public String getCareOf() {
		return careOf;
	}

	public void setCareOf(String careOf) {
		this.careOf = careOf;
	}

	public Address withCareOf(String careOf) {
		this.careOf = careOf;
		return this;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Address address = (Address) o;
		return Objects.equals(this.street, address.street) &&
			Objects.equals(this.postcode, address.postcode) &&
			Objects.equals(this.city, address.city) &&
			Objects.equals(this.careOf, address.careOf);
	}

	@Override
	public int hashCode() {
		return Objects.hash(street, postcode, city, careOf);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [street=").append(street).append(", postcode=").append(postcode).append(", city=").append(city)
			.append(", careOf=").append(careOf).append("]");
		return builder.toString();
	}
}

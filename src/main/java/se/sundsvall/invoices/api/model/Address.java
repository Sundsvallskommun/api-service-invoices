package se.sundsvall.invoices.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;

public class Address {

	@Schema(examples = "Storgatan 1", description = "Street-address")
	private String street;

	@Schema(examples = "11122", description = "Post-code")
	private String postcode;

	@Schema(examples = "Sundsvall", description = "City")
	private String city;

	@Schema(examples = "Kalle", description = "Care-of")
	private String careOf;

	public static Address create() {
		return new Address();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public Address withStreet(final String street) {
		this.street = street;
		return this;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(final String postcode) {
		this.postcode = postcode;
	}

	public Address withPostcode(final String postcode) {
		this.postcode = postcode;
		return this;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public Address withCity(final String city) {
		this.city = city;
		return this;
	}

	public String getCareOf() {
		return careOf;
	}

	public void setCareOf(final String careOf) {
		this.careOf = careOf;
	}

	public Address withCareOf(final String careOf) {
		this.careOf = careOf;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(careOf, city, postcode, street);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Address other)) {
			return false;
		}
		return Objects.equals(careOf, other.careOf) && Objects.equals(city, other.city) && Objects.equals(postcode, other.postcode) && Objects.equals(street, other.street);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Address [street=").append(street).append(", postcode=").append(postcode).append(", city=").append(city)
			.append(", careOf=").append(careOf).append("]");
		return builder.toString();
	}
}

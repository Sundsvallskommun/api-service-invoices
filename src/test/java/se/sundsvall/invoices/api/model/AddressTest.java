package se.sundsvall.invoices.api.model;

import org.junit.jupiter.api.Test;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

class AddressTest {

	@Test
	void testBean() {
		assertThat(Address.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void testBuilderMethods() {
		final var street = "Street 1";
		final var careOf = "careOf";
		final var city = "City";
		final var postCode = "Postcode";

		final var address = Address.create()
			.withStreet(street)
			.withCareOf(careOf)
			.withCity(city)
			.withPostcode(postCode);

		assertThat(address).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(address.getStreet()).isEqualTo(street);
		assertThat(address.getCareOf()).isEqualTo(careOf);
		assertThat(address.getCity()).isEqualTo(city);
		assertThat(address.getPostcode()).isEqualTo(postCode);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(Address.create()).hasAllNullFieldsOrProperties();
		assertThat(new Address()).hasAllNullFieldsOrProperties();
	}
}

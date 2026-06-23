package se.sundsvall.invoices.api.validation.impl;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.sundsvall.invoices.api.model.CustomerInvoicesParameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerNumbersOrPartyIdsConstraintValidatorTest {

	private static final String PARTY_ID = "81471222-5798-11e9-ae24-57fa13b361e1";

	private final CustomerNumbersOrPartyIdsConstraintValidator validator = new CustomerNumbersOrPartyIdsConstraintValidator();

	@Test
	void nullParametersIsValid() {
		assertThat(validator.isValid(null, mock(ConstraintValidatorContext.class))).isTrue();
	}

	@Test
	void customerNumbersOnlyIsValid() {
		final var parameters = CustomerInvoicesParameters.create().withCustomerNumbers(List.of("216870"));
		assertThat(validator.isValid(parameters, mock(ConstraintValidatorContext.class))).isTrue();
	}

	@Test
	void partyIdsOnlyIsValid() {
		final var parameters = CustomerInvoicesParameters.create().withPartyIds(List.of(PARTY_ID));
		assertThat(validator.isValid(parameters, mock(ConstraintValidatorContext.class))).isTrue();
	}

	@Test
	void bothProvidedIsValid() {
		final var parameters = CustomerInvoicesParameters.create()
			.withCustomerNumbers(List.of("216870"))
			.withPartyIds(List.of(PARTY_ID));
		assertThat(validator.isValid(parameters, mock(ConstraintValidatorContext.class))).isTrue();
	}

	@Test
	void bothEmptyIsInvalidAndReportsBothFields() {
		final var parameters = CustomerInvoicesParameters.create();

		final var context = mock(ConstraintValidatorContext.class);
		final var builder = mock(ConstraintViolationBuilder.class);
		final var customerNumbersNode = mock(NodeBuilderCustomizableContext.class);
		final var partyIdsNode = mock(NodeBuilderCustomizableContext.class);
		when(context.getDefaultConstraintMessageTemplate()).thenReturn("msg");
		when(context.buildConstraintViolationWithTemplate("msg")).thenReturn(builder);
		when(builder.addPropertyNode("customerNumbers")).thenReturn(customerNumbersNode);
		when(builder.addPropertyNode("partyIds")).thenReturn(partyIdsNode);

		final var result = validator.isValid(parameters, context);

		assertThat(result).isFalse();
		verify(context).disableDefaultConstraintViolation();
		verify(builder).addPropertyNode("customerNumbers");
		verify(builder).addPropertyNode("partyIds");
		verify(customerNumbersNode).addConstraintViolation();
		verify(partyIdsNode).addConstraintViolation();
	}
}

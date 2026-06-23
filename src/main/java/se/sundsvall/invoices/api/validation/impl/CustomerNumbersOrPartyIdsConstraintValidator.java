package se.sundsvall.invoices.api.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import se.sundsvall.invoices.api.model.CustomerInvoicesParameters;
import se.sundsvall.invoices.api.validation.CustomerNumbersOrPartyIds;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

public class CustomerNumbersOrPartyIdsConstraintValidator implements ConstraintValidator<CustomerNumbersOrPartyIds, CustomerInvoicesParameters> {

	@Override
	public boolean isValid(final CustomerInvoicesParameters value, final ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}

		final var valid = isNotEmpty(value.getCustomerNumbers()) || isNotEmpty(value.getPartyIds());
		if (!valid) {
			// Re-target the type-level violation onto both fields so the API reports clean field paths.
			final var message = context.getDefaultConstraintMessageTemplate();
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
				.addPropertyNode("customerNumbers")
				.addConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
				.addPropertyNode("partyIds")
				.addConstraintViolation();
		}

		return valid;
	}
}

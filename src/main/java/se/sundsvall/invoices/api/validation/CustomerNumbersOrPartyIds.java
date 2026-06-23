package se.sundsvall.invoices.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import se.sundsvall.invoices.api.validation.impl.CustomerNumbersOrPartyIdsConstraintValidator;

import static se.sundsvall.invoices.service.Constants.ERROR_CUSTOMER_NUMBERS_OR_PARTY_IDS_REQUIRED;

/**
 * Class-level constraint requiring that at least one of {@code customerNumbers} or {@code partyIds} is provided
 * (non-empty).
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomerNumbersOrPartyIdsConstraintValidator.class)
public @interface CustomerNumbersOrPartyIds {

	String message() default ERROR_CUSTOMER_NUMBERS_OR_PARTY_IDS_REQUIRED;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

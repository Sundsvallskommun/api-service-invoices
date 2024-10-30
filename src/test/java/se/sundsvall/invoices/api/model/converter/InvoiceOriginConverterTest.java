package se.sundsvall.invoices.api.model.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.zalando.problem.Status.BAD_REQUEST;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.zalando.problem.ThrowableProblem;

import se.sundsvall.invoices.api.model.InvoiceOrigin;

class InvoiceOriginConverterTest {

	private static final InvoiceOriginConverter CONVERTER = new InvoiceOriginConverter();

	@Test
	void testEnumValues() {

		// Verify that all defined categories can be converted by the converter (i.e. no exceptions are thrown)
		Stream.of(InvoiceOrigin.values())
			.map(InvoiceOrigin::toString)
			.map(string -> assertDoesNotThrow(() -> CONVERTER.convert(string)));
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"", " ", "invalid-category"
	})
	@NullSource
	void testInvalidValues(String parameter) {
		final var throwable = assertThrows(ThrowableProblem.class, () -> CONVERTER.convert(parameter));
		assertThat(throwable.getStatus()).isEqualTo(BAD_REQUEST);
		assertThat(throwable.getMessage()).isEqualTo(String.format("Bad Request: Invalid value for enum InvoiceOrigin: '%s'", parameter));
	}
}

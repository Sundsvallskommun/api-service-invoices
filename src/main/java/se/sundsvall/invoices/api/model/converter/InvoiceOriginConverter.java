package se.sundsvall.invoices.api.model.converter;

import java.util.stream.Stream;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import se.sundsvall.dept44.problem.Problem;
import se.sundsvall.invoices.api.model.InvoiceOrigin;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static se.sundsvall.invoices.service.Constants.INVALID_PARAMETER_INVOICE_ORIGIN;

@Component
public class InvoiceOriginConverter implements Converter<String, InvoiceOrigin> {

	@Override
	public InvoiceOrigin convert(String source) {
		return Stream.of(InvoiceOrigin.values())
			.filter(value -> value.name().equalsIgnoreCase(trim(source)))
			.findAny()
			.orElseThrow(() -> Problem.valueOf(BAD_REQUEST, format(INVALID_PARAMETER_INVOICE_ORIGIN, source)));
	}
}

package se.sundsvall.invoices.api.model.converter;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.zalando.problem.Status.BAD_REQUEST;
import static se.sundsvall.invoices.service.Constants.INVALID_PARAMETER_INVOICE_ORIGIN;

import java.util.stream.Stream;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import se.sundsvall.invoices.api.model.InvoiceOrigin;

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

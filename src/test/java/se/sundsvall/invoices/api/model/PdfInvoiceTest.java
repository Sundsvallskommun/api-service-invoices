package se.sundsvall.invoices.api.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringExcluding;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import org.junit.jupiter.api.Test;

class PdfInvoiceTest {

	@Test
	void testBean() {
		assertThat(PdfInvoice.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToStringExcluding("file")));
	}

	@Test
	void testBuilderMethods() {
		final var file = "byteArray".getBytes();
		final var fileName = "fileName";

		final var pdfInvoice = PdfInvoice.create()
			.withFile(file)
			.withFileName(fileName);

		assertThat(pdfInvoice).isNotNull().hasNoNullFieldsOrProperties();
		assertThat(pdfInvoice.getFile()).isEqualTo(file);
		assertThat(pdfInvoice.getFileName()).isEqualTo(fileName);
	}

	@Test
	void testNoDirtOnCreatedBean() {
		assertThat(PdfInvoice.create()).hasAllNullFieldsOrProperties();
	}
}

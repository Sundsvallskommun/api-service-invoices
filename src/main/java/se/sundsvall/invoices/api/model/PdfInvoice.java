package se.sundsvall.invoices.api.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import io.swagger.v3.oas.annotations.media.Schema;

public class PdfInvoice {

	@Schema(example = "faktura-999.pdf", description = "File-name")
	private String fileName;

	@Schema(description = "Base64-encoded contents of file")
	private byte[] file;

	public static PdfInvoice create() {
		return new PdfInvoice();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public PdfInvoice withFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public PdfInvoice withFile(byte[] file) {
		this.file = file;
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
		PdfInvoice pdfInvoiceResponseInvoice = (PdfInvoice) o;
		return Objects.equals(this.fileName, pdfInvoiceResponseInvoice.fileName) &&
			Arrays.equals(this.file, pdfInvoiceResponseInvoice.file);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileName, Arrays.hashCode(file));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PdfInvoice [fileName=").append(fileName).append(", file-size=").append(file == null ? 0 : file.length).append("]");
		return builder.toString();
	}
}

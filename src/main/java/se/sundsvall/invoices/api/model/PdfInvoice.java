package se.sundsvall.invoices.api.model;

import java.util.Arrays;
import java.util.Objects;

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

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public PdfInvoice withFileName(final String fileName) {
		this.fileName = fileName;
		return this;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(final byte[] file) {
		this.file = file;
	}

	public PdfInvoice withFile(final byte[] file) {
		this.file = file;
		return this;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PdfInvoice other)) {
			return false;
		}
		return Arrays.equals(file, other.file) && Objects.equals(fileName, other.fileName);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + Arrays.hashCode(file);
		result = (prime * result) + Objects.hash(fileName);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PdfInvoice [fileName=").append(fileName).append(", file-size=").append(file == null ? 0 : file.length).append("]");
		return builder.toString();
	}
}

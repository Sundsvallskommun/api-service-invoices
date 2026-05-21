package se.sundsvall.invoices.service;

import org.springframework.http.MediaType;

/**
 * Internal carrier for a downloaded invoice file - a single PDF, or a ZIP archive when several PDFs exist.
 *
 * @param content     the raw file bytes
 * @param contentType the file media type (application/pdf or application/zip)
 * @param fileName    the file name to expose to the caller
 */
public record PdfFile(byte[] content, MediaType contentType, String fileName) {
}

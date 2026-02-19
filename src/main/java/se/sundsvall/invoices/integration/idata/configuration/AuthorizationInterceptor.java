package se.sundsvall.invoices.integration.idata.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;

/**
 * Interceptor for adding authorization headers to requests made to the IDATA API. This interceptor generates an HMAC
 * signature using the given request parameters and secret key.
 */
public class AuthorizationInterceptor implements RequestInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationInterceptor.class);
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private final String apiKey;
	private final String secretKey;

	public AuthorizationInterceptor(final String apiKey, final String secretKey) {
		this.apiKey = apiKey;
		this.secretKey = secretKey;
	}

	@Override
	public void apply(RequestTemplate template) {
		var message = createMessageWithParameters(template.queries());

		var signature = generateHMAC(message, secretKey);

		template.header(AUTHORIZATION_HEADER, "IDATA " + apiKey + ":" + signature);
	}

	/**
	 * Helper method that extracts the query parameters from the request template and creates a message string. This string
	 * is used to generate the HMAC signature.
	 *
	 * @param  queries the query parameters from the request template
	 * @return         the message string
	 */
	String createMessageWithParameters(final Map<String, Collection<String>> queries) {
		return ofNullable(queries).orElse(emptyMap()).entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
			.collect(Collectors.joining("&"));
	}

	/**
	 * Generates an HMAC signature using the provided message and key.
	 *
	 * @param  message the message to be signed
	 * @param  key     the secret key used for signing
	 * @return         the HMAC signature as a hexadecimal string
	 */
	String generateHMAC(final String message, final String key) {
		try {
			var secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			var mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKeySpec);

			return Hex.encodeHexString(mac.doFinal(message.getBytes()));
		} catch (Exception e) {
			LOGGER.error("Error generating HMAC: {}", e.getMessage());
			throw Problem.valueOf(Status.INTERNAL_SERVER_ERROR, "Error occurred when trying to generate authorization header");
		}
	}

}

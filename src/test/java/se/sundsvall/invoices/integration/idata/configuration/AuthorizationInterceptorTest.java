package se.sundsvall.invoices.integration.idata.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import feign.RequestTemplate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.problem.Problem;

@ExtendWith(MockitoExtension.class)
class AuthorizationInterceptorTest {

	@InjectMocks
	private AuthorizationInterceptor interceptor;

	@BeforeEach
	void setup() {
		interceptor = new AuthorizationInterceptor("b1552d7b-e736-48b8-86fe-ab1dd85d7007", "secretKey");
	}

	@Test
	void addAuthorizationHeader() {
		var template = new RequestTemplate();
		var requestParameters = new HashMap<String, Collection<String>>();
		requestParameters.put("parameter1", List.of("data1"));
		requestParameters.put("parameter2", List.of("data2"));
		template.queries(requestParameters);

		interceptor.apply(template);

		assertThat(template.headers()).containsKey("Authorization");
		var header = template.headers().get("Authorization");

		assertThat(header).isEqualTo(List.of("IDATA b1552d7b-e736-48b8-86fe-ab1dd85d7007:3d9920941433af3f310fe0a2d18ae7afd83ee04d"));
	}

	@ParameterizedTest
	@MethodSource("createMessageWithParametersArgumentsProvider")
	void createMessageWithParameters(final String key1, final List<String> values1, final String key2, final List<String> values2, final String expected) {
		Map<String, Collection<String>> queries = new HashMap<>();
		Optional.ofNullable(key1).ifPresent(key -> queries.put(key, values1));
		Optional.ofNullable(key2).ifPresent(key -> queries.put(key, values2));

		var result = interceptor.createMessageWithParameters(queries);

		assertThat(result).isEqualTo(expected);
	}

	private static Stream<Arguments> createMessageWithParametersArgumentsProvider() {
		return Stream.of(
			Arguments.of("customerno", List.of("123456"), "invoiceno", List.of("654321"), "customerno=123456&invoiceno=654321"),
			Arguments.of("customerno", List.of("123456", "321321"), "invoiceno", List.of("654321", "213456"), "customerno=123456,321321&invoiceno=654321,213456"),
			Arguments.of(null, null, null, null, ""));
	}

	@ParameterizedTest
	@MethodSource("generateHMACArgumentsProvider")
	void generateHMAC(final String message, final String key, final String expected) {
		var result = interceptor.generateHMAC(message, key);

		assertThat(result).isEqualTo(expected);
	}

	@Test
	void generateHMAC_throws() {
		try (MockedStatic<Hex> mockedHex = mockStatic(Hex.class)) {
			mockedHex.when(() -> Hex.encodeHexString((byte[]) any())).thenThrow(new RuntimeException("Mocked exception"));

			assertThatThrownBy(() -> interceptor.generateHMAC("message", "key"))
				.isInstanceOf(Problem.class)
				.hasMessageContaining("Error occurred when trying to generate authorization header");
		}
	}

	private static Stream<Arguments> generateHMACArgumentsProvider() {
		return Stream.of(
			Arguments.of("customerno=123456&invoiceno=654321", "SecretKey", "c4224db74f128debe70deec74fcfbbd12007427d"),
			Arguments.of("customerno=123456&invoiceno=123456", "SecretKey", "9f6498e108f0f59695b54a15f99ac5d237af9678"));
	}
}

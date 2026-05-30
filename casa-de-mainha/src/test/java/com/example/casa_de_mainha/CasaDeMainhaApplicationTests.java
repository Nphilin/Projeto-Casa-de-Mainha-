package com.example.casa_de_mainha;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = {
		// Estas propriedades satisfazem o validador do Resource Server antes do teste
		// iniciar
		"spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080",
		"spring.security.oauth2.resourceserver.jwt.secret-key=essaEUmaChaveSecretaSuperSeguraComMaisDe32CaracteresParaOTestePassar"
})
@ActiveProfiles("test")
class CasaDeMainhaApplicationTests {

	@MockitoBean
	private JwtDecoder jwtDecoder;

	@Test
	void contextLoads() {

	}

}
package com.sivalabs.bookstore.order;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.Collections.singletonList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.keycloak.OAuth2Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.wiremock.integrations.testcontainers.WireMockContainer;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(ContainersConfig.class)
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest {

    static WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:3.5.2-alpine");

    static final String CLIENT_ID = "bookstore-webapp";
    static final String CLIENT_SECRET = "QaGwNlu50ybnoUd9Q8oQ5Y3ZOMUsoO4V";
    static final String USERNAME = "matheus";
    static final String PASSWORD = "matheus1234";

    @Autowired
    OAuth2ResourceServerProperties oAuth2ResourceServerProperties;

    @Autowired
    protected MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {
        wiremockServer.start();
        configureFor(wiremockServer.getHost(), wiremockServer.getPort());
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("order.catalog-service-url", wiremockServer::getBaseUrl);
    }

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    protected static void mockGetProductByCode(String code, String name, BigDecimal price) {
        stubFor(WireMock.get(urlMatching("/api/products/" + code))
                .willReturn(aResponse()
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(
                                """
                                {
                                    "code": "%s",
                                    "name": "%s",
                                    "price": %s
                                }
                                """
                                        .formatted(code, name, price.toPlainString()))));
    }

    protected String getToken() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var map = new LinkedMultiValueMap<String, String>();
        map.put(OAuth2Constants.GRANT_TYPE, singletonList(OAuth2Constants.PASSWORD));
        map.put(OAuth2Constants.CLIENT_ID, singletonList(CLIENT_ID));
        map.put(OAuth2Constants.CLIENT_SECRET, singletonList(CLIENT_SECRET));
        map.put(OAuth2Constants.USERNAME, singletonList(USERNAME));
        map.put(OAuth2Constants.PASSWORD, singletonList(PASSWORD));

        var authServerUrl = oAuth2ResourceServerProperties.getJwt().getIssuerUri() + "/protocol/openid-connect/token";

        var request = new HttpEntity<>(map, headers);
        var restTemplate = new RestTemplate();
        var token = restTemplate.postForObject(authServerUrl, request, KeycloakToken.class);

        assert token != null;
        return token.accessToken();
    }

    record KeycloakToken(@JsonProperty("access_token") String accessToken) {}
}

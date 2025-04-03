package org.example.client;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.example.dto.EventDto;
import org.example.dto.EventResponseDto;
import org.example.model.Category;
import org.example.model.Location;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest
public class ApiClientTests {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApiClient apiClient;

    @BeforeAll
    public static void setUp() {
        configureFor(wireMockExtension.getPort());
    }

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort().dynamicPort())
            .build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("api.url.locations", () -> wireMockExtension.baseUrl() +"/public-api/v1.4/locations");
        registry.add("api.url.categories", () -> wireMockExtension.baseUrl() + "/public-api/v1.4/place-categories");
    }

    @Test
    public void getLocations_shouldReturnLocations() throws JsonProcessingException {
        Location[] locations = { new Location("1", " "), new Location("2", " "),
                new Location("3", " "), new Location("4", " ")};
        String json = objectMapper.writeValueAsString(locations);

        stubFor(
                WireMock.get(urlEqualTo("/public-api/v1.4/locations"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(json)
                        )
        );

        var result = apiClient.getLocations();
        assertArrayEquals(result, locations);
    }

    @Test
    public void getCategories_shouldReturnCategories() throws JsonProcessingException {
        Category[] categories = { new Category(1L, "", " "), new Category(2L, "", " "),
                new Category(3L, "", " "), new Category(4L, "", " ")};
        String json = objectMapper.writeValueAsString(categories);

        stubFor(
                WireMock.get(urlEqualTo("/public-api/v1.4/place-categories"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(json)
                        )
        );

        var result = apiClient.getCategories();
        assertArrayEquals(result, categories);
    }


    @Test
    public void getEvents_shouldReturnEvents() throws JsonProcessingException {
        EventDto[] events = { new EventDto(1, "", " "), new EventDto(2, "", " "), new EventDto(3, "", " ")};
        EventResponseDto eventResponse = new EventResponseDto();
        eventResponse.setResults(events);
        String json = objectMapper.writeValueAsString(eventResponse);

        stubFor(
                WireMock.get(urlEqualTo("/public-api/v1.4/events/?fields=id,title,price"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(json)
                        )
        );

        var result = apiClient.getEvents(null, null, null, null);
        assertArrayEquals(result, events);
    }

    @Test
    public void convertMoney_shouldReturnConvertedAmount(){
        var amount = new BigDecimal("213.312");
        String json = """
                {
                  "fromCurrency": "USD",
                  "toCurrency": "RUB",
                  "convertedAmount": %s
                }
                """.formatted(amount);

        stubFor(
                WireMock.post(urlEqualTo("/currencies/convert"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(json)
                        )
        );

        var result = apiClient.convertMoney(null, null);

        assertEquals(amount, result);
    }
}

package de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class HolidayDataFetcher implements IHolidayDataFetcher {

    @Value("${application.federal-state}")
    private String federalState;
    private final IHolidayResponseParser parser;
    private static final String HOLIDAY_URL = "https://get.api-feiertage.de";


    @Override
    public List<HolidayJPA> readHolidayFromAPI(String year) {
        WebClient webClient = prepareWebClient();
        String result = executeRequest(year, webClient);
        return parser.parseJsonToHolidayJPAList(result);
    }

    private String executeRequest(String year, WebClient webClient) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("states", federalState)
                        .queryParam("years", year)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private WebClient prepareWebClient() {
        return WebClient.builder()
                .baseUrl(HOLIDAY_URL)
                .defaultCookie("cookie-name", "cookie-value")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}

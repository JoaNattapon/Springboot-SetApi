package org.example.setapi.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.setapi.Model.SingleStock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.Arrays;


@Service
public class StockService {

    @Value("${api.key}")
    private String apiKey;
    private final RestTemplate templates = new RestTemplate();

    // Find one particular stock's information.
    public SingleStock[] findSingleStock (String symbol, String startDate, String adjustedPriceFlag) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", apiKey);

        String endpoint = "https://www.setsmart.com/api/listed-company-api/eod-price-by-symbol";
        String url = endpoint + "?symbol=" + symbol + "&startDate=" + startDate + "&adjustedPriceFlag=" + adjustedPriceFlag;

        ResponseEntity<SingleStock[]> responseEntity = templates.exchange (

          url,
          HttpMethod.GET,
          new HttpEntity<>(headers),
          SingleStock[].class
        );

        return responseEntity.getBody();
    }

    // Compare One particular stock in selected time period.
    public float findLossOrGain (String symbol, String startDate, String endDate, String adjustedPriceFlag ) {

        Object[] responseOne = findSingleStock(symbol, startDate, adjustedPriceFlag);
        Object[] responseTwo = findSingleStock(symbol, endDate, adjustedPriceFlag);

        // Parse Json Response
        ObjectMapper objectMapper = new ObjectMapper();
        String responseOneJson;
        String responseTwoJson;

        try {
            responseOneJson = objectMapper.writeValueAsString(responseOne);
            responseTwoJson = objectMapper.writeValueAsString(responseTwo);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fail to serialize JSON . . .");
            return -1;
        }
        try {

            JsonNode jsonNodeOne = objectMapper.readTree(responseOneJson);
            float closeValueOne = jsonNodeOne.get(0).get("close").floatValue();

            JsonNode jsonNodeTwo = objectMapper.readTree(responseTwoJson);
            float closeValueTwo = jsonNodeTwo.get(0).get("close").floatValue();
            return percentageGain(closeValueOne, closeValueTwo);

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("OOP :p Something went wrong !");
            return -1;
        }
    }

    private static float percentageGain(float one, float two) {

        if (one != 0) {

            return ((two - one) / one) * 100;
        }
        return  Float.POSITIVE_INFINITY;
    }
}

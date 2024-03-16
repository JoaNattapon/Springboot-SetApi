package org.example.setapi.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.setapi.Model.SingleStock;
import org.example.setapi.Model.StockList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.text.DecimalFormat;
import java.util.Arrays;


@Service
public class StockService {

    @Value("${api.key}")
    private String apiKey;

    @Value("${first.endpoint}")
    private String firstEndpoint;

    @Value("${second.endpoint}")
    private String secondEndpoint;
    private final RestTemplate templates = new RestTemplate();

    private static float percentageGain(float one, float two) {

        DecimalFormat df = new DecimalFormat("0.00");

        if (one != 0) {

            float result = ((two - one) / one) * 100;
            return Float.parseFloat(df.format(result));
        }
        return  Float.parseFloat(df.format(Float.POSITIVE_INFINITY));
    }

    private void percentageGainString(float value, String symbol) {

        if (value >= 0) {
            System.out.println("""
                    
                    %.2s is gains at %.2f%% in your selected period.
                    
                    That's nice...
                """.formatted(symbol,value));
        } else {
            System.out.println("""
                    
                    %.2s is loss at %.2f%% in your selected period.
                    
                    That's awful man ToT.
                """.formatted(symbol,value));
        }

    }

    // Find one particular stock's information.
    public SingleStock[] findSingleStock (String symbol, String startDate, String adjustedPriceFlag) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", apiKey);

        String endpoint = firstEndpoint;
        String endpointParams = endpoint + "?symbol=" + symbol + "&startDate=" + startDate + "&adjustedPriceFlag=" + adjustedPriceFlag;

        ResponseEntity<SingleStock[]> responseEntity = templates.exchange (

          endpointParams,
          HttpMethod.GET,
          new HttpEntity<>(headers),
          SingleStock[].class
        );

        return responseEntity.getBody();
    }

    // Compare One particular stock in selected time period.
    public float findLossOrGain (String symbol, String startDate, String endDate, String adjustedPriceFlag) {

        Object[] responseOne = findSingleStock(symbol, startDate, adjustedPriceFlag);
        Object[] responseTwo = findSingleStock(symbol, endDate, adjustedPriceFlag);

        // Parse Json Response
        ObjectMapper objectMapper = new ObjectMapper();
        String responseOneJson;
        String responseTwoJson;

        try {
            responseOneJson = objectMapper.writeValueAsString(responseOne);
            responseTwoJson = objectMapper.writeValueAsString(responseTwo);

            try {

                JsonNode jsonNodeOne = objectMapper.readTree(responseOneJson);
                float closeValueOne = jsonNodeOne.get(0).get("close").floatValue();

                JsonNode jsonNodeTwo = objectMapper.readTree(responseTwoJson);
                float closeValueTwo = jsonNodeTwo.get(0).get("close").floatValue();

                float output = percentageGain(closeValueOne, closeValueTwo);

                return output;

            } catch (Exception e) {

                e.printStackTrace();
                System.out.println("OOP :p Something went wrong !");
                return -1;
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fail to serialize JSON . . .");
            return -1;
        }
    }

    public StockList[] findAllStock (String securityType, String date, String adjustedPriceFlag) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", apiKey);

        String endpoint = secondEndpoint;
        String endpointParams = endpoint + "?securityType=" + securityType + "&date=" + date + "&adjustedPriceFlag=" + adjustedPriceFlag;

        ResponseEntity<StockList[]> responseEntity = templates.exchange(

            endpointParams,
            HttpMethod.GET,
            new HttpEntity<>(headers),
            StockList[].class
        );

        return responseEntity.getBody();
    }

}

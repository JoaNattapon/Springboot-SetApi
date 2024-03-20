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
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;


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

    public String getCurrentWorkingDate() {

        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

        String dayOfWeekStr = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());

        if (dayOfWeekStr.equals("Saturday") || dayOfWeekStr.equals("Sunday")) {

            LocalDate getDate = LocalDate.now();
            LocalDate getWorkingDate = getDate.minusDays(2);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String workDate = getWorkingDate.format(formatter);

            return workDate;
        }

        LocalDate getDate = LocalDate.now();
        LocalDate getWorkingDate = getDate.minusDays(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String workDate = getWorkingDate.format(formatter);

        return workDate;
    }

    // Find one particular stock's information.
    public SingleStock[] findSingleStock (String symbol, String startDate, String adjustedPriceFlag) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", apiKey);

//        String startDate = getCurrentWorkingDate();

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

    // Find all high dividend stock.
    public StockList[] highDividend(String securityType, String date, String adjustedPriceFlag) {

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

        StockList[] apiResponse = responseEntity.getBody();

        if (apiResponse != null){

            return Arrays.stream(apiResponse)
                    .filter(res -> res.getDividendYield() > 5.0)
                    .map(res -> {
                        StockList filteredStock = new StockList();
                        filteredStock.setSymbol(res.getSymbol());
                        filteredStock.setDividendYield(res.getDividendYield());

                        return filteredStock;
                    })
                    .toArray(StockList[]::new);
        } else {

            return null;
        }
    }

//    public StockList[] consistentHighDividend(String securityType, String dateThisYear, String dateLastYear,
//                                              String dateLastTwoYear, String dateLastThreeYear, String dateLastFourYear, String adjustedPriceFlag) {
//
//
//    }

}

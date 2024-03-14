package org.example.setapi.Service;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.setapi.Model.SingleStock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.time.LocalDate;

@Service
public class StockService {

    @Value("${api.key}")
    private String apiKey;
    private final RestTemplate templates = new RestTemplate();

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
}

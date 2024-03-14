package org.example.setapi.Controller;

import org.example.setapi.Model.SingleStock;
import org.example.setapi.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/singlestock")
    public SingleStock[] getSingleStock(

            @RequestParam String symbol,
            @RequestParam String startDate,
            @RequestParam String adjustedPriceFlag) {

        return stockService.findSingleStock(symbol, startDate, adjustedPriceFlag);
    }

    @GetMapping("/percentgainloss")
    public float percentGainLoss(

            @RequestParam String symbol,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String adjustedPriceFlag) {

        return stockService.findLossOrGain(symbol, startDate, endDate, adjustedPriceFlag);
    }
}
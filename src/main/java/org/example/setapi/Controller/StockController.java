package org.example.setapi.Controller;

import org.example.setapi.Model.SingleStock;
import org.example.setapi.Model.StockList;
import org.example.setapi.Model.StockListFiveYear;
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

        return stockService.findSingleStock(symbol, startDate,adjustedPriceFlag);
    }

    @GetMapping("/percentgainloss")
    public double percentGainLoss(

            @RequestParam String symbol,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String adjustedPriceFlag) {

        return stockService.findLossOrGain(symbol, startDate, endDate, adjustedPriceFlag);
    }

    @GetMapping("/getHighDividend")
    public StockList[] getHighDividend(

            @RequestParam String securityType,
            @RequestParam String date,
            @RequestParam String adjustedPriceFlag) {

        return stockService.highDividend(securityType, date, adjustedPriceFlag);
    }

    @GetMapping("/getHihgDividendFiveYear")
    public StockListFiveYear[] getHighDividendFiveYear(

            @RequestParam String securityType,
            @RequestParam String dateThisYear,
            @RequestParam String dateLastYear,
            @RequestParam String dateLastTwoYear,
            @RequestParam String dateLastThreeYear,
            @RequestParam String dateLastFourYear,
            @RequestParam String adjustPriceFlag) {

        return stockService.dividendFiveYear(securityType, dateThisYear, dateLastYear,
                                            dateLastTwoYear, dateLastThreeYear, dateLastFourYear,
                                            adjustPriceFlag);
    }
}

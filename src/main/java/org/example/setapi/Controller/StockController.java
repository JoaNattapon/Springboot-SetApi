package org.example.setapi.Controller;

import org.example.setapi.Model.SingleStock;
import org.example.setapi.Model.StockList;
import org.example.setapi.Model.StockListFiveYear;
import org.example.setapi.Model.WishListStock;
import org.example.setapi.Service.StockPersistanceService;
import org.example.setapi.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockPersistanceService stockPersistanceService;

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

    @PostMapping("/addStock")
    public ResponseEntity<WishListStock> addStock(@RequestBody WishListStock wishListStock) {

        WishListStock createStock = stockPersistanceService.addStock(wishListStock);

        return new ResponseEntity<>(createStock, HttpStatus.CREATED);
    }

    @GetMapping("/getAllMyWishList")
    public List<WishListStock> getAllWishList() {

        return stockPersistanceService.getAll();
    }
}

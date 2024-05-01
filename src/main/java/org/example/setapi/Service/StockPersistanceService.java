package org.example.setapi.Service;

import org.example.setapi.Model.WishListStock;
import org.example.setapi.Repository.WishListStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockPersistanceService {

    private final WishListStockRepository wishListStockRepository;

    @Autowired
    public StockPersistanceService(WishListStockRepository wishListStockRepository) {

        this.wishListStockRepository = wishListStockRepository;
    }

    // Add Stock
    public WishListStock addStock(WishListStock wishListStock) {

        return wishListStockRepository.save(wishListStock);
    }

    // Find All
    public List<WishListStock> getAll() {

        return (List<WishListStock>) wishListStockRepository.findAll();
    }
}

package org.example.setapi.Repository;

import org.example.setapi.Model.WishListStock;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface WishListStockRepository {

    List<WishListStock> findAll();

    WishListStock save(WishListStock wishListStock);

    void deleteBySymbol(String symbol);

}

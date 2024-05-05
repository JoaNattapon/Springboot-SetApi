package org.example.setapi.Repository;

import org.example.setapi.Model.Situation;
import org.example.setapi.Model.WishListStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class jdbcWishListStocvkRepository implements WishListStockRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public jdbcWishListStocvkRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<WishListStock> findAll() {

        String sql = "SELECT * FROM wishliststock";

        return jdbcTemplate.query(sql, ((rs, rowNum) -> mapRowToWishListStock(rs)));
    }

    private WishListStock mapRowToWishListStock(ResultSet rs) throws SQLException {

        WishListStock wishListStock = new WishListStock();
        wishListStock.setId(rs.getLong("id"));
        wishListStock.setSymbol(rs.getString("symbol"));
        wishListStock.setBusiness_describe(rs.getString("business_overview"));
        wishListStock.setNetProfit_Quarter_ML(rs.getInt("netprofit_quarter_ml"));

        String situationString = rs.getString("situation");
        if (situationString != null) {
            wishListStock.setSituation(Situation.valueOf(situationString));
        }

        return wishListStock;
    }

    @Override
    public WishListStock save(WishListStock wishListStock) {

        if (wishListStock.getId() != null) {

            throw new InvalidEntityStateException(wishListStock.getSymbol() + " already in the wishlist.");
        }

        jdbcTemplate.update("INSERT INTO wishliststock (symbol, business_overview, netprofit_quarter_ml, situation) VALUES (?, ?, ?, ?)",
                wishListStock.getSymbol(), wishListStock.getBusiness_describe(), wishListStock.getNetProfit_Quarter_ML(), wishListStock.getSituation().toString());

        return  wishListStock;
    }

    @Override
    public void deleteBySymbol(String symbol) {

        jdbcTemplate.update("DELETE FROM wishliststock WHERE symbol = ?", symbol);
    }
}

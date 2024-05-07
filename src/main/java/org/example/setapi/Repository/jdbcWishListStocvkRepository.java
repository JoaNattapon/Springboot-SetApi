package org.example.setapi.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.setapi.Model.NetProfit;
import org.example.setapi.Model.Situation;
import org.example.setapi.Model.WishListStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class jdbcWishListStockRepository implements WishListStockRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public jdbcWishListStockRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<WishListStock> findAll() {

        String sql = "SELECT * FROM wishliststock";

        return jdbcTemplate.query(sql, new WishListStockRowMapper());
    }

    @Override
    public WishListStock save(WishListStock wishListStock) {

        if (wishListStock.getId() != null) {

            throw new InvalidEntityStateException(wishListStock.getSymbol() + " already in the wishlist.");
        }

        NetProfit netProfit = wishListStock.getNetProfit();
        Long netProfitId = insertNetProfit(netProfit);

        jdbcTemplate.update("INSERT INTO wishliststock (symbol, business_overview, net_profit_id, situation) VALUES (?, ?, ?, ?)",
                wishListStock.getSymbol(), wishListStock.getBusiness_describe(), netProfitId, wishListStock.getSituation().toString());

        return  wishListStock;
    }

    private Long insertNetProfit(NetProfit netProfit) {

        jdbcTemplate.update("INSERT INTO netprofit (quarter, millions, year) VALUES (?, ?, ?)",
                netProfit.getQuarter(), netProfit.getMillions(), netProfit.getYear());

        return jdbcTemplate.queryForObject("SELECT last_insert_id()", Long.class);
    }


    private class WishListStockRowMapper implements RowMapper<WishListStock> {

        @Override
        public WishListStock mapRow(ResultSet rs, int rowNum) throws SQLException {

            WishListStock wishListStock = new WishListStock();
            wishListStock.setId(rs.getLong("id"));
            wishListStock.setSymbol(rs.getString("symbol"));
            wishListStock.setBusiness_describe(rs.getString("business_overview"));

            NetProfit netProfit = new NetProfit();
            netProfit.setId(rs.getLong("id"));
            netProfit.setQuarter(rs.getInt("quarter"));
            netProfit.setMillions(rs.getFloat("millions"));
            netProfit.setYear(rs.getInt("year"));

            wishListStock.setNetProfit(netProfit);

            String situationString = rs.getString("situation");
            if (situationString != null) {
                wishListStock.setSituation(Situation.valueOf(situationString));
            }

            return wishListStock;
        }
    }

    @Override
    public void deleteBySymbol(String symbol) {

        jdbcTemplate.update("DELETE FROM wishliststock WHERE symbol = ?", symbol);
    }


}

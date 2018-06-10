package com.example.demo.dao;

import com.example.demo.domain.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author cgh
 */
@Mapper
@Repository
public interface StockMapper extends tk.mybatis.mapper.common.Mapper<Stock> {

    Stock selectById(int id);

    int updateSale(Stock stock);

    //使用乐观锁
    int updateByOptimistic(Stock stock);

    //int updateStockById(Stock stock);
}

package com.example.demo.service.impl;

import com.example.demo.dao.StockMapper;
import com.example.demo.domain.Stock;
import com.example.demo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cgh
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public int getStockCount(int id) {
        Stock stock = stockMapper.selectById(id);
        return stock.getCount();
    }

    @Override
    public Stock getStockById(int id) {
        return stockMapper.selectById(id);
    }

    @Override
    public int updateStockById(Stock stock) {
        return stockMapper.updateSale(stock);
    }

    @Override
    public int updateStockByOptimistic(Stock stock) {
        return stockMapper.updateByOptimistic(stock);
    }
}

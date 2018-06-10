package com.example.demo.dao;

import com.example.demo.domain.StockOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author cgh
 */
@Mapper
@Repository
public interface StockOrderMapper extends tk.mybatis.mapper.common.Mapper<StockOrder>{
}

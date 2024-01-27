package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SkuMapper {

    Long batchInsert(List<Sku> skuList);

    List<Sku> queryByGoodsId(@Param("goodsId") Long goodsId);

    Sku queryById(@Param("id")Long id);

    Double queryMinPrice(@Param("goodsId") Long gooodsId);

    int reduceStock(@Param("skuId") Long skuId, @Param("num") Integer num);
}

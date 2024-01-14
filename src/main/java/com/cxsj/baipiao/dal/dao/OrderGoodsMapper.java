package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderGoodsMapper {

    public Long insert(@Param("orderId") Long orderId, @Param("goods") Goods goods);

    public Goods queryByOrder(@Param("orderId") Long orderId);

    public List<Goods> batchQuery(@Param("list")List<Long> orderList);
}

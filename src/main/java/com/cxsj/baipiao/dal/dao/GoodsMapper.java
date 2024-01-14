package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {

    public List<Goods> pageQueryByCatetory(@Param("categoryId")Long categoryId,
                                           @Param("index") Integer index,
                                           @Param("pagesize") Integer pageSize);

    public Goods queryById(@Param("id") Long id);
}

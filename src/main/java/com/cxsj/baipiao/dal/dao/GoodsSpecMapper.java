package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.GoodsSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsSpecMapper {

    List<GoodsSpec> queryByIds(@Param("ids") List<Long> ids);

    int insert(GoodsSpec goodsSpec);

    int update(GoodsSpec goodsSpec);
}

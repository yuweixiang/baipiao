package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {

    public int insert(Goods goods);

    public List<Goods> pageQueryByCondition(@Param("categoryId") Long categoryId,
                                            @Param("goodsName") String goodsName,
                                            @Param("index") Integer index,
                                            @Param("pageSize") Integer pageSize);

    public Goods queryById(@Param("id") Long id);
}

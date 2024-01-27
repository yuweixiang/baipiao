package com.cxsj.baipiao.dal.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SequenceMapper {

    Long nextValue(@Param("sequenceName")String sequenceName);
}

package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.JstToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JstTokenMapper {

    int insert(JstToken jstToken);

    JstToken queryLast();
}

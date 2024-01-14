package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderAddressMapper {

    public Long insert(@Param("orderId") Long orderId, @Param("address") Address address);

    public Address queryByOrder(@Param("orderId") Long orderId);
}

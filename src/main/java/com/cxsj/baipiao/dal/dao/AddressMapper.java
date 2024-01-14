package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressMapper {

    public Long insert(@Param("userId") Long userId, @Param("address") Address address);

    public List<Address> queryByUser(@Param("userId") Long userId);

    public Address queryDefaultAddress(@Param("userId")Long userId);
}

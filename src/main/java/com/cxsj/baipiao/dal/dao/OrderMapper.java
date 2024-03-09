package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    public Long insert(Order order);

    public Order queryById(@Param("userId")Long userId,@Param("orderId") Long orderId);

    public Long updateStatus(@Param("orderId")Long orderId, @Param("status") String orderStatus);

    public Integer countOrdersByStatus(@Param("userId")Long userId, @Param("status") String orderStatus);

    public List<Order> queryOrdersByStatus(@Param("userId") Long userId,
                                           @Param("status") String orderStatus,
                                           @Param("index") Integer index,
                                           @Param("pageSize") Integer pageSize);
}

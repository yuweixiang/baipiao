package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.domain.UserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    public Long insert(Order order);

    public Order queryById(@Param("userId")Long userId,@Param("orderId") Long orderId);

    public Order queryByOrderId(@Param("orderId")Long orderId);

    public List<Order> queryByStatus(@Param("status") String orderStatus,
                                     @Param("index") Integer index,
                                     @Param("pageSize") Integer pageSize);

    public int countByStatus(@Param("status") String orderStatus);

    public int countWaitSyncOrder();

    public List<Order> queryWaitSynOrder(@Param("index") Integer index,
                                         @Param("pageSize") Integer pageSize);

    void updateOuterId(@Param("orderId")Long orderId,@Param("outerId")String outerId);

    public Long updateStatus(@Param("orderId")Long orderId, @Param("status") String orderStatus);

    public Integer countOrdersByStatus(@Param("userId")Long userId, @Param("status") String orderStatus);

    public List<UserOrder> queryUserOrderCount(@Param("userId")Long userId);

    public List<Order> queryOrdersByStatus(@Param("userId") Long userId,
                                           @Param("status") String orderStatus,
                                           @Param("index") Integer index,
                                           @Param("pageSize") Integer pageSize);

    public int updateLogisticsInfo(@Param("orderId") Long orderId,
                                   @Param("logisticsCompany")String logisticsCompany,
                                   @Param("logisticsNo")String logisticsNo);
}

package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.PointChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PointChangeMapper {

    int insert(PointChange pointChange);

    PointChange queryByRedeemCodeForLock(@Param("redeemCode") String redeemCode);

    int updateChangeInfo(@Param("changeStatus")String changeStatus,@Param("changeUser")String changeUser,
                         @Param("openid")String openid,@Param("redeemCode")String redeemCode);
}

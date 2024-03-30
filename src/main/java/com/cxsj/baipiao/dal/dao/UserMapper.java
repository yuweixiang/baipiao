package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    public Long insert(User user);

    public int update(User user);

    public User queryByTelephone(@Param("telephone")String telephone);

    public User queryById(@Param("userId")Long id);

    public Double queryPoint(@Param("userId")Long userId);

    public User queryByOpenid(@Param("openid")String openid);

    public int reducePoint(@Param("userId")Long userId,@Param("num")Double num);

    int addPoint(@Param("userId")Long userId,@Param("num")Double num);
}

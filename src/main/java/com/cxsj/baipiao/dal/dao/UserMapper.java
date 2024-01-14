package com.cxsj.baipiao.dal.dao;

import com.cxsj.baipiao.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    public Long insert(User user);

    public User queryByTelephone(@Param("telephone")String telephone);

    public User queryById(@Param("telephone")Long id);

    public Double queryPoint(@Param("userId")Long userId);

    public int reducePoint(@Param("userId")Long userId,@Param("num")Double num);
}

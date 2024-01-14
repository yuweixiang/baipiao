package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.result.Result;
import com.cxsj.baipiao.dal.dao.AddressMapper;
import com.cxsj.baipiao.domain.Address;
import com.cxsj.baipiao.exception.BizException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.cxsj.baipiao.enums.ResultCodeEnum.ILLEGAL_ARGUMENT;

@RestController
@RequestMapping("address")
public class AddressController {

    @Resource
    private AddressMapper addressMapper;

    @RequestMapping("/queryDefaultAddress")
    public Result<Address> queryDefaultAddress(Long userId) {

        if (userId == null){
            throw new BizException(ILLEGAL_ARGUMENT,"用户id不能为空！");
        }

        Address address = addressMapper.queryDefaultAddress(userId);
        return new Result<>(address,true);
    }

    @RequestMapping("/queryAllAddress")
    public Result<List<Address>> queryAllAddress(Long userId) {

        if (userId == null){
            throw new BizException(ILLEGAL_ARGUMENT,"用户id不能为空！");
        }

        List<Address> addresses = addressMapper.queryByUser(userId);
        return new Result<>(addresses, true);
    }

    @RequestMapping("/addAddress")
    public Result<Void> addAddress(Long userId, Address address) {

        if (userId == null){
            throw new BizException(ILLEGAL_ARGUMENT,"用户id不能为空！");
        }

        int num = addressMapper.insert(userId,address);
        return new Result<>(null, num>=1);
    }

    @RequestMapping("/updateAddress")
    public Result<Void> updateAddress(Long userId, Address address) {

        if (userId == null){
            throw new BizException(ILLEGAL_ARGUMENT,"用户id不能为空！");
        }

        int num = addressMapper.updateAddress(address);
        return new Result<>(null, num>=1);
    }
}

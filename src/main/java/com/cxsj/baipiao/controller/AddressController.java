package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.bizShare.BizTemplate;
import com.cxsj.baipiao.dal.dao.UserMapper;
import com.cxsj.baipiao.domain.User;
import com.cxsj.baipiao.request.UpdateAddressRequest;
import com.cxsj.baipiao.result.Result;
import com.cxsj.baipiao.dal.dao.AddressMapper;
import com.cxsj.baipiao.domain.Address;
import com.cxsj.baipiao.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.cxsj.baipiao.enums.ResultCodeEnum.ILLEGAL_ARGUMENT;

@RestController
@RequestMapping("address")
public class AddressController extends BizTemplate {

    @Resource
    private AddressMapper addressMapper;

    @Resource
    private UserMapper userMapper;

    @RequestMapping("/queryDefaultAddress")
    public Result<Address> queryDefaultAddress() {

        String openid = getOpenidByToken();
        User user = userMapper.queryByOpenid(openid);

        Address address = addressMapper.queryDefaultAddress(user.getId());
        return new Result<>(address,true);
    }

    @RequestMapping("/queryAllAddress")
    public Result<List<Address>> queryAllAddress() {

        String openid = getOpenidByToken();

        User user = userMapper.queryByOpenid(openid);

        List<Address> addresses = addressMapper.queryByUser(user.getId());
        return new Result<>(addresses, true);
    }

    @RequestMapping("/queryAddressDetail")
    public Result<Address> queryAddressDetail(Long addressId) {

        Address address = addressMapper.queryById(addressId);
        return new Result<>(address, true);
    }

    @RequestMapping("/addOrUpdateAddress")
    public Result<Long> addOrUpdateAddress(@RequestBody	UpdateAddressRequest request) {

        Result<Long> result = new Result<>();
        processWithNoTransation(request,result,"addAddress",()->{

            String openid = getOpenidByToken();

            User user = userMapper.queryByOpenid(openid);

            Address address = request.getAddress();
            address.setUserId(user.getId());
            if (request.getAddress().getId() == null) {
                if (StringUtils.equals(request.getAddress().getIsDefault(),"Y")) {
                    addressMapper.updateUndefualtAddress(user.getId(),null);
                    addressMapper.insert(address);
                }
            }else {
                if (StringUtils.equals(request.getAddress().getIsDefault(),"Y")) {
                    addressMapper.updateUndefualtAddress(user.getId(), address.getId());
                }
                addressMapper.updateAddress(address);
            }
            result.setData(address.getId());
            buildSuccess(result);
        });

        return result;
    }

    @RequestMapping("/updateAddress")
    public Result<Boolean> updateAddress(@RequestBody UpdateAddressRequest request) {

        Result<Boolean> result = new Result<>();
        processWithNoTransation(request,result,"addAddress",()->{

            String openid = getOpenidByToken();

            int num = addressMapper.updateAddress(request.getAddress());
            result.setData(num>1);
            buildSuccess(result);
        });

        return result;
    }
}

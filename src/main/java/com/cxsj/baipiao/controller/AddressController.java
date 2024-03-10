package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.bizShare.BizTemplate;
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

    @RequestMapping("/queryAddressDetail")
    public Result<Address> queryAddressDetail(Long addressId) {

        Address address = addressMapper.queryById(addressId);
        return new Result<>(address, true);
    }

    @RequestMapping("/addOrUpdateAddress")
    public Result<Long> addOrUpdateAddress(@RequestBody	UpdateAddressRequest request) {

        Result<Long> result = new Result<>();
        processWithNoTransation(request,result,"addAddress",()->{
            if (request.getUserId() == null){
                throw new BizException(ILLEGAL_ARGUMENT,"用户id不能为空！");
            }
            Address address = request.getAddress();
            address.setUserId(request.getUserId());
            if (request.getAddress().getId() == null) {
                if (StringUtils.equals(request.getAddress().getIsDefault(),"Y")) {
                    addressMapper.updateUndefualtAddress(request.getUserId(),null);
                    addressMapper.insert(address);
                }
            }else {
                if (StringUtils.equals(request.getAddress().getIsDefault(),"Y")) {
                    addressMapper.updateUndefualtAddress(request.getUserId(), address.getId());
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
            if (request.getUserId() == null){
                throw new BizException(ILLEGAL_ARGUMENT,"用户id不能为空！");
            }

            int num = addressMapper.updateAddress(request.getAddress());
            result.setData(num>1);
            buildSuccess(result);
        });

        return result;
    }
}

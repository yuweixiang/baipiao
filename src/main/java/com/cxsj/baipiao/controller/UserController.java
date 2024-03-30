package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.bizShare.BizTemplate;
import com.cxsj.baipiao.common.AspectContextHolder;
import com.cxsj.baipiao.dal.dao.AddressMapper;
import com.cxsj.baipiao.dal.dao.UserMapper;
import com.cxsj.baipiao.domain.Address;
import com.cxsj.baipiao.domain.CustomerService;
import com.cxsj.baipiao.domain.User;
import com.cxsj.baipiao.enums.ResultCodeEnum;
import com.cxsj.baipiao.exception.BizException;
import com.cxsj.baipiao.request.OrderRenderReqeust;
import com.cxsj.baipiao.request.UpdateUserRequest;
import com.cxsj.baipiao.request.UserCenterReqeust;
import com.cxsj.baipiao.result.Result;
import com.cxsj.baipiao.service.order.OrderQueryService;
import com.cxsj.baipiao.utils.TokenManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController extends BizTemplate {

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderQueryService orderQueryService;

    @Resource
    private AddressMapper addressMapper;

    @RequestMapping("/userCenter")
    public Result<User> queryCenterInfo(UserCenterReqeust reqeust) {

        Result<User> result = new Result<>();
        processWithNoTransation(reqeust,result,"queryUserCenter",()->{

            String openid = getOpenidByToken();

            User user = userMapper.queryByOpenid(openid);

            List<Address> addressList = addressMapper.queryByUser(user.getId());

            user.setAddresses(addressList);
            user.setServiceInfo(buildCustomService());
            user.setUserOrders(orderQueryService.queryUserOrderCount(user.getId()));

            result.setData(user);
            buildSuccess(result);
        });

        return result;
    }

    @RequestMapping("/queryUserInfo")
    public Result<User> queryUserInfo() {

        Result<User> result = new Result<>();
        processWithNoTransation(null,result,"queryUserInfo",()->{


            String openid = getOpenidByToken();
            User user = userMapper.queryByOpenid(openid);

            result.setData(user);
            buildSuccess(result);
        });

        return result;
    }

    @RequestMapping("/updateUser")
    public Result<Boolean> updateUser(UpdateUserRequest request) {

        Result<Boolean> result = new Result<>();
        processWithNoTransation(request,result,"queryUserInfo",()->{

            String openid = getOpenidByToken();

            User user = userMapper.queryByOpenid(openid);
            if (StringUtils.isNotBlank(request.getNick())){
                user.setNick(request.getNick());
            }
            if (StringUtils.isNotBlank(request.getSex())){
                user.setSex(request.getSex());
            }
            if (StringUtils.isNotBlank(request.getPic())){
                user.setPic(request.getPic());
            }
            userMapper.update(user);
            result.setData(true);
            buildSuccess(result);
        });

        return result;
    }


    private CustomerService buildCustomService() {
        CustomerService service = new CustomerService();
        service.setServicePhone("15088607597");
        return service;
    }
}

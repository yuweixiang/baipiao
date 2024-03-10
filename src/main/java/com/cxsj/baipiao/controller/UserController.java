package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.bizShare.BizTemplate;
import com.cxsj.baipiao.dal.dao.AddressMapper;
import com.cxsj.baipiao.dal.dao.UserMapper;
import com.cxsj.baipiao.domain.Address;
import com.cxsj.baipiao.domain.CustomerService;
import com.cxsj.baipiao.domain.User;
import com.cxsj.baipiao.enums.ResultCodeEnum;
import com.cxsj.baipiao.exception.BizException;
import com.cxsj.baipiao.request.OrderRenderReqeust;
import com.cxsj.baipiao.request.UserCenterReqeust;
import com.cxsj.baipiao.result.Result;
import com.cxsj.baipiao.service.order.OrderQueryService;
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

            if (reqeust.getUserId() == null){
                throw new BizException(ResultCodeEnum.ILLEGAL_ARGUMENT,"请重新登陆！");
            }

            User user = userMapper.queryById(reqeust.getUserId());

            List<Address> addressList = addressMapper.queryByUser(reqeust.getUserId());

            user.setAddresses(addressList);
            user.setServiceInfo(buildCustomService());
            user.setUserOrders(orderQueryService.queryUserOrderCount(reqeust.getUserId()));

            result.setData(user);
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

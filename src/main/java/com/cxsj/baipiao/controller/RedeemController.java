package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.bizShare.BizTemplate;
import com.cxsj.baipiao.dal.dao.PointChangeMapper;
import com.cxsj.baipiao.dal.dao.UserMapper;
import com.cxsj.baipiao.domain.Address;
import com.cxsj.baipiao.domain.PointChange;
import com.cxsj.baipiao.domain.User;
import com.cxsj.baipiao.enums.PointChangeStatusEnum;
import com.cxsj.baipiao.enums.ResultCodeEnum;
import com.cxsj.baipiao.exception.BizException;
import com.cxsj.baipiao.request.PointChangeRequest;
import com.cxsj.baipiao.request.UserCenterReqeust;
import com.cxsj.baipiao.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("redeem")
public class RedeemController extends BizTemplate {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PointChangeMapper changeMapper;

    @RequestMapping("/pointRedeem")
    public Result<Double> queryCenterInfo(PointChangeRequest reqeust) {

        Result<Double> result = new Result<>();
        processWithTransation(reqeust,result,"queryUserCenter",()->{

            if (StringUtils.isBlank(reqeust.getPhone())){
                throw new BizException(ResultCodeEnum.ILLEGAL_ARGUMENT,"请输入兑换码！");
            }

            String openid = getOpenidByToken();

            User user = userMapper.queryByOpenid(openid);

            PointChange pointChange = changeMapper.queryByRedeemCodeForLock(reqeust.getPhone());
            if(pointChange == null){
                throw new BizException(ResultCodeEnum.ILLEGAL_ARGUMENT,"兑换码不存在，请重新输入！");
            }

            if(!StringUtils.equals(pointChange.getChangeStatus(),PointChangeStatusEnum.INIT.getCode())){
                throw new BizException(ResultCodeEnum.ILLEGAL_ARGUMENT,"该兑换码已经兑换！");
            }

            userMapper.addPoint(user.getId(),pointChange.getPoint());
            changeMapper.updateChangeInfo(PointChangeStatusEnum.REDEMMED.getCode(),user.getNick(),
                    user.getOpenid(),reqeust.getPhone());

            result.setData(pointChange.getPoint());
            buildSuccess(result);
        });

        return result;
    }
}

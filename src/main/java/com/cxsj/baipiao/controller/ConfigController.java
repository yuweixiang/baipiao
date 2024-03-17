package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.diamond.DynamicConfig;
import com.cxsj.baipiao.domain.Address;
import com.cxsj.baipiao.domain.IndexConfig;
import com.cxsj.baipiao.exception.BizException;
import com.cxsj.baipiao.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cxsj.baipiao.enums.ResultCodeEnum.ILLEGAL_ARGUMENT;

@RestController
@RequestMapping("config")
public class ConfigController {

    @RequestMapping("/queryIndexConfig")
    public Result<IndexConfig> queryIndexConfig() {

        IndexConfig indexConfig = DynamicConfig.getInstance().getIndexConfig();
        return new Result<>(indexConfig,true);
    }
}
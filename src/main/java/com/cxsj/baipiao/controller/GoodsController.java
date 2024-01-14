package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.result.PageResult;
import com.cxsj.baipiao.result.Result;
import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.request.GoodsQueryRequest;
import com.cxsj.baipiao.service.goods.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping("/queryGoods")
    public PageResult<Goods> queryGoods(GoodsQueryRequest request) {

        List<Goods> goodsList = goodsService.queryGoodsByCondition(request);
        PageResult<Goods> result = new PageResult<>(goodsList, true);
        result.setPageSize(request.getPageSize());
        result.setPageIndex(request.getPageIndex());
        return result;
    }

    @RequestMapping("/queryGoodsDetail")
    public Result<Goods> queryGoodsDetail(Long goodsId) {

        Goods goods = goodsService.queryGoodsDetail(goodsId);
        return new Result<>(goods, true);
    }
}

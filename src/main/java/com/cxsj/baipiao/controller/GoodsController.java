package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.Result;
import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.request.GoodsQueryRequest;
import com.cxsj.baipiao.service.goods.GoodsService;
import com.cxsj.baipiao.service.order.OrderService;
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
    public Result<List<Goods>> queryGoods(GoodsQueryRequest request) {

        List<Goods> goodsList = goodsService.queryGoodsByCondition(request);
        return new Result<>(goodsList, true);
    }

    @RequestMapping("/queryGoodsDetail")
    public Result<Goods> queryGoodsDetail(Long goodsId) {

        Goods goods = goodsService.queryGoodsDetail(goodsId);
        return new Result<>(goods, true);
    }
}

package com.cxsj.baipiao.service.goods;

import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.request.GoodsQueryRequest;

import java.util.List;

public interface GoodsService {

    public List<Goods> queryGoodsByCondition(GoodsQueryRequest request);

    public Goods queryGoodsDetail(Long goodsId);
}

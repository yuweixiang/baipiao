package com.cxsj.baipiao.service.goods;

import com.alibaba.fastjson.JSONObject;
import com.cxsj.baipiao.dal.dao.GoodsMapper;
import com.cxsj.baipiao.dal.dao.SkuMapper;
import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.domain.Sku;
import com.cxsj.baipiao.domain.SkuSpec;
import com.cxsj.baipiao.request.GoodsQueryRequest;
import com.cxsj.baipiao.request.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private SkuMapper skuMapper;

    public List<Goods> queryGoodsByCondition(GoodsQueryRequest request){

        Integer index = (request.getPageIndex()-1)*request.getPageSize();
        List<Goods> goodsList = goodsMapper.pageQueryByCondition(request.getCategoryId(),
                request.getGoodsName(),index,request.getPageSize());
        goodsList.forEach(goods -> {
            goods.setPrice(skuMapper.queryMinPrice(goods.getId()));
        });

        return goodsList;
    }

    public Goods queryGoodsDetail(Long goodsId){

        Goods goods = goodsMapper.queryById(goodsId);
        List<Sku> skuList = skuMapper.queryByGoodsId(goodsId);
        skuList.forEach(sku -> {
            sku.setSkuSpecList(JSONObject.parseArray(sku.getSkuSpecs(), SkuSpec.class));
            sku.getSkuSpecList().forEach(skuSpec -> {
                skuSpec.setSpecValueList(JSONObject.parseArray(skuSpec.getSpecValues(),String.class));
            });
        });
        goods.setSkuList(skuList);

        return goods;
    }
}

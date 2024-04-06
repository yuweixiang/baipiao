package com.cxsj.baipiao.service.goods;

import com.alibaba.fastjson.JSONObject;
import com.cxsj.baipiao.dal.dao.GoodsMapper;
import com.cxsj.baipiao.dal.dao.GoodsSpecMapper;
import com.cxsj.baipiao.dal.dao.SkuMapper;
import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.domain.GoodsSpec;
import com.cxsj.baipiao.domain.Sku;
import com.cxsj.baipiao.domain.SpecInfo;
import com.cxsj.baipiao.request.GoodsQueryRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsSpecMapper goodsSpecMapper;

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
        List<Long> specIds = JSONObject.parseArray(goods.getSpecDesc(),Long.class);
        List<GoodsSpec> specs = goodsSpecMapper.queryByIds(specIds);
        goods.setImageList(JSONObject.parseArray(goods.getImages(),String.class));
        specs.forEach(spec->{
            spec.setSpecId(spec.getId());
            spec.setSpecValueList(JSONObject.parseArray(spec.getSpecValue(), GoodsSpec.SpecValue.class));
        });
        goods.setSpecList(specs);

        skuList.forEach(sku -> {
            sku.setSpecInfo(JSONObject.parseArray(sku.getSkuSpecs(), SpecInfo.class));
        });
        goods.setGoodsDesc(JSONObject.parseArray(goods.getDescImage(),String.class));
        goods.setSkuList(skuList);

        return goods;
    }

    public static void main(String[] args) {
        String s="[\"https://gw.alicdn.com/imgextra/i3/2207281618004/O1CN01y9hSCG28zt2Jgfntq_!!2207281618004.jpg_Q75.jpg_.webp\",\"https://gw.alicdn.com/imgextra/i2/2207281618004/O1CN01oOvqZh28zt2HOF4P1_!!2207281618004.jpg_Q75.jpg_.webp\",\"https://gw.alicdn.com/imgextra/i4/2207281618004/O1CN01CATKvk28zt2BoPkyV_!!2207281618004.jpg_Q75.jpg_.webp\",\"https://gw.alicdn.com/imgextra/i3/2207281618004/O1CN01O0VWLE28zt2ODAbBu_!!2207281618004.jpg_Q75.jpg_.webp\"]";
        System.out.println(JSONObject.parseArray(s,String.class));
    }
}

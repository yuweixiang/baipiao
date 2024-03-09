package com.cxsj.baipiao.service.order;

import com.alibaba.fastjson.JSONObject;
import com.cxsj.baipiao.bizShare.BizTemplate;
import com.cxsj.baipiao.dal.dao.*;
import com.cxsj.baipiao.domain.*;
import com.cxsj.baipiao.enums.OrderStatusEnum;
import com.cxsj.baipiao.enums.ResultCodeEnum;
import com.cxsj.baipiao.exception.BizException;
import com.cxsj.baipiao.request.OrderRenderReqeust;
import com.cxsj.baipiao.result.Result;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.cxsj.baipiao.enums.ResultCodeEnum.LACK_OF_POINT;

@Service
public class OrderServiceImpl extends BizTemplate implements OrderService{

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private SkuMapper skuMapper;

    @Resource
    private GoodsSpecMapper goodsSpecMapper;

    @Resource
    private SequenceMapper sequenceMapper;

    @Resource
    private OrderAddressMapper orderAddressMapper;

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public Long createOrder(OrderRenderReqeust reqeust) {

        Result<Long> result = new Result<>();
        processWithTransation(reqeust,result,"createOrder",()->{
            Long orderId = generateOrderId();

            Sku sku = skuMapper.queryById(reqeust.getSkuId());

            if (sku.getStock() < reqeust.getSkuNum()){
                throw new BizException(ResultCodeEnum.LACK_OF_STOCK,"库存不足！请选择其他商品下单！");
            }

            Order order = new Order();
            order.setOrderGoods(buildOrderGoods(sku,reqeust.getSkuNum()));
            order.setUserId(reqeust.getUserId());
            order.setPrice(sku.getPrice() * reqeust.getSkuNum() );
            order.setOrderAddress(addressMapper.queryById(reqeust.getAddressId()));
            reducePoint(order);
            order.setStatus(OrderStatusEnum.PAID.getCode());
            order.setId(orderId);
            orderMapper.insert(order);
            order.getOrderAddress().setOrderId(orderId);
            order.getOrderGoods().setOrderId(orderId);
            orderAddressMapper.insert(order.getOrderAddress());
            orderGoodsMapper.insert(order.getOrderGoods());
            result.setData(orderId);
            buildSuccess(result);
        });

        return result.getData();
    }

    private Goods buildOrderGoods(Sku sku,int num) {
        Goods goods = goodsMapper.queryById(sku.getGoodsId());
        goods.setId(sku.getGoodsId());
        goods.setPrice(sku.getPrice());
        goods.setNum(num);
        goods.setPrimaryImage(sku.getSkuImage());
        goods.setSkuInfo(buildSkuInfo(sku.getGoodsId(),sku.getSkuSpecs()));
        return goods;
    }

    private String buildSkuInfo(Long goodsId,String skuSpecs) {

        List<SpecInfo> list = JSONObject.parseArray(skuSpecs,SpecInfo.class);
        List<GoodsSpec> specs = goodsSpecMapper.queryByGoodsId(goodsId);
        if (CollectionUtils.isEmpty(specs)){
            return skuSpecs;
        }

        for (SpecInfo specInfo : list){
            for (GoodsSpec spec : specs){
                if (specInfo.getSpecId().equals(spec.getId())) {
                    specInfo.setSpecTitle(spec.getTitle());
                    List<GoodsSpec.SpecValue> specValues = JSONObject.parseArray(spec.getSpecValue(), GoodsSpec.SpecValue.class);
                    specValues.forEach(value->{
                       if (value.getSpecValueId().equals(specInfo.getSpecValueId())){
                           specInfo.setSpecValue(value.getSpecValue());
                       }
                    });
                }

            }
        }

        return JSONObject.toJSONString(list);
    }

    public Order orderRender(OrderRenderReqeust reqeust){

        Result<Order> result = new Result<>();
        processWithNoTransation(reqeust,result,"orderRender",()->{


            Goods goods = new Goods();
            goods.setId(reqeust.getGoodsId());
            Sku tempSku = new Sku();
            tempSku.setId(reqeust.getSkuId());
            goods.setSkuList(Lists.newArrayList(tempSku));
            goods.setNum(reqeust.getSkuNum());
            Address address = addressMapper.queryDefaultAddress(reqeust.getUserId());

            Order order = new Order();
            Goods renderGoods = goodsMapper.queryById(goods.getId());
            Sku sku = skuMapper.queryById(goods.getSkuList().get(0).getId());
            if (sku.getStock() <= goods.getNum()){
                order.setSettleType(0);
            }
            renderGoods.setSkuList(Lists.newArrayList(sku));
            order.setOrderGoods(renderGoods);
            order.setOrderAddress(address);
            order.setPrice(sku.getPrice() * goods.getNum());
            result.setData(order);
            buildSuccess(result);
        });

        return result.getData();
    }

    private Long generateOrderId() {
        Long sequenceId = sequenceMapper.nextValue("order_id_seq");

        Date d = new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyyMMdd");
        String date = sbf.format(d);

        return Long.parseLong(date+sequenceId);
    }

    private void reducePoint(Order order) {

        Double point = userMapper.queryPoint(order.getUserId());
        if (point < order.getPrice()){
            throw new BizException(LACK_OF_POINT);
        }

        int num = userMapper.reducePoint(order.getUserId(),order.getPrice());
        if (num < 1){
            throw new BizException(LACK_OF_POINT);
        }

    }
}

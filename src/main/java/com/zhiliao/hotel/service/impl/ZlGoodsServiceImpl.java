package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.goods.vo.EsGoods;
import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
import com.zhiliao.hotel.controller.goods.vo.GoodsSkuListVo;
import com.zhiliao.hotel.controller.myOrder.vo.GoodsCouponInfoVO;
import com.zhiliao.hotel.controller.myOrder.vo.GoodsShortInfoVO;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.service.ZlGoodsService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Service
public class ZlGoodsServiceImpl implements ZlGoodsService {

    private final ZlGoodsMapper zlGoodsMapper;

    private final ElasticsearchTemplate elasticsearchTemplate;

    private final RedisTemplate redisTemplate;

    private final ZlCouponMapper zlCouponMapper;

    private final ZlCouponUserMapper zlCouponUserMapper;

    private final ZlOrderMapper zlOrderMapper;

    @Override
    public List<Map<String, String>> findGoodsCategory(Integer hotelId, Integer belongModule) {
        return zlGoodsMapper.findGoodsCategory(hotelId, belongModule);
    }

    @Override
    public PageInfoResult findGoodsList(Integer hotelId, Integer belongModule, Integer pageNo, Integer pageSize, String categoryName) {
        // 设定当前页码，以及当前页显示的条数
        PageHelper.startPage(pageNo, pageSize);
        List<GoodsListVo> dataList = zlGoodsMapper.findGoodsList(hotelId, belongModule, categoryName);
        PageInfo<GoodsListVo> pageInfo = new PageInfo<>(dataList);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public List<GoodsSkuListVo> findGoodsSkuList(Integer hotelId, Integer goodsId) {
        return zlGoodsMapper.findGoodsSkuList(hotelId, goodsId);
    }

    @Override
    public GoodsListVo findGoodsDetail(Integer goodsId) {
        GoodsListVo goodsListVo = zlGoodsMapper.findGoodsDetail(goodsId);
        if (goodsListVo != null) {
            zlGoodsMapper.updateGoodsTotalVisitCount(goodsId);
        }
        return goodsListVo;
    }

    @Override
    public List<GoodsListVo> findRecommendGoodsList(Integer hotelId) {
        return zlGoodsMapper.findRecommendGoodsList(hotelId);
    }

    @Override
    public void updateGoodsCount(String out_trade_no) {

        //从redis中拿出订单商品信息
        List<GoodsShortInfoVO> goodsShortInfoVOList = (List<GoodsShortInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
        for (GoodsShortInfoVO goodsShortInfoVO : goodsShortInfoVOList) {
            Integer goodsCount = goodsShortInfoVO.getGoodsCount();
            Integer hotelGoodsSkuID = goodsShortInfoVO.getHotelGoodsSkuID();
            //更新mysql数据库库存
            zlGoodsMapper.updateGoods(hotelGoodsSkuID, goodsCount);
            zlGoodsMapper.updateGoodsSku(hotelGoodsSkuID, goodsCount);
            zlGoodsMapper.updateHotelGoodsSku(hotelGoodsSkuID, goodsCount);
            //更改redis数据库库存
            Integer count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
            redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID, count - goodsCount);
        }

        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + out_trade_no)) {
            //从redis中拿出优惠券集合信息
            List<GoodsCouponInfoVO> goodsCouponInfoVOList = (List<GoodsCouponInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + out_trade_no);
            for (GoodsCouponInfoVO goodsCouponInfoVO : goodsCouponInfoVOList) {
                Integer recID = goodsCouponInfoVO.getRecID();
                //更改数据库中该优惠券的状态
                Integer useDate = Math.toIntExact(System.currentTimeMillis() / 1000);
                zlCouponUserMapper.updateCouponUser(recID, useDate, out_trade_no);
                //删除该订单下锁定的优惠券
                redisTemplate.delete(RedisKeyConstant.ORDER_RECID + recID);
            }
            //删除该订单下锁定的优惠券集合
            redisTemplate.delete(RedisKeyConstant.ORDER_RECID_ORDERSERIALNO + out_trade_no);
        }

        //下单业务完成,删除redis订单商品信息
        redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
        //删除redis中锁定的订单商品标记
        redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + out_trade_no);
    }

    @Override
    public List<EsGoods> searchGoods(Integer hotelId, String selectParam, Integer belongModule, Integer pageNo, Integer pageSize) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(selectParam).defaultField("goodsname"))
                .withPageable(PageRequest.of(pageNo - 1, pageSize))
                .build();
        //获取结果集list集合
        List<EsGoods> esGoodsList = elasticsearchTemplate.queryForList(query, EsGoods.class);
        //对集合进行遍历筛选
        Iterator<EsGoods> iterator = esGoodsList.iterator();
        if (belongModule == 0) {
            while (iterator.hasNext()) {
                EsGoods esGoods = iterator.next();
                if (!esGoods.getHotelid().equals(hotelId)) {
                    iterator.remove();
                }
            }
        } else {
            while (iterator.hasNext()) {
                EsGoods esGoods = iterator.next();
                if (!esGoods.getHotelid().equals(hotelId) || !esGoods.getBelongmodule().equals(belongModule)) {
                    iterator.remove();
                }
            }
        }

        return esGoodsList;
    }
}
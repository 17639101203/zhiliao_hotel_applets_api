package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.controller.goods.vo.*;
import com.zhiliao.hotel.controller.myOrder.vo.GoodsCouponInfoVO;
import com.zhiliao.hotel.controller.myOrder.vo.GoodsShortInfoVO;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.ZlXcxmenu;
import com.zhiliao.hotel.service.ZlGoodsService;
import com.zhiliao.hotel.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Service
public class ZlGoodsServiceImpl implements ZlGoodsService {

    @Autowired
    private final ZlGoodsMapper zlGoodsMapper;

    @Autowired
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private final RedisTemplate redisTemplate;

    @Autowired
    private final ZlCouponMapper zlCouponMapper;

    @Autowired
    private final ZlCouponUserMapper zlCouponUserMapper;

    @Autowired
    private final ZlOrderMapper zlOrderMapper;

    @Autowired
    private ZlXcxMenuMapper zlXcxMenuMapper;

    @Override
    public List<Map<String, String>> findGoodsCategory(Integer hotelId, Integer belongModule) {
        return zlGoodsMapper.findGoodsCategory(hotelId, belongModule);
    }

    @Override
    public PageInfoResult findGoodsList(Integer hotelId, Integer belongModule, Integer pageNo, Integer pageSize, String categoryName) {
        // 设定当前页码，以及当前页显示的条数
        PageHelper.startPage(pageNo, pageSize);
        List<GoodsListVo> dataList = zlGoodsMapper.findGoodsList(hotelId, belongModule, categoryName);

        //判断列表商品是否有规格
        for (GoodsListVo goodsListVo : dataList) {
            Integer skuCount = zlGoodsMapper.findSkuCount(goodsListVo.getGoodsId());
            if (skuCount > 1) {
                goodsListVo.setIsManySku(true);
            } else {
                goodsListVo.setIsManySku(false);
            }
        }

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
    public List<EsGoodsVO> searchGoods(Integer hotelId, String selectParam, Integer belongModule, Integer pageNo, Integer pageSize) {
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

        List<EsGoodsVO> esGoodsVOList = new LinkedList<>();
        //根据字段复制实体并查询商品是否为多规格
        for (EsGoods esGoods : esGoodsList) {
            Integer skuCount = zlGoodsMapper.findSkuCount(esGoods.getGoodsid());
            EsGoodsVO esGoodsVO = new EsGoodsVO();
            BeanUtils.copyProperties(esGoods, esGoodsVO);
            if (skuCount > 1) {
                esGoodsVO.setIsManySku(true);
            } else {
                esGoodsVO.setIsManySku(false);
            }
            esGoodsVOList.add(esGoodsVO);
        }

        return esGoodsVOList;
    }

    @Override
    public BusinessHoursVO getBusinessHours(Integer menuId) {
        BusinessHoursVO businessHoursVO = new BusinessHoursVO();
        ZlXcxmenu zlXcxmenu = zlXcxMenuMapper.getBusinessHours(menuId);
        String serviceopentime = zlXcxmenu.getServiceopentime();
        String[] split = serviceopentime.split("-");
        String dateByString = DateUtils.getDateByString();
        //拼接营业时间字符串
        String startBusinessHoursStr = dateByString + " " + split[0];
        String endBusinessHoursStr = dateByString + " " + split[1];
        //转化成时间戳
        Long startBusinessHours;
        Long endBusinessHours;
        try {
            startBusinessHours = Long.valueOf(DateUtils.dateToStamp2(startBusinessHoursStr));
            businessHoursVO.setStartBusinessHours(startBusinessHours);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endBusinessHours = Long.valueOf(DateUtils.dateToStamp2(endBusinessHoursStr));
            businessHoursVO.setEndBusinessHours(endBusinessHours);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return businessHoursVO;
    }
}
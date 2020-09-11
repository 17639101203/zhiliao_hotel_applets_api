package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.common.constant.RedisKeyConstant;
import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.goods.vo.*;
import com.zhiliao.hotel.controller.myOrder.vo.GoodsCouponInfoVO;
import com.zhiliao.hotel.controller.myOrder.vo.GoodsShortInfoVO;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.model.ZlXcxmenu;
import com.zhiliao.hotel.service.ZlGoodsService;
import com.zhiliao.hotel.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilder;
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
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.*;


/**
 * Created by xiegege on 2020/4/15.
 */

@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Service
public class ZlGoodsServiceImpl implements ZlGoodsService {
    @Autowired
    private ZlGoodsMapper zlGoodsMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ZlCouponMapper zlCouponMapper;

    @Autowired
    private ZlOrderMapper zlOrderMapper;

    @Autowired
    private ZlXcxMenuMapper zlXcxMenuMapper;

    @Autowired
    private ZlOrderDetailMapper zlOrderDetailMapper;

    @Override
    public List<Map<String, String>> findGoodsCategory(Integer hotelId, Integer belongModule) {
        return zlGoodsMapper.findGoodsCategory(hotelId, belongModule);
    }

    @Override
    public PageInfoResult findGoodsList(Integer hotelId, Integer belongModule, Integer pageNo, Integer pageSize, String categoryName) {
        // 设定当前页码，以及当前页显示的条数
        PageHelper.startPage(pageNo, pageSize);
        List<GoodsListVo> dataList = zlGoodsMapper.findGoodsList(hotelId, belongModule, categoryName);

        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }

        Iterator<GoodsListVo> goodsListVoIterator = dataList.iterator();
        while (goodsListVoIterator.hasNext()) {
            GoodsListVo goodsListVo = goodsListVoIterator.next();

            //判断列表商品是否有规格
            Integer skuCount = zlGoodsMapper.findSkuCount(goodsListVo.getGoodsId());
            if (skuCount > 1) {
                goodsListVo.setIsManySku(true);
                goodsListVo.setHotelGoodsSkuId(-1);
            } else {
                goodsListVo.setIsManySku(false);
            }
            Integer stockCount = zlGoodsMapper.getByHotelIDAndGoodsID(goodsListVo.getHotelId(), goodsListVo.getGoodsId());
            if (goodsListVo.getGoodsStatus() == 0 && stockCount == 0) {
                //安全下架
                goodsListVoIterator.remove();
            } else {
                goodsListVo.setStockCount(stockCount);
            }
        }

        PageInfo<GoodsListVo> pageInfo = new PageInfo<>(dataList);
        return PageInfoResult.getPageInfoResult(pageInfo);
    }

    @Override
    public GoodsSkuListVo2 findGoodsSkuList(Integer hotelId, Integer goodsId) {
        //查询出所有商品规格基本信息
        List<GoodsSkuListVo> goodsSkuList = zlGoodsMapper.findGoodsSkuList(hotelId, goodsId);

        if (CollectionUtils.isEmpty(goodsSkuList)) {
            throw new BizException("参数有误!");
        }

        //开始对商品规格信息进行处理
        Set<String> propertyIdSet = new LinkedHashSet<>();
        for (GoodsSkuListVo goodsSkuListVo : goodsSkuList) {
            String propertyIds = goodsSkuListVo.getPropertyId();
            String[] split = propertyIds.split(",");
            for (String s : split) {
                propertyIdSet.add(s);
            }
        }
        List<GoodsSkuVO> goodsSkuVOList = new LinkedList<>();
        for (String propertyId : propertyIdSet) {
            GoodsSkuVO goodsSkuVO = zlGoodsMapper.findGoodsSku(propertyId);
            goodsSkuVOList.add(goodsSkuVO);
        }
//        Map<String, List<GoodsSkuVO>> GoodsSkuVOMap = new HashMap<>();
        List<List<GoodsSkuVO>> GoodsSkuVOLists = new LinkedList<>();

        Set<Integer> categoryIdSet = new LinkedHashSet<>();
        for (int i = 0; i < goodsSkuVOList.size(); i++) {
            categoryIdSet.add(goodsSkuVOList.get(i).getCategoryId());
        }
//        int count = 1;
        List<Integer> categoryIdList = new LinkedList<>();
        for (Integer categoryId : categoryIdSet) {
            categoryIdList.add(categoryId);
            List<GoodsSkuVO> goodsSkuVOS = new LinkedList<>();
            for (GoodsSkuVO goodsSkuVO : goodsSkuVOList) {
                if (goodsSkuVO.getCategoryId().equals(categoryId)) {
                    goodsSkuVOS.add(goodsSkuVO);
                }
            }
            GoodsSkuVOLists.add(goodsSkuVOS);
//            GoodsSkuVOMap.put(count + "", goodsSkuVOS);
//            count++;
        }
        GoodsSkuListVo goodsSkuListVo = goodsSkuList.get(0);
//        goodsSkuListVo.setPropertyMap(GoodsSkuVOMap);
//        goodsSkuListVo.setGoodsSkuVOLists(GoodsSkuVOLists);

        GoodsSkuListVo2 goodsSkuListVo2 = new GoodsSkuListVo2();
        goodsSkuListVo2.setGoodsId(goodsSkuListVo.getGoodsId());
        goodsSkuListVo2.setBelongModule(goodsSkuListVo.getBelongModule());
        goodsSkuListVo2.setCoverImgUrl(goodsSkuListVo.getCoverImgUrl());
        goodsSkuListVo2.setGoodsName(goodsSkuListVo.getGoodsName());
        goodsSkuListVo2.setCategoryIdList(categoryIdList);
        goodsSkuListVo2.setGoodsSkuVOLists(GoodsSkuVOLists);

        return goodsSkuListVo2;
    }

    @Override
    public GoodsListVo findGoodsDetail(Integer goodsId) {
        GoodsListVo goodsListVo = zlGoodsMapper.findGoodsDetail(goodsId);
        if (goodsListVo != null) {
            //修改该商品的访问量
            zlGoodsMapper.updateGoodsTotalVisitCount(goodsId);
            //查询该商品是否为多规格
            Integer skuCount = zlGoodsMapper.findSkuCount(goodsListVo.getGoodsId());
            if (skuCount > 1) {
                goodsListVo.setIsManySku(true);
                goodsListVo.setHotelGoodsSkuId(-1);
            } else {
                goodsListVo.setIsManySku(false);
            }
            //查询该商品的总库存
            Integer stockCount = zlGoodsMapper.getByHotelIDAndGoodsID(goodsListVo.getHotelId(), goodsListVo.getGoodsId());
            goodsListVo.setStockCount(stockCount);
        }
        return goodsListVo;
    }

    @Override
    public List<GoodsListVo> findRecommendGoodsList(Integer hotelId) {
        List<GoodsListVo> recommendGoodsList = zlGoodsMapper.findRecommendGoodsList(hotelId);
        if (CollectionUtils.isEmpty(recommendGoodsList)) {
            return null;
        }
        Iterator<GoodsListVo> goodsListVoIterator = recommendGoodsList.iterator();
        while (goodsListVoIterator.hasNext()) {
            GoodsListVo goodsListVo = goodsListVoIterator.next();
            if (goodsListVo.getGoodsStatus() == 0) {
                Integer stockCount = zlGoodsMapper.getByHotelIDAndGoodsID(hotelId, goodsListVo.getGoodsId());
                //安全下架
                if (stockCount == 0) {
                    goodsListVoIterator.remove();
                }
            }
        }
        return zlGoodsMapper.findRecommendGoodsList(hotelId);
    }

    @Override
    public void updateGoodsCount(String out_trade_no) {

        //拿出存在redis的订单商品短信息集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no)) {
            List<GoodsShortInfoVO> goodsShortInfoVOList = (List<GoodsShortInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
            for (GoodsShortInfoVO goodsShortInfoVO : goodsShortInfoVOList) {
                Integer hotelGoodsSkuID = goodsShortInfoVO.getHotelGoodsSkuID();
                Integer goodsCount = goodsShortInfoVO.getGoodsCount();

                Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);
                //更新mysql数据库库存
                zlGoodsMapper.updateGoods(hotelGoodsSkuID, goodsCount, currertTime);
                zlGoodsMapper.updateGoodsSku(hotelGoodsSkuID, goodsCount, currertTime);
                zlGoodsMapper.updateHotelGoodsSku(hotelGoodsSkuID, goodsCount, currertTime);

                //更新redis该商品数量(删除redis中锁定的商品数量)
                Integer count = (Integer) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                if (count.equals(goodsCount)) {
                    redisTemplate.delete(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID);
                } else {
                    redisTemplate.opsForValue().set(RedisKeyConstant.ORDER_HOTELGOODSSKUID_ID + hotelGoodsSkuID, count - goodsCount);
                }
            }
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO + out_trade_no);
            redisTemplate.delete(RedisKeyConstant.ORDER_ORDERSERIALNO_FLAG + out_trade_no);
        }

        //拿出存入redis的订单商品所使用的优惠券的集合
        if (redisTemplate.hasKey(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + out_trade_no)) {
            //如果该订单使用了优惠券
            //拿出存入redis的订单商品所使用的优惠券的集合
            List<GoodsCouponInfoVO> goodsCouponInfoVOList = (List<GoodsCouponInfoVO>) redisTemplate.opsForValue().get(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + out_trade_no);

            //利用迭代器遍历删除该订单在此模块下使用的的商品优惠券
            Iterator<GoodsCouponInfoVO> iteratorGoodsCouponInfo = goodsCouponInfoVOList.iterator();
            while (iteratorGoodsCouponInfo.hasNext()) {
                GoodsCouponInfoVO goodsCouponInfoVO = iteratorGoodsCouponInfo.next();
                Long couponUserId = goodsCouponInfoVO.getCouponUserId();
                //删除redis中锁定的优惠券
                redisTemplate.delete(RedisKeyConstant.ORDER_COUPONUSERID + couponUserId);
                iteratorGoodsCouponInfo.remove();
            }

            redisTemplate.delete(RedisKeyConstant.ORDER_COUPONUSERID_ORDERSERIALNO + out_trade_no);
        }

    }

    @Override
    public List<EsGoodsVO> searchGoods(Integer hotelId, String selectParam, Integer belongModule, Integer pageNo, Integer pageSize) {

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("belongmodule", belongModule))
                .must(QueryBuilders.termQuery("hotelid", hotelId))
                .must(QueryBuilders.matchQuery("goodsname", selectParam));

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(PageRequest.of(pageNo - 1, pageSize))
                .build();

        //获取结果集list集合
        List<EsGoods> esGoodsList = elasticsearchTemplate.queryForList(query, EsGoods.class);

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
            Integer stockCount = zlGoodsMapper.getByHotelIDAndGoodsID(esGoodsVO.getHotelid(), esGoodsVO.getGoodsid());
            esGoodsVO.setSoldcount(stockCount);
            esGoodsVOList.add(esGoodsVO);
        }

        return esGoodsVOList;
    }

    @Override
    public BusinessHoursVO getBusinessHours(Integer menuId) {
        BusinessHoursVO businessHoursVO = new BusinessHoursVO();
        ZlXcxmenu zlXcxmenu = zlXcxMenuMapper.getBusinessHours(menuId);

        if (zlXcxmenu != null) {
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
        }


        return businessHoursVO;
    }

    @Override
    public GoodsPropertysVO findGoodsPropertyIdInfo(Integer hotelId, Integer goodsId, List<Integer> goodsPropertyIdList) {
        List<GoodsPropertyInfoVO> goodsPropertyInfoVOList = zlGoodsMapper.findGoodsPropertyIdInfo(hotelId, goodsId);
        if (CollectionUtils.isEmpty(goodsPropertyInfoVOList)) {
            throw new BizException("参数有误!");
        }
        //将用户选择的规格信息组合成字符串
        Collections.sort(goodsPropertyIdList);
        String goodsPropertyIdStr = goodsPropertyIdList.toString();

        GoodsPropertysVO goodsPropertysVO = new GoodsPropertysVO();

        for (GoodsPropertyInfoVO goodsPropertyInfoVO : goodsPropertyInfoVOList) {
            List<Integer> list = new LinkedList<>();
            String propertyIds = goodsPropertyInfoVO.getPropertyId();
            String[] split = propertyIds.split(",");
            for (String s : split) {
                list.add(Integer.parseInt(s));
            }
            Collections.sort(list);
            String s = list.toString();
            if (goodsPropertyIdStr.equals(s)) {
                goodsPropertysVO.setHotelGoodsSkuId(goodsPropertyInfoVO.getHotelGoodsSkuId());
                goodsPropertysVO.setCurrentPrice(goodsPropertyInfoVO.getCurrentPrice());
                goodsPropertysVO.setOriginalPrice(goodsPropertyInfoVO.getOriginalPrice());
                goodsPropertysVO.setStockCount(goodsPropertyInfoVO.getStockCount());
                goodsPropertysVO.setPropertyIds(list);
                break;
            }
        }
        if (goodsPropertysVO == null) {
            throw new BizException("参数有误!");
        }

        return goodsPropertysVO;
    }

    @Override
    public GoodsPropertysVO findSelectedGoodsProperty(Integer hotelGoodsSkuId) {

        GoodsPropertyInfoVO goodsPropertyInfoVO = zlGoodsMapper.findSelectedGoodsProperty(hotelGoodsSkuId);
        if (goodsPropertyInfoVO == null) {
            throw new BizException("参数有误!");
        }

        GoodsPropertysVO goodsPropertysVO = new GoodsPropertysVO();

        List<Integer> list = new LinkedList<>();
        String propertyIds = goodsPropertyInfoVO.getPropertyId();
        String[] split = propertyIds.split(",");
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }
        Collections.sort(list);
        goodsPropertysVO.setHotelGoodsSkuId(goodsPropertyInfoVO.getHotelGoodsSkuId());
        goodsPropertysVO.setCurrentPrice(goodsPropertyInfoVO.getCurrentPrice());
        goodsPropertysVO.setOriginalPrice(goodsPropertyInfoVO.getOriginalPrice());
        goodsPropertysVO.setStockCount(goodsPropertyInfoVO.getStockCount());
        goodsPropertysVO.setPropertyIds(list);

        return goodsPropertysVO;
    }

    @Override
    public void updateGoodsCountReturn(Long orderid) {

        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        ZlOrderDetail zlOrderDetail = new ZlOrderDetail();
        zlOrderDetail.setOrderid(orderid);
        List<ZlOrderDetail> zlOrderDetailList = zlOrderDetailMapper.select(zlOrderDetail);

        for (ZlOrderDetail orderDetail : zlOrderDetailList) {
            Integer hotelGoodsSkuID = orderDetail.getHotelgoodsid();
            Integer goodsCount = orderDetail.getGoodscount();
            zlGoodsMapper.updateGoodsReturn(hotelGoodsSkuID, goodsCount, currertTime);
            zlGoodsMapper.updateGoodsSkuReturn(hotelGoodsSkuID, goodsCount, currertTime);
            zlGoodsMapper.updateHotelGoodsSkuReturn(hotelGoodsSkuID, goodsCount, currertTime);
        }

        //更新mysql数据库库存
    }
}

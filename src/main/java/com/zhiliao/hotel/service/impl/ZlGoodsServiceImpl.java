package com.zhiliao.hotel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhiliao.hotel.common.PageInfoResult;
import com.zhiliao.hotel.controller.goods.vo.EsGoods;
import com.zhiliao.hotel.controller.goods.vo.GoodsListVo;
import com.zhiliao.hotel.mapper.ZlGoodsMapper;
import com.zhiliao.hotel.model.ZlOrderDetail;
import com.zhiliao.hotel.service.ZlGoodsService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xiegege on 2020/4/15.
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlGoodsServiceImpl implements ZlGoodsService {


    private final ZlGoodsMapper zlGoodsMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public ZlGoodsServiceImpl(ZlGoodsMapper zlGoodsMapper) {
        this.zlGoodsMapper = zlGoodsMapper;
    }

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
    public List<Map<String, Object>> findGoodsSkuList(Integer goodsId) {
        return zlGoodsMapper.findGoodsSkuList(goodsId);
    }

    @Override
    public GoodsListVo findGoodsDetail(Integer goodsID) {
        GoodsListVo goodsListVo = zlGoodsMapper.findGoodsDetail(goodsID);
        if (goodsListVo != null) {
            zlGoodsMapper.updateTotalVisitCount(goodsID, goodsListVo.getTotalVisitCount());
        }
        return goodsListVo;
    }

    @Override
    public void updateGoodsCount(List<ZlOrderDetail> zlOrderDetailList) {
        for (ZlOrderDetail zlOrderDetail : zlOrderDetailList) {
            Integer goodsID = zlOrderDetail.getGoodsID();
            Integer goodsCount = zlOrderDetail.getGoodsCount();
            zlGoodsMapper.updateGoods(goodsID, goodsCount);
            zlGoodsMapper.updateGoodsSku(goodsID, goodsCount);
            zlGoodsMapper.updateHotelGoods(goodsID, goodsCount);
        }
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
                    esGoodsList.remove(esGoods);
                }
            }
        } else {
            while (iterator.hasNext()) {
                EsGoods esGoods = iterator.next();
                if (!esGoods.getHotelid().equals(hotelId) || !esGoods.getBelongmodule().equals(belongModule)) {
                    esGoodsList.remove(esGoods);
                }
            }
        }

        return esGoodsList;
    }
}
package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.mapper.*;
import com.zhiliao.hotel.model.*;
import com.zhiliao.hotel.service.ZlOrderProfitService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-12 11:13
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ZlOrderProfitServiceImpl implements ZlOrderProfitService {

    @Autowired
    private ZlOrderMapper zlOrderMapper;

    @Autowired
    private ZlSupplierHotelMapper zlSupplierHotelMapper;

    @Autowired
    private ZlSupplierHotelProfitrateMapper zlSupplierHotelProfitrateMapper;

    @Autowired
    private ZlSettingMapper zlSettingMapper;

    @Autowired
    private ZlHotelSettleinMapper zlHotelSettleinMapper;

    @Autowired
    private ZlS2BrokerageRuleMapper zlS2BrokerageRuleMapper;

    @Autowired
    private ZlS2BrokerageRuleRateMapper zlS2BrokerageRuleRateMapper;

    @Autowired
    private ZlOrderProfitMapper zlOrderProfitMapper;

    @Override
    public void setUpOrderProfit(String out_trade_no) {

        ZlOrder zlOrder = new ZlOrder();

        if (out_trade_no.startsWith("SM")) {
            zlOrder.setParentorderserialno(out_trade_no);
        } else {
            zlOrder.setOrderserialno(out_trade_no);
        }

        zlOrder.setPaystatus((byte) 2);
        zlOrder.setOrderstatus((byte) 1);
        zlOrder.setRefundstatus((byte) 0);
        zlOrder.setIsdelete(false);
        zlOrder.setIsuserdelete(false);
        List<ZlOrder> zlOrderList = zlOrderMapper.select(zlOrder);
        if (CollectionUtils.isEmpty(zlOrderList)) {
            throw new BizException("订单为空...");
        }

        for (ZlOrder order : zlOrderList) {
            //订单分润实体类
            ZlOrderProfit zlOrderProfit = new ZlOrderProfit();
            int currentTime = Math.toIntExact(System.currentTimeMillis() / 1000);

            Integer hotelid = order.getHotelid();
            Short belongmodule = order.getBelongmodule();
            Integer supplierid = order.getSupplierid();
            BigDecimal actuallypay = order.getActuallypay();

            //向订单分润实体类封装信息
            zlOrderProfit.setOrderid(order.getOrderid());
            zlOrderProfit.setHotelid(hotelid);
            zlOrderProfit.setSupplierid(supplierid);
            zlOrderProfit.setActuallypay(actuallypay);
            zlOrderProfit.setCreatedate(order.getCreatedate());
            zlOrderProfit.setUpdatedate(currentTime);

            //向供应商酒店关系实体类封装信息
            ZlSupplierHotel zlSupplierHotel = new ZlSupplierHotel();
            zlSupplierHotel.setHotelid(hotelid);
            zlSupplierHotel.setBelongmodule(belongmodule);
            zlSupplierHotel.setSupplierid(supplierid);

            //编写供应商分润业务
            zlSupplierHotel = zlSupplierHotelMapper.selectOne(zlSupplierHotel);

            BigDecimal supplierratemoney = new BigDecimal(0);//定义供应商分润金额
            BigDecimal hotelratemoney = new BigDecimal(0);//定义酒店分润金额
            BigDecimal s2partnerratemoney = new BigDecimal(0);//定义合伙人分润金额

            if (zlSupplierHotel != null) {
                ZlSupplierHotelProfitrate zlSupplierHotelProfitrate = new ZlSupplierHotelProfitrate();
                zlSupplierHotelProfitrate.setRecid(zlSupplierHotel.getRecid());
                zlSupplierHotelProfitrate.setIsdelete(false);
                List<ZlSupplierHotelProfitrate> zlSupplierHotelProfitrateList = zlSupplierHotelProfitrateMapper.select(zlSupplierHotelProfitrate);
                if (!CollectionUtils.isEmpty(zlSupplierHotelProfitrateList)) {
                    for (int i = 0; i < zlSupplierHotelProfitrateList.size(); i++) {
                        ZlSupplierHotelProfitrate supplierHotelProfitrate = zlSupplierHotelProfitrateList.get(i);
                        Integer effectdate = supplierHotelProfitrate.getEffectdate();
                        Integer invaliddate = supplierHotelProfitrate.getInvaliddate();
                        if ((currentTime >= effectdate && currentTime <= invaliddate) || (currentTime >= effectdate && invaliddate == 0)) {
                            BigDecimal supplierrate = supplierHotelProfitrate.getSupplierrate();
                            BigDecimal hotelrate = supplierHotelProfitrate.getHotelrate();
                            zlOrderProfit.setSupplierhotelprofitid(supplierHotelProfitrate.getSupplierhotelprofitrateid());
                            zlOrderProfit.setSupplierrate(supplierrate);
                            zlOrderProfit.setHotelrate(hotelrate);
                            supplierratemoney = supplierratemoney.add(actuallypay.multiply(supplierrate).divide(BigDecimal.valueOf(100)));
                            zlOrderProfit.setSupplierratemoney(supplierratemoney);
                            hotelratemoney = hotelratemoney.add(actuallypay.multiply(hotelrate).divide(BigDecimal.valueOf(100)));
                            zlOrderProfit.setHotelratemoney(hotelratemoney);
                            break;
                        } else {
                            if (i == zlSupplierHotelProfitrateList.size() - 1) {
                                Map<String, BigDecimal> orderProfit = new HashMap<>();
                                try {
                                    orderProfit = getOrderProfit(actuallypay);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                zlOrderProfit.setSupplierrate(orderProfit.get("supplierRate"));
                                zlOrderProfit.setHotelrate(orderProfit.get("hotelRate"));
                                zlOrderProfit.setSupplierratemoney(orderProfit.get("supplierRateMoney"));
                                zlOrderProfit.setHotelratemoney(orderProfit.get("hotelRateMoney"));
                            }
                        }
                    }

                } else {
                    Map<String, BigDecimal> orderProfit = new HashMap<>();
                    try {
                        orderProfit = getOrderProfit(actuallypay);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    supplierratemoney = supplierratemoney.add(orderProfit.get("supplierRateMoney"));
                    hotelratemoney = hotelratemoney.add(orderProfit.get("hotelRateMoney"));

                    zlOrderProfit.setSupplierrate(orderProfit.get("supplierRate"));
                    zlOrderProfit.setHotelrate(orderProfit.get("hotelRate"));
                    zlOrderProfit.setSupplierratemoney(supplierratemoney);
                    zlOrderProfit.setHotelratemoney(hotelratemoney);
                }
            } else {
                Map<String, BigDecimal> orderProfit = new HashMap<>();
                try {
                    orderProfit = getOrderProfit(actuallypay);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                zlOrderProfit.setSupplierrate(orderProfit.get("supplierRate"));
                zlOrderProfit.setHotelrate(orderProfit.get("hotelRate"));
                zlOrderProfit.setSupplierratemoney(orderProfit.get("supplierRateMoney"));
                zlOrderProfit.setHotelratemoney(orderProfit.get("hotelRateMoney"));
            }

            //编写合伙人分润业务
            ZlHotelSettlein zlHotelSettlein = new ZlHotelSettlein();
            zlHotelSettlein.setHotelid(hotelid);
            zlHotelSettlein.setJointype((byte) 2);
            zlHotelSettlein.setCheckstatus((byte) 4);
            zlHotelSettlein.setHotelstatus((byte) 2);
            zlHotelSettlein.setSettleinstatus((byte) 1);
            zlHotelSettlein.setIsdelete(false);
            zlHotelSettlein = zlHotelSettleinMapper.selectOne(zlHotelSettlein);
            if (zlHotelSettlein != null) {
                Integer createdate = zlHotelSettlein.getCreatedate();
                Integer extid = zlHotelSettlein.getExtid();
                ZlS2BrokerageRule zlS2BrokerageRule = new ZlS2BrokerageRule();
                zlS2BrokerageRule.setPartnerid(extid);
                zlS2BrokerageRule.setRulestatus((byte) 2);
                List<ZlS2BrokerageRule> zlS2BrokerageRuleList = zlS2BrokerageRuleMapper.select(zlS2BrokerageRule);
                for (ZlS2BrokerageRule s2BrokerageRule : zlS2BrokerageRuleList) {
                    Integer effectivedate = s2BrokerageRule.getEffectivedate();//生效时间
                    Integer invaliddate = s2BrokerageRule.getInvaliddate();//失效时间
                    Integer ruleid = s2BrokerageRule.getRuleid();//佣金规则id
                    Byte ruletype = s2BrokerageRule.getRuletype();//佣金率0:统一佣金率;1按模块佣金率
                    if ((currentTime >= effectivedate && currentTime <= invaliddate) || (currentTime >= effectivedate && invaliddate == 0)) {
                        ZlS2BrokerageRuleRate zlS2BrokerageRuleRate = new ZlS2BrokerageRuleRate();
                        zlS2BrokerageRuleRate.setRuleid(ruleid);
                        zlS2BrokerageRuleRate.setIsdelete(false);
                        List<ZlS2BrokerageRuleRate> zlS2BrokerageRuleRateList = zlS2BrokerageRuleRateMapper.select(zlS2BrokerageRuleRate);
                        if (ruletype == 0) {
                            for (ZlS2BrokerageRuleRate s2BrokerageRuleRate : zlS2BrokerageRuleRateList) {
                                Integer beginTime = s2BrokerageRuleRate.getHotelstaytimebegin() * 24 * 60 * 60 + createdate;
                                Integer endTime = s2BrokerageRuleRate.getHotelstaytimeend() * 24 * 60 * 60 + createdate;
                                if (currentTime >= beginTime && currentTime <= endTime) {
                                    BigDecimal rate = s2BrokerageRuleRate.getRate();
                                    zlOrderProfit.setS2partnerrateid(s2BrokerageRuleRate.getRateid());
                                    zlOrderProfit.setS2partnerrate(rate);

                                    s2partnerratemoney = actuallypay.subtract(supplierratemoney).subtract(hotelratemoney).multiply(rate).divide(BigDecimal.valueOf(100));
                                    zlOrderProfit.setS2partnerratemoney(s2partnerratemoney);
                                }
                            }
                        } else if (ruletype == 1) {
                            for (ZlS2BrokerageRuleRate s2BrokerageRuleRate : zlS2BrokerageRuleRateList) {
                                Integer beginTime = s2BrokerageRuleRate.getHotelstaytimebegin() * 24 * 60 * 60 + createdate;
                                Integer endTime = s2BrokerageRuleRate.getHotelstaytimeend() * 24 * 60 * 60 + createdate;
                                Short belongmodule2 = s2BrokerageRuleRate.getBelongmodule();
                                if (belongmodule2.equals(belongmodule)) {
                                    if (currentTime >= beginTime && currentTime <= endTime) {
                                        BigDecimal rate = s2BrokerageRuleRate.getRate();
                                        zlOrderProfit.setS2partnerrateid(s2BrokerageRuleRate.getRateid());
                                        zlOrderProfit.setS2partnerrate(rate);

                                        s2partnerratemoney = actuallypay.subtract(supplierratemoney).subtract(hotelratemoney).multiply(rate).divide(BigDecimal.valueOf(100));
                                        zlOrderProfit.setS2partnerratemoney(s2partnerratemoney);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            zlOrderProfitMapper.insertSelective(zlOrderProfit);
        }


    }

    public Map<String, BigDecimal> getOrderProfit(BigDecimal actuallypay) throws JSONException {
        Map<String, BigDecimal> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<String, Object>();
        ZlSetting zlSetting = new ZlSetting();
        zlSetting.setSettingkey("ProfitRuleSetting");
        zlSetting = zlSettingMapper.selectOne(zlSetting);
        String settingvalue = zlSetting.getSettingvalue();
        JSONObject json = null;
        try {
            json = new JSONObject(settingvalue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator it = json.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object value = json.get(key);
            map2.put(key, value);
        }
        BigDecimal supplierRate = getBigDecimal(map2.get("ProfitSupplier"));
        BigDecimal hotelRate = getBigDecimal(map2.get("ProfitHotel"));
//        BigDecimal supplierRate = (BigDecimal) map2.get("ProfitSupplier");
//        BigDecimal hotelRate = (BigDecimal) map2.get("ProfitHotel");
        map.put("supplierRate", supplierRate);
        map.put("hotelRate", hotelRate);

        BigDecimal supplierRateMoney = actuallypay.multiply((supplierRate).divide(BigDecimal.valueOf(100)));
        BigDecimal hotelRateMoney = actuallypay.multiply((hotelRate).divide(BigDecimal.valueOf(100)));

        map.put("supplierRateMoney", supplierRateMoney);
        map.put("hotelRateMoney", hotelRateMoney);

        return map;
    }

    public BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

}

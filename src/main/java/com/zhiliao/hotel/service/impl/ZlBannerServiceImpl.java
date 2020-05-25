package com.zhiliao.hotel.service.impl;

import com.google.common.collect.Lists;
import com.zhiliao.hotel.mapper.ZlBannerMapper;
import com.zhiliao.hotel.model.ZlBanner;
import com.zhiliao.hotel.service.ZlBannerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author 邓菡晨
 * @date 2020/4/14 10:37
 */

@Service
public class ZlBannerServiceImpl implements ZlBannerService {

    private final ZlBannerMapper zlBannerMapper;

    @Autowired
    public ZlBannerServiceImpl(ZlBannerMapper zlBannerMapper) {
        this.zlBannerMapper = zlBannerMapper;
    }

    /**
     * 查询所属轮播图
     *
     * @param hotelID
     * @param menuID
     * @return
     */
    @Override
    public List<ZlBanner> findBanner(Integer hotelID, Integer menuID) {

        List<ZlBanner> zlBanners = zlBannerMapper.findBanner(hotelID, menuID);

        int size = CollectionUtils.isEmpty(zlBanners) ? 0 : zlBanners.size();

        //判断是否有数据
        //数量等于0返回0
        if (size == 0) {
            return Lists.newArrayList();
        }

        //数量>=5则直接返回
        if (size == 5) {
            return zlBanners;
        }

        //数量小于5则向后台获取图片补足5张
        if (size < 5) {
            Integer page = 5 - size;
            List<ZlBanner> plaBanner = zlBannerMapper.findByPlaBanner(page);
            for (ZlBanner zlBanner : plaBanner) {
                zlBanners.add(zlBanner);
            }
        }
        return zlBanners;
    }
}

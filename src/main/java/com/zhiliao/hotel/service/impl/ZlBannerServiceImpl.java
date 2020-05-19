package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.mapper.ZlBannerMapper;
import com.zhiliao.hotel.model.ZlBanner;
import com.zhiliao.hotel.service.ZlBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 邓菡晨
 * @date 2020/4/14 10:37
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlBannerServiceImpl implements ZlBannerService {

    private final ZlBannerMapper zlBannerMapper;

    @Autowired
    public ZlBannerServiceImpl(ZlBannerMapper zlBannerMapper) {
        this.zlBannerMapper = zlBannerMapper;
    }

    /**
     * 查询所属轮播图
     * @param hotelID
     * @param menuID
     * @return
     */
    @Override
    public List<ZlBanner> findBanner(Integer hotelID, Integer menuID) {

        List<ZlBanner> zlBanners = zlBannerMapper.findBanner(hotelID, menuID);

        //判断是否有数据
        //数量等于0返回0
        if (zlBanners.size() == 0) {
            throw new RuntimeException("0");
        }

        //数量>=5则直接返回
        if (zlBanners.size() == 5) {
            return zlBanners;
        }

        //数量小于5则向后台获取图片补足5张
        if (zlBanners.size() < 5) {
            Integer page = 5 - zlBanners.size();
            List<ZlBanner> plaBanner = zlBannerMapper.findByPlaBanner(page);
            for (ZlBanner zlBanner : plaBanner) {
                zlBanners.add(zlBanner);
            }
            return zlBanners;
        }
        return null;
    }

}

package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlNews;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 咨讯mapper层接口
 */
public interface ZlNewsMapper extends Mapper<ZlNews> {

    List<ZlNews> findAllJiuDianId(@Param("hotelID") Integer hotelID, @Param("type") Integer type, @Param("status") Integer status);

    ZlNews findById(@Param("newsid") Integer newsid);

    @Select(" select NewsID,HotelID,Type,Author,Title,Content,ImgUrl,CreateDate,IsDelete\n" +
            "    from zl_news\n" +
            "    where   Type=1 and Status = 1 and HotelID=#{hotelId}   or (HotelID = 0) and IsDelete = 0 ")
    List<ZlNews>getNewsByHotel(@Param("hotelId") String hotelId);
}

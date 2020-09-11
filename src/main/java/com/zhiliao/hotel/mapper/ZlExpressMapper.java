package com.zhiliao.hotel.mapper;

import com.zhiliao.hotel.model.ZlExpress;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-15 17:10
 **/
public interface ZlExpressMapper extends Mapper<ZlExpress> {

    List<ZlExpress> getExpressList();

}

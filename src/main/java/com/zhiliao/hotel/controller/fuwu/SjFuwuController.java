package com.zhiliao.hotel.controller.fuwu;

import com.zhiliao.hotel.common.ReturnString;
import com.zhiliao.hotel.common.UserLoginToken;
import com.zhiliao.hotel.controller.fuwu.vo.FuwuListVo;
import com.zhiliao.hotel.service.SjFuwuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by xiegege on 2020/4/11.
 */

@Api(tags = "客房服务接口")
@RestController
@RequestMapping("fuwu")
public class SjFuwuController {

    private SjFuwuService sjFuwuService;

    @Autowired
    public SjFuwuController(SjFuwuService sjFuwuService) {
        this.sjFuwuService = sjFuwuService;
    }

    @ApiOperation(value = "客房服务商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "jiuDianId", dataType = "String", required = true, value = "酒店id")
    })
    @UserLoginToken
    @PostMapping("findFuwuList/{jiuDianId}")
    public ReturnString findFuwuList(String token, @PathVariable Integer jiuDianId) {
        try {
            List<FuwuListVo> fuwuListVos = sjFuwuService.findFuwuList(jiuDianId);
            // 存放商品类别
            List<String> titles = new ArrayList<>();
            // 处理后的数据
            Map<String, List<FuwuListVo>> dataMap = new LinkedHashMap<>();
            // 最终返回数据
            Map<String, Object> returnMap = new HashMap<>();
            // 存放商品类别名称
            String title;
            for (FuwuListVo fuwuListVo : fuwuListVos) {
                title = fuwuListVo.getTitle();
                // key存在就add进去
                if (dataMap.containsKey(title)) {
                    dataMap.get(title).add(fuwuListVo);
                } else {
                    // 不存在就新建一条key
                    List<FuwuListVo> dataList = new ArrayList<>();
                    dataList.add(fuwuListVo);
                    dataMap.put(title, dataList);
                }
            }
            dataMap.forEach((k, v) -> {
                titles.add(k);
            });
            returnMap.put("titles", titles);
            returnMap.put("dataList", dataMap);
            return new ReturnString(returnMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }

    @ApiOperation(value = "客房服务商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "token"),
            @ApiImplicitParam(paramType = "path", name = "fuwuId", dataType = "String", required = true, value = "客房服务商品id")
    })
    @UserLoginToken
    @PostMapping("findFuwuDetail/{fuwuId}")
    public ReturnString findFuwuDetail(String token, @PathVariable Integer fuwuId) {
        try {
            FuwuListVo fuwuListVo = sjFuwuService.findFuwuDetail(fuwuId);
            if (fuwuListVo == null) {
                return new ReturnString("客房服务商品不存在");
            }
            return new ReturnString(fuwuListVo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnString("获取出错");
        }
    }
}
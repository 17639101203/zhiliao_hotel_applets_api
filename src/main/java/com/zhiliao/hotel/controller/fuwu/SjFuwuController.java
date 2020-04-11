package com.zhiliao.hotel.controller.fuwu;

import com.zhiliao.hotel.service.SjFuwuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
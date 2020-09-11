package com.zhiliao.hotel.service;

import com.zhiliao.hotel.controller.feedback.dto.ZlFeedbackDTO;
import com.zhiliao.hotel.model.ZlFeedbackType;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-18 17:31
 **/
public interface ZlFeedbackService {

    List<ZlFeedbackType> getFeedbackTypeList();

    void addFeedback(Long userId, ZlFeedbackDTO zlFeedbackDTO);

}

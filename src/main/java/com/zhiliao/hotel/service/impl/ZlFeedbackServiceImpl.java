package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.common.exception.BizException;
import com.zhiliao.hotel.controller.feedback.dto.ZlFeedbackDTO;
import com.zhiliao.hotel.mapper.ZlFeedbackMapper;
import com.zhiliao.hotel.mapper.ZlFeedbackTypeMapper;
import com.zhiliao.hotel.mapper.ZlWxuserMapper;
import com.zhiliao.hotel.model.ZlFeedback;
import com.zhiliao.hotel.model.ZlFeedbackType;
import com.zhiliao.hotel.model.ZlWxuser;
import com.zhiliao.hotel.service.ZlFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: zhiliao_hotel_applets_api.git
 * @description
 * @author: 姬慧慧
 * @create: 2020-08-18 17:32
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class ZlFeedbackServiceImpl implements ZlFeedbackService {

    @Autowired
    private ZlFeedbackMapper zlFeedbackMapper;

    @Autowired
    private ZlFeedbackTypeMapper zlFeedbackTypeMapper;

    @Autowired
    private ZlWxuserMapper zlWxuserMapper;

    @Override
    public List<ZlFeedbackType> getFeedbackTypeList() {

        ZlFeedbackType zlFeedbackType = new ZlFeedbackType();
        zlFeedbackType.setIsdelete(false);
        List<ZlFeedbackType> zlFeedbackTypeList = zlFeedbackTypeMapper.select(zlFeedbackType);

        if (CollectionUtils.isEmpty(zlFeedbackTypeList)) {
            return null;
        }

        return zlFeedbackTypeList;
    }

    @Override
    public void addFeedback(Long userId, ZlFeedbackDTO zlFeedbackDTO) {

        //当前时间
        Integer currertTime = Math.toIntExact(System.currentTimeMillis() / 1000);

        ZlFeedbackType zlFeedbackType = new ZlFeedbackType();
        zlFeedbackType.setFeedbacktypeid(zlFeedbackDTO.getFeedbacktype());

        zlFeedbackType = zlFeedbackTypeMapper.selectOne(zlFeedbackType);
        if (zlFeedbackType == null) {
            throw new BizException("反馈类型Id有误!");
        }

        ZlFeedback zlFeedback = new ZlFeedback();
        zlFeedback.setFeedbacktype(zlFeedbackDTO.getFeedbacktype());
        zlFeedback.setContent(zlFeedbackDTO.getContent());

        if (!StringUtils.isEmpty(zlFeedbackDTO.getImgsurl())) {
            zlFeedback.setImgsurl(zlFeedbackDTO.getImgsurl());
        }

        ZlWxuser zlWxuser = new ZlWxuser();
        zlWxuser.setUserid(userId);
        zlWxuser = zlWxuserMapper.selectOne(zlWxuser);

        zlFeedback.setFeedbackpersonid(userId);
        zlFeedback.setFeedbackperson(zlWxuser.getNickname());
        zlFeedback.setComeformid(1);
        zlFeedback.setCreatedate(currertTime);
        zlFeedback.setUpdatedate(currertTime);

        zlFeedbackMapper.insertSelective(zlFeedback);
    }

}

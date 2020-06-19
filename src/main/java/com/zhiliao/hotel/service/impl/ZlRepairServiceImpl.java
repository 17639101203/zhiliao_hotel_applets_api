package com.zhiliao.hotel.service.impl;

import com.zhiliao.hotel.controller.Repair.params.RepairParam;
import com.zhiliao.hotel.controller.Repair.vo.RepairOrderVO;
import com.zhiliao.hotel.mapper.ZlRepairorderMapper;
import com.zhiliao.hotel.model.ZlRepairorder;
import com.zhiliao.hotel.service.ZlRepairService;
import com.zhiliao.hotel.utils.DateUtils;
import com.zhiliao.hotel.utils.OrderIDUtil;
import com.zhiliao.hotel.utils.TokenUtil;
import com.zhiliao.hotel.utils.UploadPhotoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ZlRepairServiceImpl implements ZlRepairService {

    @Autowired
    private ZlRepairorderMapper zlRepairorderMapper;


    @Override
    public void addRepairMsg(ZlRepairorder repair) {
//        zlRepairorderMapper.insertRepairorder(repair);
        zlRepairorderMapper.insertSelective(repair);
    }

    @Override
    public Map<String, Object> addRepairMsg(Long userid, RepairParam repairParam, MultipartFile multipartFile) {
        Map<String, Object> map = new HashMap<>();

        //说明不是第一次
        if (repairParam.getOrderid() != -1) {
            ZlRepairorder zlRepairorder = new ZlRepairorder();
            zlRepairorder.setOrderid(repairParam.getOrderid());
            List<ZlRepairorder> zlRepairorderList = zlRepairorderMapper.select(zlRepairorder);
            ZlRepairorder zlRepairorderOld = zlRepairorderList.get(0);
            StringBuffer imageurls = new StringBuffer();
            String imageurlOld = zlRepairorderOld.getImgurls();
            String imgurl = UploadPhotoUtil.uploadPhotoUtil(multipartFile);
            imageurls.append(imageurlOld).append("|").append(imgurl);
            zlRepairorderMapper.updateCommentImg(repairParam.getOrderid(), imageurls.toString());
            map.put("orderid", repairParam.getOrderid());
            return map;
        }

        Integer now = DateUtils.javaToPhpNowDateTime();
        String serialNumber = OrderIDUtil.createOrderID("");
        ZlRepairorder zlRepairorder = new ZlRepairorder();
        zlRepairorder.setUserid(userid);
        zlRepairorder.setHotelname(repairParam.getHotelname());  // 酒店名
        zlRepairorder.setHotelid(repairParam.getHotelid());     // 酒店ID
        zlRepairorder.setRoomid(repairParam.getRoomid());       // 客房ID
        zlRepairorder.setRoomnumber(repairParam.getRoomnumber());   //客房号
        zlRepairorder.setRemark(repairParam.getRemark());       // 备注信息
        zlRepairorder.setSerialnumber(serialNumber);  //订单号
        zlRepairorder.setCreatedate(now);
        zlRepairorder.setUpdatedate(now);
        if (multipartFile != null) {
            String imgurl = UploadPhotoUtil.uploadPhotoUtil(multipartFile);
            zlRepairorder.setImgurls(imgurl);  //图片地址
        }
        zlRepairorderMapper.insertSelective(zlRepairorder);
        map.put("orderid", zlRepairorder.getOrderid());
        return map;
    }


    @Override
    public RepairOrderVO findRepairOrder(Long orderID) {
        RepairOrderVO repairOrderVO = zlRepairorderMapper.queryRepairMsg(orderID);
        List<String> imageurllist = new LinkedList<>();
        if (StringUtils.isNoneBlank(repairOrderVO.getImgurls())) {
            String imageurls = repairOrderVO.getImgurls();
            if (imageurls.contains("|")) {
                String[] imageurlArr = imageurls.split("\\|");
                for (int i = 0; i < imageurlArr.length; i++) {
                    imageurllist.add(imageurlArr[i]);
                }
            } else {
                imageurllist.add(imageurls);
            }
        }
        repairOrderVO.setImageurllist(imageurllist);
        return repairOrderVO;
    }

    @Override
    public void cancelRepairOrder(Long orderID, Integer updatedate) {
        zlRepairorderMapper.removeRepairOrder(orderID, updatedate);
    }

    /**
     * 用户删除报修订单
     *
     * @param orderID
     */
    @Override
    public void userDeleteRepairOrder(Long orderID) {
        Integer updateDate = Math.toIntExact(System.currentTimeMillis() / 1000);
        zlRepairorderMapper.userDeleteRepairOrder(orderID, updateDate);
    }
}
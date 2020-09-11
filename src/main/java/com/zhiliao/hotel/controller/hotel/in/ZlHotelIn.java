package com.zhiliao.hotel.controller.hotel.in;

import com.zhiliao.hotel.controller.hotel.vo.HotelMoneyVO;
import com.zhiliao.hotel.controller.hotel.vo.ZlXcxmenuVO;
import com.zhiliao.hotel.model.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @chenrong
 * @created 2020/4/17
 */
@Data
public class ZlHotelIn implements Serializable {

    private String receptionTel;

    private List<ZlXcxmenuVO> zlXcxmenuVOList;


    public ZlHotelIn() {
        super();
    }

    public ZlHotelIn(ZlHotel zlHotel) {
        BeanUtils.copyProperties(zlHotel, this);
    }

    private Integer hotelID;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     *
     */
    private String coverUrl;

    /**
     * 客服电话, 多个用|隔开
     */
    private String tel;

    /**
     * 省编号(对应area表AreaID)
     */
    private Short provinceCode;

    /**
     * 市编号(对应area表)
     */
    private Short cityCode;

    /**
     * 区编号(对应area表)
     */
    private Short districtCode;

    /**
     * 酒店详细地址
     */
    private String address;

    /**
     * 最大允许挂账金额
     */
    private BigDecimal maxCreditCash;

    /**
     * 类型:1酒店;2民宿
     */
    private Byte hotelType;

    /**
     * 酒店状态:0关闭;1启用
     */
    private Boolean hotelStatus;

    /**
     * 今天的利润
     */
    private BigDecimal todayMoney;
    /**
     * 累计获得佣金
     */
    private BigDecimal allMoney;

    /**
     * 已提现佣金
     */
    private BigDecimal withdrewMoney;
    /**
     * 提现处理中佣金
     */
    private BigDecimal withdrawingMoney;

    /**
     * 入驻类型：0：非入驻型；1：酒店自己入驻；2：通过S2入驻
     */
    private Integer joinType;

    /**
     * 入驻酒店合作状态：-1终止合作;1已正常入驻
     */
    private Integer joinStatus;


    /**
     * 删除状态:0正常;1删除;
     */
    private Boolean isDelete;

    /**
     * 更新日期
     */
    private Integer updateDate;

    /**
     * 添加日期
     */
    private Integer createDate;

    private List<ZlBanner> zlBannerList;

    private List<ZlXcxmenu> zlXcxMenus;

    private ZlHotelroom hotelRoom;

    /**
     * 酒店公告信息
     */
    private List<ZlNews> zlNews;

    /**
     * 酒店起送金额和配送金额
     */
    private HotelMoneyVO hotelMoneyVO;

    /**
     * 服务时间
     */
    private Byte serviceTime;

}

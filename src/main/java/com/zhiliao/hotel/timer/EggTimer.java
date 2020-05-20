package com.zhiliao.hotel.timer;

import com.zhiliao.hotel.mapper.ZlOrderDetailMapper;
import com.zhiliao.hotel.mapper.ZlOrderMapper;
import com.zhiliao.hotel.model.ZlOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 待支付定时器
 */
public class EggTimer {
    private final Timer timer = new Timer();
    private final int createdate;
    public EggTimer(int createdate) {
        this.createdate = createdate;
    }

    @Autowired
    private ZlOrderMapper zlOrderMapper;
    @Autowired
    private ZlOrderDetailMapper zlOrderdetailMapper;

    public void start(final Long orderid) {

        timer.schedule(new TimerTask() {
            public void run() {
                playSound();
                timer.cancel();
            }
            private void playSound() {
                Integer payStatus = 1;
                ZlOrder order = zlOrderMapper.findById(orderid,payStatus);
                if (order != null){
                    order.setOrderstatus(-1);
                    order.setUpdatedate((int) new Date().getTime());
                }
                //修改订单表状态及时间
                zlOrderMapper.byOrderId(order);
                //修改订单详情表取消时间
                zlOrderdetailMapper.byOrderdetailId(order);
            }
        }, createdate + 900000);
    }

}

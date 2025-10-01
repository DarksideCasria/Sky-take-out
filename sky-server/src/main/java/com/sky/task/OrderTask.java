package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 * * * * ?")
    public void processTimeoutOrders() {
        log.info("处理超时订单,当前时间:{}", LocalDateTime.now());
        List<Orders> timeOutOrders = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, LocalDateTime.now().plusMinutes(-15));
        if(timeOutOrders != null && timeOutOrders.size() > 0) {
            timeOutOrders.forEach(order -> {
                log.info("处理超时订单,订单id:{}", order.getId());
                order.setStatus(Orders.CANCELLED);
                order.setCancelTime(LocalDateTime.now());
                order.setCancelReason("订单超时未支付,系统自动取消");
                orderMapper.update(order);
            });
        }
    }
    @Scheduled(cron = "0 0 1 * * ?")
    //@Scheduled(cron = "0/5 * * * * ?")
    public void processDeliveryOrders() {
        log.info("处理配送中的订单,当前时间:{}", LocalDateTime.now());
        List<Orders> deliveryInProgressOrders = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().plusMinutes(-60));
        if(deliveryInProgressOrders != null && deliveryInProgressOrders.size() > 0) {
            deliveryInProgressOrders.forEach(order -> {
                log.info("处理配送中的订单,订单id:{}", order.getId());
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            });
        }
    }
}

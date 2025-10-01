package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);


    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    PageResult findPreOrders(Integer page, Integer pageSize, Integer status);

    OrderVO findOrderDetail(Long id);

    void cancelOrder(Long id) throws Exception;

    void repetition(Long id);

    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO getStatics();

    OrderVO getOrderById(Long id);

    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    void delivery(Long id);

    void complete(Long id);
}

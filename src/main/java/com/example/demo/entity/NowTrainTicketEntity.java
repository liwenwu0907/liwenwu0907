package com.example.demo.entity;

import lombok.Data;

@Data
public class NowTrainTicketEntity {

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 序号
     */
    private String num;

    /**
     * 乘机人
     */
    private String passenger;


    /**
     * 工号
     */
    private String jobNum;
    /**
     * 员工类型
     */
    private String employeeType;
    /**
     * 出发时间
     */
    private String departureTime;
    /**
     * 出发站
     */
    private String departureStation;
    /**
     * 到达站
     */
    private String destination;
    /**
     * 车票类型
     */
    private String ticketType;
    /**
     * 出退改
     */
    private String exitType;
    /**
     * 票价
     */
    private String ticketPrice;
    /**
     * 保险费
     */
    private String premium;
    /**
     * 改签费
     */
    private String changeFee;
    /**
     * 退票费
     */
    private String refundFee;
    /**
     * 实收/实付
     */
    private String actualPay;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 机构/部门
     */
    private String department;
    /**
     * 申请单
     */
    private String applicationForm;
    /**
     * 出差事由
     */
    private String businessTripReason;
    /**
     * 是否超标
     */
    private String exceedsStandard;
    /**
     * 伙食补贴
     */
    private String foodAllowance;
    /**
     * 交通补贴
     */
    private String transportAllowance;

}

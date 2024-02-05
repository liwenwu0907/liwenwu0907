package com.example.demo.entity;

import lombok.Data;

@Data
public class NowPlaneTicketEntity {

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
     * 起飞时间
     */
    private String departureTime;
    /**
     * 出退改
     */
    private String exitType;
    /**
     * 航程
     */
    private String voyage;
    /**
     * 舱等
     */
    private String cabinType;
    /**
     * 成交净价
     */
    private String netPrice;
    /**
     * 民航基金
     */
    private String civilAviationFund;
    /**
     * 燃油税
     */
    private String fuelTax;
    /**
     * 税费
     */
    private String tax;
    /**
     * 保险费
     */
    private String premium;
    /**
     * 改签费
     */
    private String changeFee;
    /**
     * 改签服务费
     */
    private String changeServiceFee;
    /**
     * 退票服务费
     */
    private String refundServiceFee;
    /**
     * 退票费
     */
    private String refundFee;
    /**
     * 实收/实付
     */
    private String actualPay;
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

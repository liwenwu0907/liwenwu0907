package com.example.demo.entity;

import lombok.Data;

@Data
public class MonthCarEntity {

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 序号
     */
    private String num;

    /**
     * 乘车人
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
     * 用车日期
     */
    private String vehicleTime;
    /**
     * 出发城市
     */
    private String departureCity;
    /**
     * 到达城市
     */
    private String arrivalCity;

    /**
     * 用车渠道
     */
    private String vehicleChannel;
    /**
     * 消费金额
     */
    private String consumptionAmount;

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
     * 伙食补贴
     */
    private String foodAllowance;
    /**
     * 交通补贴
     */
    private String transportAllowance;

}

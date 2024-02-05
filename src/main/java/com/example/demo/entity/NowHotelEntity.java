package com.example.demo.entity;

import lombok.Data;

@Data
public class NowHotelEntity {

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 序号
     */
    private String num;

    /**
     * 入住人
     */
    private String checkInPerson;


    /**
     * 工号
     */
    private String jobNum;
    /**
     * 员工类型
     */
    private String employeeType;
    /**
     * 入住时间
     */
    private String checkInTime;
    /**
     * 离店日期
     */
    private String checkOutTime;

    /**
     * 酒店城市
     */
    private String hotelCity;
    /**
     * 酒店名称
     */
    private String hotelName;
    /**
     * 房型
     */
    private String houseType;
    /**
     * 星级
     */
    private String starLevel;
    /**
     * 价格
     */
    private String price;

    /**
     * 夜数
     */
    private String nightCount;
    /**
     * 退房手续费
     */
    private String checkOutFee;

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
     * 企业差标金额
     */
    private String enterpriseDifferenceAmount;
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

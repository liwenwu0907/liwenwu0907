package com.example.demo.entity;

import lombok.Data;

@Data
public class ElectronicInvoiceDTO {

    /**
     * 收件人
     */
    private String receiver;

    /**
     * 抄送人
     */
    private String ccTo;

    /**
     * 主题
     */
    private String subject;

    /**
     * 发票抬头
     */
    private String invoiceNo;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 电子邮件地址
     */
    private String address;


    /**
     * 后缀
     */
    private String suffix;
}

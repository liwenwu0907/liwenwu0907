package com.example.demo.entity;

import java.util.Date;

/**
 * @author <a href="mailto:liwenwu@gtmap.cn">liwenwu</a>
 * @version 2.0,
 * @description  退款信息
 */
public class WctJyTkxx {

    //退款id
    private String tkid;

    //退款单号
    private String tkdh;

    //退款状态
    private String tkzt;

    //订单ID
    private String ddid;

    //退款金额
    private Double tkje;

    //内网受理编号
    private String nwslbh;

    //退款时间
    private Date tksj;

    //订单编号
    private String ddbh;

    public String getTkid() {
        return tkid;
    }

    public void setTkid(String tkid) {
        this.tkid = tkid;
    }

    public String getTkdh() {
        return tkdh;
    }

    public void setTkdh(String tkdh) {
        this.tkdh = tkdh;
    }

    public String getTkzt() {
        return tkzt;
    }

    public void setTkzt(String tkzt) {
        this.tkzt = tkzt;
    }

    public String getDdid() {
        return ddid;
    }

    public void setDdid(String ddid) {
        this.ddid = ddid;
    }

    public Double getTkje() {
        return tkje;
    }

    public void setTkje(Double tkje) {
        this.tkje = tkje;
    }

    public String getNwslbh() {
        return nwslbh;
    }

    public void setNwslbh(String nwslbh) {
        this.nwslbh = nwslbh;
    }

    public Date getTksj() {
        return tksj;
    }

    public void setTksj(Date tksj) {
        this.tksj = tksj;
    }

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }
}

package com.example.demo.entity;

/**
 * @author <a href="mailto:liwenwu@gtmap.cn">liwenwu</a>
 * @version 2.0,
 * @description  申请人信息
 */
public class WctJySqrxx {

    //申请人id
    private String sqrid;

    //申请人名称
    private String sqrmc;

    //证件种类
    private String zjzl;

    //证件种类名称
    private String zjzlmc;

    //证件号
    private String zjh;

    //电话
    private String dh;

    //收费收税信息id
    private String sfssxxid;

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getSqrmc() {
        return sqrmc;
    }

    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    public String getZjzlmc() {
        return zjzlmc;
    }

    public void setZjzlmc(String zjzlmc) {
        this.zjzlmc = zjzlmc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getSfssxxid() {
        return sfssxxid;
    }

    public void setSfssxxid(String sfssxxid) {
        this.sfssxxid = sfssxxid;
    }
}

package com.example.demo.entity;

/**
 * @author <a href="mailto:liwenwu@gtmap.cn">liwenwu</a>
 * @version 2.0,
 * @description  核税信息明细
 */
public class WctJyHsmx {

    //核税明细ID
    private String hsmxid;

    //核税信息ID
    private String hsxxid;

    //明细种类
    private String mxzl;

    //明细种类名称
    private String mxzlmc;

    //应纳税额
    private Double ynse;

    //减免税额
    private Double jmse;

    //实际纳税额
    private Double sjnse;

    public String getHsmxid() {
        return hsmxid;
    }

    public void setHsmxid(String hsmxid) {
        this.hsmxid = hsmxid;
    }

    public String getHsxxid() {
        return hsxxid;
    }

    public void setHsxxid(String hsxxid) {
        this.hsxxid = hsxxid;
    }

    public String getMxzl() {
        return mxzl;
    }

    public void setMxzl(String mxzl) {
        this.mxzl = mxzl;
    }

    public String getMxzlmc() {
        return mxzlmc;
    }

    public void setMxzlmc(String mxzlmc) {
        this.mxzlmc = mxzlmc;
    }

    public Double getYnse() {
        return ynse;
    }

    public void setYnse(Double ynse) {
        this.ynse = ynse;
    }

    public Double getJmse() {
        return jmse;
    }

    public void setJmse(Double jmse) {
        this.jmse = jmse;
    }

    public Double getSjnse() {
        return sjnse;
    }

    public void setSjnse(Double sjnse) {
        this.sjnse = sjnse;
    }
}

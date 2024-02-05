package com.example.demo.entity;

/**
 * @author <a href="mailto:liwenwu@gtmap.cn">liwenwu</a>
 * @version 2.0,
 * @description  收费收税信息
 */
public class WctJySfssxx {

    //收费收税信息id
    private String sfssxxid;

    //项目主键
    private String xmid;

    //权利人类别
    private String qlrlb;

    //区县代码
    private String qxdm;

    //执收单位名称
    private String zsdwmc;

    //执收单位代码
    private String zsdwdm;

    //内网受理编号
    private String nwslbh;

    //缴费状态(0:未缴费 1:部分缴费 2:已缴费)
    private String jfzt;

    public String getSfssxxid() {
        return sfssxxid;
    }

    public void setSfssxxid(String sfssxxid) {
        this.sfssxxid = sfssxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getZsdwmc() {
        return zsdwmc;
    }

    public void setZsdwmc(String zsdwmc) {
        this.zsdwmc = zsdwmc;
    }

    public String getZsdwdm() {
        return zsdwdm;
    }

    public void setZsdwdm(String zsdwdm) {
        this.zsdwdm = zsdwdm;
    }

    public String getNwslbh() {
        return nwslbh;
    }

    public void setNwslbh(String nwslbh) {
        this.nwslbh = nwslbh;
    }

    public String getJfzt() {
        return jfzt;
    }

    public void setJfzt(String jfzt) {
        this.jfzt = jfzt;
    }
}

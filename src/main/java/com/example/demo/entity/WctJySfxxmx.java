package com.example.demo.entity;

/**
 * @author <a href="mailto:liwenwu@gtmap.cn">liwenwu</a>
 * @version 2.0,
 * @description  收费信息明细
 */
public class WctJySfxxmx {

    //收费项目ID
    private String sfxmid;

    //收费项目名称
    private String sfxmmc;

    //收费信息ID
    private String sfxxid;

    //收费项目代码
    private String sfxmdm;

    //数量
    private String sl;

    //减免金额
    private Double jmje;

    //应收金额
    private Double ysje;

    //实收金额
    private Double ssje;

    //收费比例
    private String sfbl;

    //收费项目标准
    private String sfxmbz;

    //金额单位
    private String jedw;

    //金额单位名称
    private String jedwmc;

    //计算方法
    private String jsff;

    //缴费终端
    private String jfzd;

    public String getSfxmid() {
        return sfxmid;
    }

    public void setSfxmid(String sfxmid) {
        this.sfxmid = sfxmid;
    }

    public String getSfxmmc() {
        return sfxmmc;
    }

    public void setSfxmmc(String sfxmmc) {
        this.sfxmmc = sfxmmc;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getSfxmdm() {
        return sfxmdm;
    }

    public void setSfxmdm(String sfxmdm) {
        this.sfxmdm = sfxmdm;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public Double getJmje() {
        return jmje;
    }

    public void setJmje(Double jmje) {
        this.jmje = jmje;
    }

    public Double getYsje() {
        return ysje;
    }

    public void setYsje(Double ysje) {
        this.ysje = ysje;
    }

    public Double getSsje() {
        return ssje;
    }

    public void setSsje(Double ssje) {
        this.ssje = ssje;
    }

    public String getSfbl() {
        return sfbl;
    }

    public void setSfbl(String sfbl) {
        this.sfbl = sfbl;
    }

    public String getSfxmbz() {
        return sfxmbz;
    }

    public void setSfxmbz(String sfxmbz) {
        this.sfxmbz = sfxmbz;
    }

    public String getJedw() {
        return jedw;
    }

    public void setJedw(String jedw) {
        this.jedw = jedw;
    }

    public String getJedwmc() {
        return jedwmc;
    }

    public void setJedwmc(String jedwmc) {
        this.jedwmc = jedwmc;
    }

    public String getJsff() {
        return jsff;
    }

    public void setJsff(String jsff) {
        this.jsff = jsff;
    }

    public String getJfzd() {
        return jfzd;
    }

    public void setJfzd(String jfzd) {
        this.jfzd = jfzd;
    }
}

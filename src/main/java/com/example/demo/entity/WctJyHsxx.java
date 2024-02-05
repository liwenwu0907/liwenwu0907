package com.example.demo.entity;

/**
 * @author <a href="mailto:liwenwu@gtmap.cn">liwenwu</a>
 * @version 2.0,
 * @description  核税信息
 */
public class WctJyHsxx {

    //核税信息ID
    private String hsxxid;

    //核定计税价格
    private Double hdjsjg;

    //完税状态
    private String wszt;

    //完税状态名称
    private String wsztmc;

    //应纳税额合计
    private Double ynsehj;

    //减免税额合计
    private Double jmsehj;

    //实际应征合计
    private Double sjyzhj;

    //税务机关代码
    private String swjgdm;

    //纳税人识别号
    private String nsrsbh;

    //税票号码
    private String sphm;

    //收费收税信息id
    private String sfssxxid;

    public String getHsxxid() {
        return hsxxid;
    }

    public void setHsxxid(String hsxxid) {
        this.hsxxid = hsxxid;
    }

    public Double getHdjsjg() {
        return hdjsjg;
    }

    public void setHdjsjg(Double hdjsjg) {
        this.hdjsjg = hdjsjg;
    }

    public String getWszt() {
        return wszt;
    }

    public void setWszt(String wszt) {
        this.wszt = wszt;
    }

    public String getWsztmc() {
        return wsztmc;
    }

    public void setWsztmc(String wsztmc) {
        this.wsztmc = wsztmc;
    }

    public Double getYnsehj() {
        return ynsehj;
    }

    public void setYnsehj(Double ynsehj) {
        this.ynsehj = ynsehj;
    }

    public Double getJmsehj() {
        return jmsehj;
    }

    public void setJmsehj(Double jmsehj) {
        this.jmsehj = jmsehj;
    }

    public Double getSjyzhj() {
        return sjyzhj;
    }

    public void setSjyzhj(Double sjyzhj) {
        this.sjyzhj = sjyzhj;
    }

    public String getSwjgdm() {
        return swjgdm;
    }

    public void setSwjgdm(String swjgdm) {
        this.swjgdm = swjgdm;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getSphm() {
        return sphm;
    }

    public void setSphm(String sphm) {
        this.sphm = sphm;
    }

    public String getSfssxxid() {
        return sfssxxid;
    }

    public void setSfssxxid(String sfssxxid) {
        this.sfssxxid = sfssxxid;
    }
}

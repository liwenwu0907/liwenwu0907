package com.example.demo.entity;

/**
 * @author <a href="mailto:liwenwu@gtmap.cn">liwenwu</a>
 * @version 2.0,
 * @description  订单信息
 */
public class WctJyDdxx {

    //订单id
    private String ddid;

    //订单编号
    private String ddbh;

    //订单金额
    private Double ddje;

    //缴费状态(0：未缴费  1：已缴费,2:已退款，3:退款中)
    private String jfzt;

    //订单状态(0：关闭  1：激活，2：待支付)
    private String ddzt;

    //内网受理编号
    private String nwslbh;

    //缴费URL
    private String url;

    //第三方订单编号
    private String dsfddbh;

    //总额
    private Double ze;

    //税费关联id(1: SFXXID（收费信息主键）、2: SFXMID（收费项目主键）、3: HSXXID（核税信息主键）、4: HSMXID（核税明细信息主键）、5：SFSSXXID（收费收税信息主键）
    //放对应的主键)
    private String sfglid;

    //税费关联id类型(1: SFXXID（收费信息主键）、2: SFXMID（收费项目主键）、3: HSXXID（核税信息主键）、4: HSMXID（核税明细信息主键）、5：SFSSXXID（收费收税信息主键）
    //放代码)
    private String sfglidlx;

    public String getDdid() {
        return ddid;
    }

    public void setDdid(String ddid) {
        this.ddid = ddid;
    }

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public Double getDdje() {
        return ddje;
    }

    public void setDdje(Double ddje) {
        this.ddje = ddje;
    }

    public String getJfzt() {
        return jfzt;
    }

    public void setJfzt(String jfzt) {
        this.jfzt = jfzt;
    }

    public String getDdzt() {
        return ddzt;
    }

    public void setDdzt(String ddzt) {
        this.ddzt = ddzt;
    }

    public String getNwslbh() {
        return nwslbh;
    }

    public void setNwslbh(String nwslbh) {
        this.nwslbh = nwslbh;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDsfddbh() {
        return dsfddbh;
    }

    public void setDsfddbh(String dsfddbh) {
        this.dsfddbh = dsfddbh;
    }

    public Double getZe() {
        return ze;
    }

    public void setZe(Double ze) {
        this.ze = ze;
    }

    public String getSfglid() {
        return sfglid;
    }

    public void setSfglid(String sfglid) {
        this.sfglid = sfglid;
    }

    public String getSfglidlx() {
        return sfglidlx;
    }

    public void setSfglidlx(String sfglidlx) {
        this.sfglidlx = sfglidlx;
    }


}

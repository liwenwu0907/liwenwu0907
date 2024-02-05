package com.example.demo.util.msdatasource;

/**
 * @author li wenwu
 * @date 2020/12/22 19:44
 */
public enum DataSourceType {

    READ("read"),
    WRITE("write");

    private String type;


    DataSourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

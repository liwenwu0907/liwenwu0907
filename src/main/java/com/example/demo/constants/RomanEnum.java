package com.example.demo.constants;

public enum RomanEnum {

    I("I", 1),
    V("V", 5),
    X("X", 10),
    L("L", 50),
    C("C", 100),
    D("D", 500),
    M("M", 1000),
    ;

    private String code;

    private int value;

    RomanEnum(String code, int value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

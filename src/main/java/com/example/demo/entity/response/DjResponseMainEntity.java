package com.example.demo.entity.response;

/**
 * @author <a href="mailto:liwenwu@gtmap.cn">liwenwu</a>
 * @version 2.0,
 * @description  返回登记的实体类
 */
public class DjResponseMainEntity<T> {

    private DjResponseMainHeadEntity head;

    private T data;

    public DjResponseMainEntity(String code, T t) {
        DjResponseMainHeadEntity head = new DjResponseMainHeadEntity();
        head.setStatusCode(code);
        head.setReturncode(code);
        this.data = t;
        this.head = head;
    }


    //无参构造函数
    public DjResponseMainEntity() {
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

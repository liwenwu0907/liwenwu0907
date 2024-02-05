package com.example.demo.entity.request;

import com.example.demo.entity.response.DjResponseMainHeadEntity;

/**
 * @author <a href="mailto:liwenwu@gtmap.cn">liwenwu</a>
 * @version 2.0,
 * @description  请求登记的实体类
 */
public class DjRequestMainEntity<T> {

    public DjRequestMainEntity() {
        System.out.println("构造器执行");
    }

    private DjResponseMainHeadEntity head;
    private T data;

    public DjResponseMainHeadEntity getHead() {
        return head;
    }

    public void setHead(DjResponseMainHeadEntity head) {
        this.head = head;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}

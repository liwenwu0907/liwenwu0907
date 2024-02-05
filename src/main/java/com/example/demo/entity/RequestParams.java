package com.example.demo.entity;

import lombok.Data;

import java.util.UUID;


@Data
public  class RequestParams {
        /**
         * id
         */
        private String id = UUID.randomUUID().toString().replace("-", "");

        /**
         * 时间
         */
        private Long timestamp = System.currentTimeMillis();

        /**
         * 资源类型
         */
        private String resourceType = "encrypt";

        /**
         * 事件类型
         */
        private String eventType;


        /**
         *   应用Id
         */
        private String appId;


        /**
         * 参数（用户信息）
         */
        private ParamsResource resource;

        public RequestParams(String eventType) {
            this.eventType = eventType;
        }
        public RequestParams() {

        }
    }
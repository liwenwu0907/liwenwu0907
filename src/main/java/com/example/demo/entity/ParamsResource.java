package com.example.demo.entity;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;


@Data
public class ParamsResource {

        /**
         * 编码
         */
        private String algorithm = "AEAD_AES_256_GCM";

        /**
         * 加密后的用户数据
         */
        private String ciphertext;

        /**
         * 关联数据
         */
        private String associatedData;

        /**
         * 随机数
         */
        private String nonce = RandomStringUtils.randomNumeric(12);

        public ParamsResource(String associatedData) {
                this.associatedData = associatedData;
        }

        public ParamsResource(){}
    }
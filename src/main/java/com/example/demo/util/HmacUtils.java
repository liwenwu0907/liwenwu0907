package com.example.demo.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author xuming
 * @data 2023-07-19 11:44
 */
public class HmacUtils {


    public static final String HMAC_SHA256 = "HmacSHA256";

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String sha256(String key, String message) {
        try {
            // 生成密钥
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            // 创建HMAC-SHA256的Mac实例
            Mac mac = Mac.getInstance(HMAC_SHA256);
            // 初始化Mac实例，并设置密钥
            mac.init(secretKey);
            // 计算认证码
            byte[] hmac = mac.doFinal(message.getBytes());
            return bytesToHex(hmac);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

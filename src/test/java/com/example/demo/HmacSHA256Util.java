package com.example.demo;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class HmacSHA256Util {

    /**
     * HmacSHA256算法,返回的结果始终是32位
     *
     * @param key     加密的键，可以是任何数据
     * @param content 待加密的内容
     * @return 加密后的内容
     * @throws Exception
     */
    public static byte[] hmacSHA256(byte[] key, byte[] content) throws Exception {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(new SecretKeySpec(key, 0, key.length, "HmacSHA256"));
        byte[] hmacSha256Bytes = hmacSha256.doFinal(content);
        return hmacSha256Bytes;
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String hmacSHA256(String secret, String message) throws Exception {
        String hash = "";
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        hmacSha256.init(secret_key);
        byte[] bytes = hmacSha256.doFinal(message.getBytes());
        hash = byteArrayToHexString(bytes);
        return hash;
    }

    public static String generateSecretKeyStr() throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(generateSecretKey().getEncoded());
    }

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        return keyGenerator.generateKey();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(generateSecretKeyStr());
//        String appSecret = "b06c75b58d1701ff470119a4114f8b45";
        //9e3c6d1210acc2c1e202f636a9952e18654c3b801885610236dc4fb51fbc438f
        String appSecret = UUID.randomUUID().toString().replaceAll("-","");

        String str = "";
        try {
            str = HmacSHA256Util.hmacSHA256(appSecret, "message2");
            System.out.println("加密后 =========>" + str);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========>" + e.getMessage());
        }

    }


}

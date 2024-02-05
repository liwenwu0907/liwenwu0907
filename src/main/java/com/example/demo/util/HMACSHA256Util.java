//package com.example.demo.util;
//
//import com.justai.icp.common.core.exception.ServiceException;
//import org.apache.commons.lang3.StringUtils;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.Mac;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//
//public class HMACSHA256Util {
//
//    public static void main(String[] args) throws Exception {
//        String message = "Hello, World!";
//        String aesKey = "AnjiPLUSAjReport";
////        String key = generateSecretKeyAndAESEncrpt(aesKey);
////        System.out.println(universallyEncode(key,message,aesKey));
//    }
//
//    /**
//     * key为经过AES加密后的密钥（数据库里存的），plaintext为明文，aesKey为AES解密密钥
//     * @description 通用加密算法
//     * @author liwenwu
//     * @time 2023/7/18
//     */
//    public static String universallyEncode(String key,String plaintext,String aesKey,byte[] associatedData, byte[] nonce) throws Exception {
//        String result = null;
//        if(StringUtils.isNoneBlank(key,plaintext)){
//            if(StringUtils.isBlank(aesKey)){
//                throw new ServiceException("AES加密算法的密钥不可为空，aesKey：" + aesKey);
//            }
//            //经过AES解密获取
//            String trueKey = AESUtil.decrypt(key,aesKey.getBytes(StandardCharsets.UTF_8),associatedData,nonce);
//            //AES/ECB/PKCS5Padding这种不需要associatedData，nonce
//            //String trueKey = AESUtil.aesDecrypt(key,aesKey);
//            if(StringUtils.isBlank(trueKey)){
//                throw new ServiceException("AES解密错误");
//            }
//            //加密生成密文
//            SecretKeySpec secretKey = new SecretKeySpec(trueKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//
//            // 创建HMAC-SHA256的Mac实例
//            Mac mac = Mac.getInstance("HmacSHA256");
//            // 初始化Mac实例，并设置密钥
//            mac.init(secretKey);
//            // 计算认证码
//            byte[] hmac = mac.doFinal(plaintext.getBytes());
//            result = bytesToHex(hmac);
//        }else {
//            throw new ServiceException("HmacSHA256加密算法的密钥和明文不可为空，key：" + key + "，plaintext：" + plaintext);
//        }
//
//        return result;
//    }
//
//    /**
//     * @description 生成密钥字符串
//     * @author liwenwu
//     * @time 2023/7/18
//     */
//    public static String generateSecretKeyStr() throws NoSuchAlgorithmException {
//        return Base64.getEncoder().encodeToString(generateSecretKey().getEncoded());
//    }
//
//    /**
//     * @description 生成密钥字符串并经过AES加密之后的结果
//     * @author liwenwu
//     * @time 2023/7/18
//     */
//    public static String generateSecretKeyAndAESEncrpt(String aesKey,byte[] associatedData, byte[] nonce) throws Exception {
//        if(StringUtils.isBlank(aesKey)){
//            throw new ServiceException("AES加密算法的密钥不可为空，aesKey：" + aesKey);
//        }
//        String keyStr = Base64.getEncoder().encodeToString(generateSecretKey().getEncoded());
//        String result = AESUtil.encrypt(keyStr,aesKey.getBytes(StandardCharsets.UTF_8),associatedData,nonce);
//        //AES/ECB/PKCS5Padding这种不需要associatedData，nonce
//        //String result = AESUtil.aesEncrypt(keyStr,aesKey)
//        return result;
//    }
//
//
//    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
//        return keyGenerator.generateKey();
//    }
//
//    public static String bytesToHex(byte[] bytes) {
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : bytes) {
//            String hex = Integer.toHexString(0xff & b);
//            if (hex.length() == 1) {
//                hexString.append('0');
//            }
//            hexString.append(hex);
//        }
//        return hexString.toString();
//    }
//}
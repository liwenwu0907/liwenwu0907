package com.example.demo.util.encryptionAlgorithm;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RSAUtils {
    /** 算法名称 */
    private static final String ALGORITHM = "RSA";

    /** 密钥长度 */
    private static final int KEY_SIZE = 2048;

    /**
     * 随机生成密钥对（包含公钥和私钥）
     */
    public static KeyPair generateKeyPair() throws Exception {
        // 获取指定算法的密钥对生成器
        KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM);

        // 初始化密钥对生成器（指定密钥长度, 使用默认的安全随机数源）
        gen.initialize(KEY_SIZE);

        // 随机生成一对密钥（包含公钥和私钥）
        return gen.generateKeyPair();
    }

    /**
     * 将 公钥/私钥 编码后以 Base64 的格式保存到指定文件
     */
    public static void saveKeyForEncodedBase64(Key key, File keyFile) throws IOException {
        // 获取密钥编码后的格式
        byte[] encBytes = key.getEncoded();

        // 转换为 Base64 文本
        String encBase64 = new BASE64Encoder().encode(encBytes);

        // 保存到文件
       writeFile(encBase64, keyFile);
    }

    /**
     * 根据公钥的 Base64 文本创建公钥对象
     */
    public static PublicKey getPublicKey(String pubKeyBase64) throws Exception {
        // 把 公钥的Base64文本 转换为已编码的 公钥bytes
        byte[] encPubKey = new BASE64Decoder().decodeBuffer(pubKeyBase64);

        // 创建 已编码的公钥规格
        X509EncodedKeySpec encPubKeySpec = new X509EncodedKeySpec(encPubKey);

        // 获取指定算法的密钥工厂, 根据 已编码的公钥规格, 生成公钥对象
        return KeyFactory.getInstance(ALGORITHM).generatePublic(encPubKeySpec);
    }

    /**
     * 根据私钥的 Base64 文本创建私钥对象
     */
    public static PrivateKey getPrivateKey(String priKeyBase64) throws Exception {
        // 把 私钥的Base64文本 转换为已编码的 私钥bytes
        byte[] encPriKey = new BASE64Decoder().decodeBuffer(priKeyBase64);

        // 创建 已编码的私钥规格
        PKCS8EncodedKeySpec encPriKeySpec = new PKCS8EncodedKeySpec(encPriKey);

        // 获取指定算法的密钥工厂, 根据 已编码的私钥规格, 生成私钥对象
        return KeyFactory.getInstance(ALGORITHM).generatePrivate(encPriKeySpec);
    }

    /**
     * 公钥加密数据
     */
    public static byte[] encrypt(byte[] plainData, PublicKey pubKey) throws Exception {
        // 获取指定算法的密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        // 初始化密码器（公钥加密模型）
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        // 加密数据, 返回加密后的密文
        return cipher.doFinal(plainData);
    }

    /**
     * 私钥加密
     * @author  liwenwu
     * @date  2021/12/8
     **/
    public static byte[] encrypt(byte[] plainData, PrivateKey priKey) throws Exception {
        // 获取指定算法的密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        // 初始化密码器（公钥加密模型）
        cipher.init(Cipher.ENCRYPT_MODE, priKey);

        // 加密数据, 返回加密后的密文
        return cipher.doFinal(plainData);
    }

    /**
     * 私钥解密数据
     */
    public static byte[] decrypt(byte[] cipherData, PrivateKey priKey) throws Exception {
        // 获取指定算法的密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        // 初始化密码器（私钥解密模型）
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        // 解密数据, 返回解密后的明文
        return cipher.doFinal(cipherData);
    }

    /**
     * 公钥解密
     * @author  liwenwu
     * @date  2021/12/8
     **/
    public static byte[] decrypt(byte[] cipherData, PublicKey pubKey) throws Exception {
        // 获取指定算法的密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        // 初始化密码器（私钥解密模型）
        cipher.init(Cipher.DECRYPT_MODE, pubKey);

        // 解密数据, 返回解密后的明文
        return cipher.doFinal(cipherData);
    }

    /**
     * 写文件
     * @param data
     * @param file
     * @throws IOException
     */
    public static void writeFile(String data, File file) throws IOException {
        OutputStream out = null;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            out.write(data.getBytes());
            out.flush();
        } finally {
            close(out);
        }
    }

    /**
     * 关闭
     * @param c
     */
    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                // nothing
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //生成一对公钥
        KeyPair keyPair = RSAUtils.generateKeyPair();

        PublicKey pubKey = keyPair.getPublic();
        PrivateKey priKey = keyPair.getPrivate();

        //保存到文件中
        RSAUtils.saveKeyForEncodedBase64(pubKey,new File("E:\\pub.txt"));
        RSAUtils.saveKeyForEncodedBase64(priKey,new File("E:\\pri.txt"));
        test();
    }

    /**
     * 加解密示例
     * @author  liwenwu
     * @date  2021/12/8
     **/
    public static void test()throws Exception{
        PublicKey pubKey = RSAUtils.getPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi7BIblqd0griK1QvYdthy9fKOpJLKOzD\n" +
                "2orz5RflNlCFGu8NS42sRT9W2OykumOXzOx5jlgVbKs6MT6Ra97d/dlbYgGh4d3x+TlMAibc1Uxi\n" +
                "adSufxJ5w/l2Y4kdMcUTPlFsqWpjHuSBDEp8/z2uxWVamgxee5IRTDgiNA7O5WzDk/S8SyMCLpJI\n" +
                "k7svARe4xglcMsfATIdMuPJoEcItGuM5+okFXOqiTSG8/TB1pY0GiIkrVWJX7a5dsz3bBv9vCZYd\n" +
                "jUC3jMbQVptl4bEhSxzKjvw40bYZ7rVtGUmKIl7paFt/we7ffg/U4K7lbPe4Z2oGPJmje+FrAMRK\n" +
                "cxSbXQIDAQAB");
        PrivateKey priKey = RSAUtils.getPrivateKey("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCLsEhuWp3SCuIrVC9h22HL18o6\n" +
                "kkso7MPaivPlF+U2UIUa7w1LjaxFP1bY7KS6Y5fM7HmOWBVsqzoxPpFr3t392VtiAaHh3fH5OUwC\n" +
                "JtzVTGJp1K5/EnnD+XZjiR0xxRM+UWypamMe5IEMSnz/Pa7FZVqaDF57khFMOCI0Ds7lbMOT9LxL\n" +
                "IwIukkiTuy8BF7jGCVwyx8BMh0y48mgRwi0a4zn6iQVc6qJNIbz9MHWljQaIiStVYlftrl2zPdsG\n" +
                "/28Jlh2NQLeMxtBWm2XhsSFLHMqO/DjRthnutW0ZSYoiXuloW3/B7t9+D9TgruVs97hnagY8maN7\n" +
                "4WsAxEpzFJtdAgMBAAECggEAV9A/CFig4/0BhzmJ+9t9FDcYo096PkTxLn6xqjAmYMmnk3H6CqgK\n" +
                "RxH1V7MULzQSprl2BPzsov7VO0XZB1X4ee9dGjRqfuIWjpTtamDh7JjzORKBi9Kih4dYXGRC3RnF\n" +
                "6iQnyizeGPqPAZoGEDLHnsIDfdoOv2Hoy+Bx6AXzHJggIdVfR9j9SnGQ3wxaLG5SAweL7m/Vxgda\n" +
                "mT4TPfYF53Whszg6+F4CBgEMHCx5ReE54IabNgIBXBJ7XPXtylSNCuWo9+Z2Qy/XzIIPmyuSk6DO\n" +
                "iwJPD8fzBnLb6q2mCHZNDlehQzcLGKpJLRanGBIFRxfG7WOLkXP2AybsGhTbhQKBgQDGtkaBuZGd\n" +
                "ERGLBfFKu+issWhp4KxqIFLINy0jiiJgMlpmTW7ziqoXadYk28jht8LhC85nXCpOrzoIkq4Unky4\n" +
                "edzUQzDcotXqoGw3Q2odrg3i85/tKscUH/IqqcyBskH7o+IYXYBZ9SwYXOy/vNBIxvscj1Nw13NC\n" +
                "W9HoVoJiIwKBgQCz9dlSBJbAGBw5mFHNsnMKmxJtq3sCIF3/JNPSz9UdfDNkozQqiS27KZDqAFs1\n" +
                "sGUOerHU8H5S3SkeYaFPz9aAgVptmVP2hHhNEzk6oMSbnONYZl7i3oJjltb6yz5FDWSoMcUL1XIE\n" +
                "nSGwOlAhTyNevkV0X26E4ZbBqDQGDhMkfwKBgCncUDIX05MQ+28cC17BAx0OKn7Tc77PnGDNLtcj\n" +
                "zGYTdXPDsDHh7j8Mbq5JFqJmQcmC4TJo+gilZUZVEBML3gGmulyE4xA3xXSt4t4pd3tVJVvAEtq1\n" +
                "fkbBDBTsvEY+NSQ7u++CEv3gZi+js8QYlJAgStoTX3KQEJnUGf4l60WdAoGAeGVBCBsj084NjAa4\n" +
                "q7n4Nx85+/8eY3TODfVQdZgvxvMh2vFI5DA0VSPg3HxyiP/DDCXPtVtT2BI4AShl6GRu6DgiDHLo\n" +
                "9mL3rPDwd6W5Aza57plGX9dnOkNe/hhdR4tHTTNG1bzt8+I/+It6sl4klq0LAGkcInBeEJ98dpSR\n" +
                "//cCgYBNy+OZBIzC84uw3w9pbItvxREd9rSsGqPA3ncpblEyW1LxCoXS1gQIwzsYICObxs7IRZ3t\n" +
                "Mxi0hnAOYBREB/5mXsYu52atOnF6GFc9HAq/OpnzhqVB42Irjo+csPWusO1IRhZn9kZblmmAqdBF\n" +
                "oTKnm0un4AIkHMcSb8Imq3sBnQ==");

        //公钥加密
        byte[] encrypt = RSAUtils.encrypt("加密串".getBytes(StandardCharsets.UTF_8),pubKey);
        String pubEncrypt = new BASE64Encoder().encode(encrypt);
        System.out.println("加密结果：" + pubEncrypt);
        //私钥解密
        byte[] decrypt = RSAUtils.decrypt(new BASE64Decoder().decodeBuffer(pubEncrypt),priKey);
        System.out.println("解密结果：" + new String(decrypt,StandardCharsets.UTF_8));
    }
}

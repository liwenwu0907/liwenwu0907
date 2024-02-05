package com.example.demo.util;


import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class AESUtil {
    public static final String CIPHER_TRANSFORM = "AES/GCM/NoPadding";
    public static final String ALGORITHM_AES = "AES";
    private static final int TAG_LENGTH_BIT = 128;

    /**
     * @param ciphertext     密文
     * @param key            密钥
     * @param associatedData 关联数据
     * @param nonce          随机串
     * @return 原文
     * @throws RuntimeException 异常
     */
    public static String decrypt(String ciphertext, byte[] key, byte[] associatedData, byte[] nonce)
            throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM_AES);
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, spec);
            cipher.updateAAD(associatedData);

            return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param plaintext      原文
     * @param key            密钥
     *                       科普：256 = 32 * 8，AEAD_AES_256_GCM的key长度必须是32位
     * @param associatedData 关联数据
     * @param nonce          随机串
     * @return 密文
     * @throws RuntimeException 异常
     */
    public static String encrypt(String plaintext, byte[] key, byte[] associatedData, byte[] nonce)
            throws RuntimeException {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM_AES);
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, spec);
            cipher.updateAAD(associatedData);

            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] key = UUID.randomUUID().toString().replace("-", "").getBytes(StandardCharsets.UTF_8);
        byte[] associatedData = "Associated Data".getBytes(StandardCharsets.UTF_8);
        byte[] nonce = UUID.randomUUID().toString().replace("-", "").getBytes(StandardCharsets.UTF_8);

        String originalText = "Plain text to be encrypted by AEAD-AES-256-GCM in Java";
        System.out.println("key: " + new String(key));
        System.out.println("associatedData: " + new String(associatedData));
        System.out.println("nonce: " + new String(nonce));

        System.out.println("Original Text:  " + originalText);
        String ciphertext = encrypt(originalText, key, associatedData, nonce);
        System.out.println("Encrypted Text: " + ciphertext);

        String plaintext = decrypt(ciphertext, key, associatedData, nonce);
        System.out.println("Decrypted Text: " + plaintext);
    }

}

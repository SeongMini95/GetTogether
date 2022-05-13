package com.jsm.gettogether.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESUtil {

    public static final String alg = "AES/CBC/PKCS5Padding";

    public static String encrypt(String str, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encryptStr = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encryptStr);
    }

    public String decrypt(String str, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decodeByte = Base64.getDecoder().decode(str);
        byte[] decryptStr = cipher.doFinal(decodeByte);

        return new String(decryptStr, StandardCharsets.UTF_8);
    }
}

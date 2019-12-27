package com.lc.encryption;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 第三方接口使用AES加密
 *
 * @author liucheng
 * @date 2019/9/20
 */
@Slf4j
public class ThirdAesUtils {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String IV = "AGHD4CB8BF5E4FD4";
    /**
     * 加密
     *
     * */
    public static String encrypt(String sourceData, String key)  throws Exception {
        byte[] keyByte = key.getBytes("utf-8");
        SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
        //偏移量
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(IV.getBytes("utf-8")));
        byte[] bytes = cipher.doFinal(sourceData.getBytes("utf-8"));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 解密
     *
     * */
    public static String decrypt(String encData,String key) throws Exception {
        byte[] data = Base64.getDecoder().decode(encData);
        byte[] keyByte = key.getBytes("utf-8");
        //偏移量
        SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(IV.getBytes("utf-8")));
        byte[] res = cipher.doFinal(data);
        return new String(res, "utf-8");
    }




}

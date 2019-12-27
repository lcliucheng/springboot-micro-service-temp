package com.lc.encryption;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import com.lc.exception.EncryptionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * <p> 摘要加密工具类 </p>
 *
 * @author liucheng
 * @since 2018-08-23
 */
@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DigestUtils {

    /**
     * MD5加密
     *
     * @param data 待加密数据
     * @return 加密后的数据
     */
    public static String md5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes(CharsetUtil.defaultCharsetName()));
            return HexUtil.encodeHexStr(md.digest());
        } catch (Exception e) {
            throw new EncryptionException (String.format("Failed to get the Message MD5 digest instance [%s]", data), e);
        }

    }

    /**
     * MD5加密
     * @param data 待加密数据
     * @param charset 字符集编码
     * @return 加密后的数据
     */
    public static String md5(String data, String  charset) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes(charset));
            return HexUtil.encodeHexStr(md.digest());
        } catch (Exception e) {
            throw new EncryptionException(String.format("Failed to get the Message MD5 digest instance [%s]", data), e);
        }

    }

    /**
     * MD5加密
     *
     * @param data 待加密数据
     * @return 加密后的数据
     */
    public static String md5(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            return HexUtil.encodeHexStr(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(String.format("Failed to get the Message MD5 digest instance [%s]",
                    Arrays.toString(data)), e);
        }
    }

    /**
     * SHA加密
     *
     * @param data 待加密数据
     * @return 加密后的数据
     */
    public static String sha(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(data.getBytes(CharsetUtil.defaultCharsetName()));
            return HexUtil.encodeHexStr(md.digest());
        } catch (Exception e) {
            throw new EncryptionException(String.format("Failed to get the Message SHA digest instance [%s]", data), e);
        }
    }

    /**
     * SHA加密
     *
     * @param data 待加密数据
     * @return 加密后的数据
     */
    public static String sha(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(data);
            return HexUtil.encodeHexStr(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(String.format("Failed to get the Message SHA digest instance [%s]",
                    Arrays.toString(data)), e);
        }
    }

}

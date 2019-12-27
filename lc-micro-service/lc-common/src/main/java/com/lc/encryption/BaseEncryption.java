package com.lc.encryption;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.lc.exception.EncryptionException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.util.Base64;

/**
 * <p> 加密抽象类 </p>
 *
 * @author liucheng
 * @since 2018-08-23
 */
public abstract class BaseEncryption implements Encryption {

    /**
     * 加密
     *
     * @param encryptionKey 密钥
     * @param data          待加密的数据
     * @param iv            偏移量
     * @return 加密后的数据
     */
    @Override
    public String encrypt(EncryptionKey encryptionKey, String data, String iv) {
        try {
            Cipher cipher = getCipher(encryptionKey, Cipher.ENCRYPT_MODE, iv);
            byte[] bytes = cipher.doFinal(StrUtil.bytes(data));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new EncryptionException (String.format("Encrypted exception [%s]", data), e);
        }
    }

    /**
     * 解密
     *
     * @param encryptionKey 密钥
     * @param data          待解密的数据
     * @return 解密后的数据
     */
    @Override
    public String decrypt(EncryptionKey encryptionKey, String data, String iv) {
        try {
            Cipher cipher = getCipher(encryptionKey, Cipher.DECRYPT_MODE, iv);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(data));
            return StrUtil.str(bytes, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            throw new EncryptionException(String.format("Decrypt exception [%s]", data), e);
        }
    }

    /**
     * 获取加密填充算法
     *
     * @return 填充算法
     */
    public abstract String getAlgorithm();

    /**
     * 把自定义的密钥转换成需要加解密的密钥
     *
     * @param encryptionKey 自定义密钥
     * @return 加解密密钥
     */
    public abstract Key transformKey(EncryptionKey encryptionKey);

    /**
     * 获取加解密工具类
     *
     * @param encryptionKey 密钥
     * @param iv            偏移量
     * @return Cipher
     */
    Cipher getCipher(EncryptionKey encryptionKey, int mode, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(getAlgorithm());
            Key key = transformKey(encryptionKey);
            if (StrUtil.isBlank(iv)) {
                cipher.init(mode, key);
            } else {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(CharsetUtil.defaultCharsetName()));
                cipher.init(mode, key, ivParameterSpec);
            }
            return cipher;
        } catch (Exception e) {
            throw new EncryptionException(String.format("Invalid key fail mode[%d]", mode), e);
        }
    }

}

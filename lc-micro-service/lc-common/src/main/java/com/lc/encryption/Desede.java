package com.lc.encryption;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.lc.exception.EncryptionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Objects;

/**
 * <p> Desede加密工具类，简称3DES加密，单例且线程安全</p>
 *
 * @author liucheng
 * @since 2018-08-23
 */
@SuppressWarnings({"unused", "Duplicates"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Desede extends BaseEncryption {

    /**
     * Key生成算法
     */
    private static final String KEY_ALGORITHM = "DESEDE";

    /**
     * 加密填充算法
     */
    private static final String ALGORITHM = "DESEDE/ECB/PKCS5Padding";

    public static Desede getInstance() {
        return DesedeEncryptionHolder.instance;
    }

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }

    @Override
    public String encrypt(EncryptionKey encryptionKey, String data, String iv) {
        try {
            Cipher cipher = getCipher(encryptionKey, Cipher.ENCRYPT_MODE, iv);
            byte[] bytes = cipher.doFinal(StrUtil.bytes(data));
            return Hex.encodeHexString(bytes);
        } catch (IllegalBlockSizeException e) {
            throw new EncryptionException (String.format("Encrypted illegal block size exception [%s]", data), e);
        } catch (BadPaddingException e) {
            throw new EncryptionException(String.format("Encrypted bad padding exception [%s]", data), e);
        }
    }

    @Override
    public String decrypt(EncryptionKey encryptionKey, String data, String iv) {
        try {
            Cipher cipher = getCipher(encryptionKey, Cipher.DECRYPT_MODE, iv);
            byte[] bytes = cipher.doFinal(Hex.decodeHex(data.toCharArray()));
            return StrUtil.str(bytes, CharsetUtil.CHARSET_UTF_8);
        } catch (IllegalBlockSizeException e) {
            throw new EncryptionException(String.format("Encrypted illegal block size exception [%s]", data), e);
        } catch (BadPaddingException e) {
            throw new EncryptionException(String.format("Encrypted bad padding exception [%s]", data), e);
        } catch (DecoderException e) {
            throw new EncryptionException(String.format("Encrypted decoder exception [%s]", data), e);
        }
    }

    @Override
    public Key transformKey(EncryptionKey encryptionKey) {
        if (!Objects.equals(EncryptionKeyType.DEFAULT, encryptionKey.getEncryptionKeyType())) {
            throw new IllegalArgumentException("Encryption key mismatch");
        }

        byte[] encryptionKeys = StrUtil.bytes(encryptionKey.getKey());
        int keySize = 24;
        byte[] keys = new byte[keySize];
        if (encryptionKeys.length < keySize) {
            System.arraycopy(encryptionKeys, 0, keys, 0, encryptionKeys.length);
        } else {
            System.arraycopy(encryptionKeys, 0, keys, 0, keySize);
        }

        //获得原始对称密钥的字节数组并根据字节数组生成密钥
        return new SecretKeySpec(keys, KEY_ALGORITHM);
    }

    private static class DesedeEncryptionHolder {
        private static Desede instance = new Desede();
    }

}

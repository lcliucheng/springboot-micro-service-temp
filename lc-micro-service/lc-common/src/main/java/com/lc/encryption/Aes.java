package com.lc.encryption;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Objects;

/**
 * <p> Aes加密工具类，单例且线程安全</p>
 *
 * @author liucheng
 * @since 2018-08-23
 */
@SuppressWarnings({"unused", "Duplicates"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Aes extends BaseEncryption {

    private static final byte[] KEY =
            new byte[]{0x3a, 0x2a, 0x1f, 0xf, 0x3f, 0x7a, 0x41, 0x15, 0x3f, 0x2b, 0x1f, 0x72, 0x4a, 0x10, 0x9, 0x1a};

    private static final String IV =
            new String (new byte[]{0x3a, 0x2a, 0x1f, 0xf, 0x3f, 0x7a, 0x41, 0x15, 0x3f, 0x2b, 0x1f, 0x72, 0x4a, 0x10, 0x9, 0x1a});

    private static final Encryption.EncryptionKey ENCRYPTION_KEY =
            Encryption.EncryptionKey.builder ().key (new String (KEY)).build ();

    /**
     * Key生成算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密填充算法
     */
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public static Aes getInstance() {
        return AesEncryptionHolder.instance;
    }

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }


    /**
     * 采用默认密钥加密
     *
     * @param data 待加密数据
     * @return String
     */
    public String encrypt(String data) {
        return super.encrypt (ENCRYPTION_KEY, data, IV);
    }

    /**
     * 采用默认密钥解密
     *
     * @param data 待解密数据
     * @return String
     */
    public String decrypt(String data) {
        return super.decrypt (ENCRYPTION_KEY, data, IV);
    }

    @Override
    public Key transformKey(EncryptionKey encryptionKey) {
        if (!Objects.equals (EncryptionKeyType.DEFAULT, encryptionKey.getEncryptionKeyType ())) {
            throw new IllegalArgumentException ("Encryption key mismatch");
        }

        byte[] encryptionKeys = StrUtil.bytes (encryptionKey.getKey ());
        int[] keySizes = new int[]{16, 24, 32};
        int keySize = keySizes[0];
        if (encryptionKeys.length > keySizes[0] && encryptionKeys.length <= keySizes[1]) {
            keySize = keySizes[1];
        } else if (encryptionKeys.length > keySizes[1]) {
            keySize = keySizes[2];
        }

        byte[] keys = new byte[keySize];
        if (encryptionKeys.length < keySize) {
            System.arraycopy (encryptionKeys, 0, keys, 0, encryptionKeys.length);
        } else {
            System.arraycopy (encryptionKeys, 0, keys, 0, keySize);
        }

        //获得原始对称密钥的字节数组并根据字节数组生成密钥
        return new SecretKeySpec (keys, KEY_ALGORITHM);
    }

    private static class AesEncryptionHolder {
        private static Aes instance = new Aes ();
    }





}

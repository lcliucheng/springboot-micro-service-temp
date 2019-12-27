package com.lc.encryption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p> 加解密接口 </p>
 *
 * @author liucheng
 * @since 2019-01-22
 */
public interface Encryption {

    /**
     * 加密
     *
     * @param encryptionKey EncryptionKey
     * @param data          待加密数据
     * @param iv            偏移量
     * @return String
     */
    String encrypt(EncryptionKey encryptionKey, String data, String iv);

    /**
     * 解密
     *
     * @param encryptionKey EncryptionKey
     * @param data          待解密数据
     * @param iv            偏移量
     * @return String
     */
    String decrypt(EncryptionKey encryptionKey, String data, String iv);


    /**
     * <p> 密钥类型 </p>
     *
     * @author liucheng
     * @since 2018-08-23
     */
    enum EncryptionKeyType {

        /**
         * 默认
         */
        DEFAULT,
        /**
         * 公钥
         */
        PUBLIC,
        /**
         * 私钥
         */
        PRIVATE

    }

    /**
     * <p> 密钥，密钥采用Base64编号 </p>
     *
     * @author liucheng
     * @since 2018-08-23
     */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class EncryptionKey {

        /**
         * 加密类型
         */
        @Builder.Default
        private EncryptionKeyType encryptionKeyType = EncryptionKeyType.DEFAULT;

        /**
         * 密钥
         */
        private String key;

    }

}

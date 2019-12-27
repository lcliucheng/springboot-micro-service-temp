package com.lc.encryption;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.lc.exception.EncryptionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

/**
 * <p> Rsa加密工具类，单例且线程安全</p>
 *
 * @author liucheng
 * @since 2018-08-23
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Rsa extends BaseEncryption {

    /**
     * Key生成算法
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 加密填充算法
     */
    private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * 默认密钥长度
     */
    private static final int KEY_SIZE = 1024;

    public static Rsa getInstance() {
        return RsaEncryptionHolder.instance;
    }

    /**
     * 加解密
     *
     * @param data      待加解密的数据
     * @param maxLength 最大加解密长度
     * @param cipher    加密类
     * @throws EncryptionException 加解密异常
     */
    private static byte[] doFinal(byte[] data, int maxLength, Cipher cipher) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            int offSet = 0;
            while (data.length - offSet > 0) {
                if (data.length - offSet > maxLength) {
                    byteArrayOutputStream.write(cipher.doFinal(data, offSet, maxLength));
                } else {
                    byteArrayOutputStream.write(cipher.doFinal(data, offSet, data.length - offSet));
                }
                offSet = offSet + maxLength;
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IllegalBlockSizeException e) {
            throw new EncryptionException("Do final data size exception ", e);
        } catch (BadPaddingException e) {
            throw new EncryptionException("Do final bad padding exception", e);
        } catch (IOException e) {
            throw new EncryptionException("Do final fill exception", e);
        }
    }

    /**
     * 生成密钥对
     *
     * @return List<EncryptionKey>
     * @throws EncryptionException 加密异常
     */
    public static Map<EncryptionKeyType, String> generateKey() throws EncryptionException {
        Map<EncryptionKeyType, String> keys = Maps.newHashMap();
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException ("No such algorithm exception", e);
        }

        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Key publicKey = keyPair.getPublic();

        String regex = "(.{64})";
        String pubKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        keys.put(EncryptionKeyType.PUBLIC, pubKeyStr.replaceAll(regex, "$1\n"));
        Key privateKey = keyPair.getPrivate();
        String priKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        keys.put(EncryptionKeyType.PRIVATE, priKeyStr.replaceAll(regex, "$1\n"));
        return keys;
    }

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }

    @Override
    public String encrypt(EncryptionKey encryptionKey, String data, String iv) {
        Cipher cipher = super.getCipher(encryptionKey, Cipher.ENCRYPT_MODE, iv);
        return Base64.getEncoder().encodeToString(doFinal(
                data.getBytes(CharsetUtil.CHARSET_UTF_8), KEY_SIZE / 8 - 11, cipher));
    }

    public String encrypt(EncryptionKey encryptionKey, String data) {
        return encrypt(encryptionKey, data, null);
    }

    @Override
    public String decrypt(EncryptionKey encryptionKey, String data, String iv) {
        Cipher cipher = super.getCipher(encryptionKey, Cipher.DECRYPT_MODE, iv);
        return StrUtil.str(doFinal(Base64.getDecoder().decode(data), KEY_SIZE / 8, cipher)
                , CharsetUtil.CHARSET_UTF_8);
    }

    public String decrypt(EncryptionKey encryptionKey, String data) {
        return decrypt(encryptionKey, data, null);
    }

    /**
     * 签名
     *
     * @param encryptionKey 密钥
     * @param data          待签名数据
     * @return 签名
     */
    public String sign(EncryptionKey encryptionKey, String data) {
        if (!Objects.equals(EncryptionKeyType.PRIVATE, encryptionKey.getEncryptionKeyType())) {
            throw new IllegalArgumentException("Encryption key mismatch");
        }

        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign((PrivateKey) transformKey(encryptionKey));
            signature.update(data.getBytes(CharsetUtil.CHARSET_UTF_8));
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(String.format("No such algorithm exception data[%s]", data), e);
        } catch (InvalidKeyException e) {
            throw new EncryptionException("Invalid key spec exception", e);
        } catch (SignatureException e) {
            throw new EncryptionException(String.format("Signature exception data[%s]", data), e);
        }
    }

    /**
     * 验签
     *
     * @param encryptionKey 密钥
     * @param sign          签名
     * @param data          签名数据
     * @return boolean
     */
    public boolean verify(EncryptionKey encryptionKey, String sign, String data) {
        if (!Objects.equals(EncryptionKeyType.PUBLIC, encryptionKey.getEncryptionKeyType())) {
            throw new EncryptionException("Encryption key type error");
        }

        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify((PublicKey) transformKey(encryptionKey));
            signature.update(data.getBytes(CharsetUtil.CHARSET_UTF_8));
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException(String.format("No such algorithm exception sign[%s],data[%s]", sign, data), e);
        } catch (InvalidKeyException e) {
            throw new EncryptionException("Invalid key spec exception", e);
        } catch (SignatureException e) {
            throw new EncryptionException(String.format("Signature exception sign[%s],data[%s]", sign, data), e);
        }
    }

    /**
     * 把自定义的密钥转换成需要加解密的密钥，AES密钥只能为128、192、256位，对应字节数为16、24、32
     *
     * @param encryptionKey 自定义密钥
     * @return 加解密密钥
     */
    @Override
    public Key transformKey(EncryptionKey encryptionKey) {
        switch (encryptionKey.getEncryptionKeyType()) {
            case PUBLIC: {
                // 对密钥base64编码
                byte[] keyBytes = Base64.getMimeDecoder().decode(encryptionKey.getKey());
                // 取得公钥
                X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
                try {
                    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
                    return keyFactory.generatePublic(x509KeySpec);
                } catch (NoSuchAlgorithmException e) {
                    throw new EncryptionException("No such algorithm exception", e);
                } catch (InvalidKeySpecException e) {
                    throw new EncryptionException("Invalid key spec exception", e);
                }
            }
            case PRIVATE: {
                // 对密钥base64编码
                byte[] keyBytes = Base64.getMimeDecoder().decode(encryptionKey.getKey());
                // 取得私钥
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
                try {
                    KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
                    return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
                } catch (NoSuchAlgorithmException e) {
                    throw new EncryptionException("No such algorithm exception", e);
                } catch (InvalidKeySpecException e) {
                    throw new EncryptionException("Invalid key spec exception", e);
                }
            }
            default:
                throw new EncryptionException("Encryption key not match");
        }
    }

    private static class RsaEncryptionHolder {
        private static Rsa instance = new Rsa();
    }


}

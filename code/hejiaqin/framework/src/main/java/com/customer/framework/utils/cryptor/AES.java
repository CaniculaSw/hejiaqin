package com.customer.framework.utils.cryptor;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.customer.framework.utils.LogUtil;

/**
 * AES加解密
 * 对称加密算法（秘钥一样）
 * @author Yangjianliang datetime 2009-8-16
 */
public final class AES {

    private static final String TAG = "AES";

    /**
     * 编码
     */
    static final String ENCODE_TYPE = "UTF8";

    /**
     * DES升级版
     */
    static final String KEY_TYPE_AES = "AES";//"算法/模式/补码方式"
    static final String CIPHER_TYPE_AES = "AES/ECB/PKCS5Padding";//"算法/模式/补码方式"

    /**
     * 加密
     *
     * @param content  需要加密的内容
     * @param password 加密密码
     * @return
     */
    public static String encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_TYPE_AES);
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_TYPE_AES);
            Cipher cipher = Cipher.getInstance(CIPHER_TYPE_AES);// 创建密码器
            byte[] byteContent = content.getBytes(ENCODE_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);

            String resultString = parseByte2HexStr(result);
            return resultString; // 加密
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e(TAG, e);
        } catch (NoSuchPaddingException e) {
            LogUtil.e(TAG, e);
        } catch (InvalidKeyException e) {
            LogUtil.e(TAG, e);
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, e);
        } catch (IllegalBlockSizeException e) {
            LogUtil.e(TAG, e);
        } catch (BadPaddingException e) {
            LogUtil.e(TAG, e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String decrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_TYPE_AES);
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_TYPE_AES);
            Cipher cipher = Cipher.getInstance(CIPHER_TYPE_AES);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] cont = parseHexStr2Byte(content);
            byte[] result = cipher.doFinal(cont);

            String resultString = new String(result);
            return resultString;
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e(TAG, e);
        } catch (NoSuchPaddingException e) {
            LogUtil.e(TAG, e);
        } catch (InvalidKeyException e) {
            LogUtil.e(TAG, e);
        } catch (IllegalBlockSizeException e) {
            LogUtil.e(TAG, e);
        } catch (BadPaddingException e) {
            LogUtil.e(TAG, e);
        }
        return null;
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        String content = "test";
        String password = "12345678";
        //加密
        System.out.println("加密前：" + content);
        String encryptResult = encrypt(content, password);
        System.out.println(encryptResult);

        //解密
        String decryptResult = decrypt(encryptResult, password);
        System.out.println("解密后：" + decryptResult);
    }

}

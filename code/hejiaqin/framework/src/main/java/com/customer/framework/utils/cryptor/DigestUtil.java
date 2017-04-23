package com.customer.framework.utils.cryptor;

import android.text.TextUtils;

import com.customer.framework.utils.LogUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 消息摘要工具类
 * 
 * @author Yangjianliang datetime 2009-8-16
 */
public class DigestUtil {

	private static final String TAG = "AES";

	protected static final String KEY_SHA = "SHA-1";
	protected static final String KEY_MD5 = "MD5";
	private static MessageDigest shaMessageDigest = null;
	private static MessageDigest md5MessageDigest = null;

	/**
	 * 编码
	 */
	public static final String ENCODE_TYPE = "UTF8";

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return 摘要
	 * @throws Exception
	 */
	public static String encryptMD5(String data){
		if (TextUtils.isEmpty(data)) {
			return data;
		}
		byte[] res = null;
		try {
			if(md5MessageDigest==null){
				md5MessageDigest = MessageDigest.getInstance(KEY_MD5);
			}
			md5MessageDigest.reset();
			md5MessageDigest.update(data.getBytes(ENCODE_TYPE));
			res = md5MessageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			LogUtil.e(TAG, e);
            throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(TAG, e);
            throw new RuntimeException(e);
		}

		return asHex(res);
	}

	/**
	 * SHA摘要
	 * 
	 * @param data
	 *            要取摘要的字符串
	 * @return 摘要
	 * @throws Exception
	 */
	public static String encryptSHA(String data) {
		if (TextUtils.isEmpty(data)) {
			return data;
		}
		byte[] res = null;
		try {
			if(shaMessageDigest==null){
				shaMessageDigest = MessageDigest.getInstance(KEY_SHA);
			}
			shaMessageDigest.reset();
			shaMessageDigest.update(data.getBytes(ENCODE_TYPE));
			res = shaMessageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			LogUtil.e(TAG, e);
            throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(TAG, e);
            throw new RuntimeException(e);
		}

		return asHex(res);

	}

	/**
	 * 将字节数组转换成16进制的字符串 二行制转字符串 Turns array of bytes into string
	 * 
	 * @param bts
	 *            Array of bytes to convert to hex string
	 * @return 16进制的字符串 Generated hex string
	 */
	public static final String asHex(byte[] bts) {
		if (bts == null) {
			return null;
		}
		StringBuffer strbuf = new StringBuffer(bts.length * 2);
		int i;

		for (i = 0; i < bts.length; i++) {
			if (((int) bts[i] & 0xff) < 0x10) {
				strbuf.append("0");
			}

			strbuf.append(Long.toString((int) bts[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

	/**
	 * 将16进制的字符串转换成字节数组
	 * 
	 * @param src
	 * @return bytes array
	 */
	public static final byte[] asBin(String src) {
		if (src == null || src.length() < 1) {
            return null;
        }
		byte[] encrypted = new byte[src.length() / 2];
		for (int i = 0; i < src.length() / 2; i++) {
			int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);

			encrypted[i] = (byte) (high * 16 + low);
		}
		return encrypted;
	}

    /**
     * HMAC-MD5加密
     *
     * @param keyString  密钥
     * @param s 字符串
     * @return 加密后的字符串
     */
    public static String getHmacMd5Str(String s, String keyString){
        try{
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);

            byte[] bytes = mac.doFinal(s.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();

            for (int i=0; i<bytes.length; i++) {
                String hex = Integer.toHexString(0xFF &  bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            return hash.toString() ;
        } catch (UnsupportedEncodingException e) {
			LogUtil.e(TAG, e);
            throw new RuntimeException(e);
        } catch(InvalidKeyException e){
			LogUtil.e(TAG, e);
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
			LogUtil.e(TAG, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验是否匹配
     *
     * @param hmacMD5Key    HMAC-MD5加密后的字符串
     * @param key           密钥
     * @param livebosMD5Key livebosMD5加密后的字符串
     * @return 是否匹配
     */
    public static boolean isMatchByLivebosMD5(String hmacMD5Key, String key, String livebosMD5Key) {
        BigInteger bbb = new BigInteger(livebosMD5Key.toLowerCase(), 16);
        byte[] bbbc = bbb.toByteArray();
        if (getHmacMd5Str(asHex(bbbc), key).equals(hmacMD5Key)) {
            return true;
        }
        for (int i = 0; i < bbbc.length; i++) {
            if (i == bbbc.length - 1) {
                bbbc[i] = (byte) (0 - bbbc[i]);
            } else {
                bbbc[i] = (byte) (0 - bbbc[i] - 1);
            }
        }
        if (getHmacMd5Str(asHex(bbbc), key).equals(hmacMD5Key)) {
            return true;
        }
        if (livebosMD5Key.startsWith("0")) {
            return isMatchByLivebosMD5(key, hmacMD5Key, livebosMD5Key.substring(1));
        }
        return false;
    }

//	public static void main(String[] args) {
//		String msgf = "http://123.com:90/测&=试的阿迪王啊123190-jidannf=123";
//		encryptSHA(msgf);
//	}
}

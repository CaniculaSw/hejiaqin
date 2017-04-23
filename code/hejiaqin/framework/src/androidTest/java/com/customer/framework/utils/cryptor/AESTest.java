package com.customer.framework.utils.cryptor;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class AESTest extends TestCase {

    public void testEncrypt() throws Exception {
        String content = "abcdefghijklmn";
        String key = "12345678";
        String encryptText = AES.encrypt(content, key);
        assertNotNull(encryptText);
    }

}
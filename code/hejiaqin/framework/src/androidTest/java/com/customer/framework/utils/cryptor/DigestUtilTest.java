package com.customer.framework.utils.cryptor;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class DigestUtilTest extends TestCase {

    public void testEncryptMD5() throws Exception {
        String content1 = "aaaa bbbb";
        String content2 = "aaaa bbbb";
        String content3 = "aaaa cccc";

        String encrypt1 = DigestUtil.encryptMD5(content1);
        assertNotNull(encrypt1);

        String encrypt2 = DigestUtil.encryptMD5(content2);
        assertNotNull(encrypt2);

        String encrypt3 = DigestUtil.encryptMD5(content3);
        assertNotNull(encrypt3);

        assertEquals(encrypt1, encrypt2);
        assertNotSame(encrypt1, encrypt3);
    }
}

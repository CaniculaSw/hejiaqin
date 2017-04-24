package com.customer.framework.utils.cryptor;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class Base64Test extends TestCase {
    public void testEncode() throws Exception {
        String content = "abcdefg";
        String encodeStr = Base64.encode(content.getBytes());
        assertNotNull(encodeStr);

        byte[] decodeByte = Base64.decode(encodeStr);
        assertNotNull(decodeByte);

        assertEquals(content, new String(decodeByte));
    }
}

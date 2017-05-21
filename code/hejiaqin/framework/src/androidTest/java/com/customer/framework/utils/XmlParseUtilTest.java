package com.customer.framework.utils;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/21 0021.
 */
public class XmlParseUtilTest extends TestCase {

    public void testGetElemString() throws Exception {
        assertNotNull(XmlParseUtil.getElemString("<abc>sss</abc>", "abc"));
    }

    public void testGetElemStringIncludeParam() throws Exception {
        assertNotNull(XmlParseUtil.getElemStringIncludeParam("<abc>123</abc>", "abc"));
    }

    public void testGetXmlId() throws Exception {
        assertNotNull(XmlParseUtil.getXmlId("<abc>123</abc>", "abc"));
    }
}
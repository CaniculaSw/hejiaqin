package com.chinamobile.hejiaqin.business.model;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/31 0031.
 */
public class RequestResultTest extends TestCase {

    public void testGetResult() throws Exception {
        Object result = new Object();
        PageableInfo pageableInfo = new PageableInfo();
        RequestResult requestResult = new RequestResult(result, pageableInfo);
        assertNotNull(requestResult.getResult());
        assertEquals(result, requestResult.getResult());
    }

    public void testSetResult() throws Exception {
        Object result1 = new Object();
        PageableInfo pageableInfo = new PageableInfo();
        RequestResult requestResult = new RequestResult(result1, pageableInfo);
        assertNotNull(requestResult.getResult());
        assertEquals(result1, requestResult.getResult());

        Object result2 = new Object();
        requestResult.setResult(result2);
        assertEquals(result2, requestResult.getResult());
    }

    public void testGetPageableInfo() throws Exception {
        Object result = new Object();
        PageableInfo pageableInfo = new PageableInfo();
        RequestResult requestResult = new RequestResult(result, pageableInfo);
        assertNotNull(requestResult.getPageableInfo());
        assertEquals(pageableInfo, requestResult.getPageableInfo());
    }

    public void testSetPageableInfo() throws Exception {
        Object result = new Object();
        PageableInfo pageableInfo1 = new PageableInfo();
        RequestResult requestResult = new RequestResult(result, pageableInfo1);
        assertNotNull(requestResult.getPageableInfo());
        assertEquals(pageableInfo1, requestResult.getPageableInfo());

        PageableInfo pageableInfo2 = new PageableInfo();

        requestResult.setPageableInfo(pageableInfo2);
        assertEquals(pageableInfo2, requestResult.getPageableInfo());
    }
}
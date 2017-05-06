package com.chinamobile.hejiaqin.business.utils;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class CaaSUtilTest extends TestCase {

    public void testSetMainBody() throws Exception {
        String cmdType = "cmdType";
        String seq = "seq";
        String opCode = "opCode";
        StringBuilder mainBody = CaaSUtil.setMainBody(cmdType, seq, opCode);
        assertNotNull(mainBody);
        assertTrue(mainBody.toString().contains(cmdType));
        assertTrue(mainBody.toString().contains(seq));
        assertTrue(mainBody.toString().contains(opCode));

    }

    public void testBuildMessage() throws Exception {
        String cmdType = "cmdType";
        String seq = "seq";
        String opCode = "opCode";
        String phoneNum = "phoneNum";
        String mainBody = CaaSUtil.buildMessage(cmdType, seq, opCode, phoneNum, null);
        assertNotNull(mainBody);
        assertTrue(mainBody.contains(cmdType));
        assertTrue(mainBody.contains(seq));
        assertTrue(mainBody.contains(opCode));
        assertTrue(mainBody.contains(phoneNum));
    }
}

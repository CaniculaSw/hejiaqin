package com.chinamobile.hejiaqin.business.utils;

import com.customer.framework.utils.LogUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by eshaohu on 16/10/25.
 */
public class CaaSUtil {
    private static final String TAG = "CaaSUtil";


    public interface CmdType {
        int BIND = 0;
        int SEND_CONTACT = 1;
        int TV_HELPER = 3;
    }

    public interface OpCode {
        int BIND = 0; //Phone -> TV
        int DEBIND = 1;//Phone -> TV
        int BIND_SUCCESS = 100; //TV->Phone
        int BIND_DENIED = 101;//TV->Phone
        int DEBIND_SUCCESS = 102;//TV->Phone
        int DEBIND_FAILED = 103;//TV->Phone
        int BIND_UNAVALIABLE = 104;//TV->Phone
    }

    public static StringBuilder setMainBody(String cmdType, String seq, String opCode) {
        StringBuilder content = new StringBuilder();

        content.append("<?xml version='1.0' encoding='UTF-8'?>");
        content.append("<ControlMsg Version='1.0'>");
        content.append("<MsgHead>");
        content.append("<CmdType>");
        content.append(cmdType);
        content.append("</CmdType>");
        content.append("<Seq>");
        content.append(seq);
        content.append("</Seq>");
        content.append("</MsgHead>");
        content.append("<MsgBody>");
        content.append("<OpCode>");
        content.append(opCode);
        content.append("</OpCode>");

        return content;
    }

    public static String buildMessage(String cmdType, String seq, String opCode, Map<String, String> params) {
        StringBuilder content = setMainBody(cmdType, seq, opCode);

        if (params != null && !params.isEmpty()) {
            Set<Map.Entry<String, String>> allSet = params.entrySet();
            Iterator<Map.Entry<String, String>> iter = allSet.iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> me = iter.next();
                LogUtil.d(TAG, "Add param into message: " + me.getKey() + ":" + me.getValue());
                content.append("<" + me.getKey() + ">");
                content.append(me.getValue());
                content.append("</" + me.getKey() + ">");
            }
        }
        content.append("</MsgBody>");
        content.append("</ControlMsg>");
        return content.toString();
    }
}

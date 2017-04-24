package com.customer.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***/

public final class XmlParseUtil {

    //TODO 4.重新实现解析xml方法

    public static String getElemString(String toParse, String elemName) {
        String result = "";
        String elemBeg = "<" + elemName + ">";
        String elemEnd = "</" + elemName + ">";
        int iElemBeg = toParse.indexOf(elemBeg);

        if (iElemBeg != -1) {
            iElemBeg += elemBeg.length();
            int iElemEnd = toParse.indexOf(elemEnd);
            if (iElemEnd != -1) {
                result = toParse.substring(iElemBeg, iElemEnd);
                if (result.startsWith("\r\n")) {
                    result = toParse.substring(iElemBeg + 2, iElemEnd);
                }
            }
        }

        return result;
    }

    public static String getElemStringIncludeParam(String toParse, String elemName) {
        String result = "";
        String compileStr = "<\\s*" + elemName + ".*>(.*)</\\s*" + elemName + "\\s*>";
        Pattern pattern = Pattern.compile(compileStr);
        Matcher matcher = pattern.matcher(toParse);
        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    public static int getXmlId(String param, String subString) {
        int id = -1;
        if (param == null || "".equals(param) || subString == null || "".equals(subString)) {
            return id;
        }
        String startString = "<" + subString + ">";
        String endString = "</" + subString + ">";
        int startIndex = param.indexOf(startString) + startString.length();
        int endIndex = param.indexOf(endString);
        if (startIndex > 0 && endIndex > startIndex) {
            String subParam = param.substring(startIndex, endIndex);
            id = Integer.parseInt(subParam);
        }
        return id;
    }

}

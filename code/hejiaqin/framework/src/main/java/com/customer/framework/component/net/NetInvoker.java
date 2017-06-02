package com.customer.framework.component.net;

/**
 * Created by Administrator on 2017/6/2 0002.
 */
public class NetInvoker {
    private boolean isUnitTest;
    private int result;
    private Object resultObj;


    public static NetInvoker buildInvoker1() {
        NetInvoker netInvoker = new NetInvoker();
        netInvoker.isUnitTest = true;
        netInvoker.result = RESULT_SUCCESS;
        return netInvoker;
    }

    public static NetInvoker buildInvoker2() {
        NetInvoker netInvoker = new NetInvoker();
        netInvoker.isUnitTest = true;
        netInvoker.result = RESULT_FAILED;
        return netInvoker;
    }

    public static NetInvoker buildInvoker3() {
        NetInvoker netInvoker = new NetInvoker();
        netInvoker.isUnitTest = true;
        netInvoker.result = RESULT_NETWORKERROR;
        return netInvoker;
    }

    public boolean isUnitTest() {
        return isUnitTest;
    }

    public void setIsUnitTest(boolean isUnitTest) {
        this.isUnitTest = isUnitTest;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public NetInvoker setResultObj(Object resultObj) {
        this.resultObj = resultObj;
        return this;
    }

    public static final int RESULT_SUCCESS = 1000;
    public static final int RESULT_FAILED = 1001;
    public static final int RESULT_NETWORKERROR = 1002;
}

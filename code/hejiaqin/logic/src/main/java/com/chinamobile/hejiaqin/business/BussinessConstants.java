package com.chinamobile.hejiaqin.business;

/**
 * 业务常量值
 * Kangxi Version 001
 * author: zhanggj
 * Created: 2016/4/8.
 */
public class BussinessConstants {

    /**
     * 公共信息
     */
    public interface CommonInfo {

        String MOBILE_TYPE_ANDROID = "Android";

        String SPLIT = ",";

        int DEFAULT_PAGE_SIZE = 10;

        String APP_UNIQUE_ID = "appUniqueId";

        String INTENT_EXTRA_PERMISSIONS = "intent_extra_permissions";

        int PERMISSIONS_GRANTED = 0; // 权限授权
        int PERMISSIONS_DENIED = 1; // 权限拒绝

    }

    public interface ActivityRequestCode {
        int BASE_QEUEST_CODE = 1000;
        //权限申请的RequestCode
        int PERMISSIONS_REQUEST_CODE = BASE_QEUEST_CODE + 1;
    }

    /**
     * 服务器的地址
     */
    public interface ServerInfo {

        String HTTP_ADDRESS = "http://service.XXX.com";

        String HTTPS_ADDRESS = "https://service.XXX.com";
    }

    /**
     * 公用字典值表(必须和服务器保持一致)
     */
    public interface DictInfo {

        //男
        String SEX_MALE = "1";

        //女
        String SEX_FEMALE = "0";

    }

    /**
     * 公共信息
     */
    public interface HttpHeaderInfo {

        String HEADER_IMEI = "IMEI";

        String HEADER_MOBILE_TYPE = "mobileType";

        String HEADER_MOBILE_NAME = "mobileName";

        String HEADER_MOBILE_VERSION = "mobileVersion";

        String HEADER_TOKENID = "TOKENID";

        String HEADER_UNIQ = "UNIQ";

    }

    /**
     * 服务器返回的ResultCode
     */
    public interface HttpCommonCode {
        //通用成功的ResultCode
        String COMMON_SUCCESS_CODE = "1";
    }

    /**
     * 服务器返回的ResultCode
     */
    public interface CommonMsgId {

        int BASE_MSG_ID = 10001000;

        //网络错误消息ID
        int NETWORK_ERROR_MSG_ID = BASE_MSG_ID + 1;

    }

    /**
     * 登录模块常量
     */
    public interface Login {

        String REGISTER_INFO_KEY = "registerInfo";

        String LOGIN_HISTORY_LIST_KEY = "loginHistoryList";

        int LOGIN_HISTORY_LIST_MAX = 10;

        String USER_INFO_KEY = "userInfo";

        String TOKEN_DATE = "token_data";

        String INTENT_REGISTER_FIRST_INFO = "registerFirstInfo";

        String INTENT_REGISTER_SECOND_INFO = "registerSecondInfo";

        String INTENT_LOGIN_ID = "registerLoginId";

    }

    /**
     * 登录模块网络接口错误码
     */
    public interface LoginHttpErrorCode {

        //注册时已经注册过的错误码
        String HAS_REGISTER = "3001";

        //注册时已经注册过,但账户已经被禁用
        String HAS_REGISTER_FORBIDDEN = "3002";

        //验证码已经失效
        String VERIFY_CODE_DISABLE = "3003";
    }

    /**
     * 登录模块消息ID
     */
    public interface LoginMsgID {

        int BASE_MSG_ID = 20001000;
        //获取验证码成功的消息ID
        int GET_VERIFY_CDOE_SUCCESS_MSG_ID = BASE_MSG_ID + 1;
        //获取验证码失败的消息ID
        int GET_VERIFY_CDOE_FAIL_MSG_ID = BASE_MSG_ID + 2;
        //获取验证码网络失败消息ID
        int GET_VERIFY_CDOE_NET_ERROR_MSG_ID = BASE_MSG_ID + 3;
        //检查验证码成功的消息ID
        int CHECK_VERIFY_CDOE_SUCCESS_MSG_ID = BASE_MSG_ID + 4;
        //检查验证码失败的消息ID
        int CHECK_VERIFY_CDOE_FAIL_MSG_ID = BASE_MSG_ID + 5;
        //注册第一步成功的消息ID
        int REGISTER_FIRST_STEP_SUCCESS_MSG_ID = BASE_MSG_ID + 6;
        //注册第一步失败的消息ID
        int REGISTER_FIRST_STEP_FAIL_MSG_ID = BASE_MSG_ID + 7;
        //注册第二步成功的消息ID
        int REGISTER_SECOND_STEP_SUCCESS_MSG_ID = BASE_MSG_ID + 8;
        //注册第二步失败的消息ID
        int REGISTER_SECOND_STEP_FAIL_MSG_ID = BASE_MSG_ID + 9;
        //登陆成功的消息ID
        int LOGIN_SUCCESS_MSG_ID = BASE_MSG_ID + 10;
        //登陆失败的消息ID
        int LOGIN_FAIL_MSG_ID = BASE_MSG_ID + 11;

        //修改密码成功的消息ID
        int UPDATE_PWD_SUCCESS_MSG_ID = BASE_MSG_ID + 12;
        //修改密码失败的消息ID
        int UPDATE_PWD_FAIL_MSG_ID = BASE_MSG_ID + 13;

        //注销成功的消息ID
        int LOGOUT_SUCCESS_MSG_ID = BASE_MSG_ID + 14;
        //注销失败的消息ID
        int LOGOUT_FAIL_MSG_ID = BASE_MSG_ID + 15;

        //重置密码获取验证码成功的消息ID
        int RESET_GET_VERIFY_CDOE_SUCCESS_MSG_ID = BASE_MSG_ID + 16;
        //重置密码获取验证码失败的消息ID
        int RESET_GET_VERIFY_CDOE_FAIL_MSG_ID = BASE_MSG_ID + 17;
        //重置密码获取验证码网络失败消息ID
        int RESET_GET_VERIFY_CDOE_NET_ERROR_MSG_ID = BASE_MSG_ID + 18;
        //重置密码成功的消息ID
        int RESET_PWD_SUCCESS_MSG_ID = BASE_MSG_ID + 19;
        //重置密码失败的消息ID
        int RESET_PWD_FAIL_MSG_ID = BASE_MSG_ID + 20;

    }

    /**
     * 联系人模块常量
     */
    public interface Contact {
        // 联系人详情key值
        String INTENT_CONTACTSINFO_KEY = "INTENT_CONTACTSINFO_KEY";

    }

    /**
     * 联系人模块网络接口错误码
     */
    public interface ContactHttpErrorCode {


    }

    /**
     * 联系人模块消息ID
     */
    public interface ContactMsgID {

        int BASE_MSG_ID = 30001000;

        // 获取本地联系人成功的消息ID
        int GET_LOCAL_CONTACTS_SUCCESS_MSG_ID = BASE_MSG_ID + 1;
        // 搜索本地联系人成功的消息ID
        int SEARCH_LOCAL_CONTACTS_SUCCESS_MSG_ID = BASE_MSG_ID + 2;

        // 获取应用联系人成功的消息ID
        int GET_APP_CONTACTS_SUCCESS_MSG_ID = BASE_MSG_ID + 3;
        // 搜索应用联系人成功的消息ID
        int SEARCH_APP_CONTACTS_SUCCESS_MSG_ID = BASE_MSG_ID + 4;

        // handleFragmentMessage
        // 隐藏联系人列表的title
        int UI_HIDE_CCONTACT_LIST_TITLE_ID = BASE_MSG_ID + 100;
        // 显示联系人列表的title
        int UI_SHOW_CCONTACT_LIST_TITLE_ID = BASE_MSG_ID + 101;
    }

    /**
     * 拨号模块常量
     */
    public interface Dial {


    }

    /**
     * 拨号模块网络接口错误码
     */
    public interface DialHttpErrorCode {
        int BASE_ACTION_ID = 40002000;
    }

    /**
     * 拨号模块消息ID
     */
    public interface DialMsgID {

        int BASE_MSG_ID = 40001000;
        int CALL_RECORD_START_SERTCH_CONTACT_MSG_ID = BASE_MSG_ID+1;
        int CALL_VIDEO_INCOMING_MSG_ID = BASE_MSG_ID+2;
        int CALL_ON_TALKING_MSG_ID = BASE_MSG_ID+3;
        int CALL_CLOSED_MSG_ID = BASE_MSG_ID+4;

    }


    /**
     * 设置模块常量
     */
    public interface Setting {

        //客户端必须更新
        String APP_MUST_UPDATE_Y = "1";

        //登录后获取版本
        String GET_VERSION_AFTER_LOGIN = "getVersionAfterLogin";


        String APP_SAVE_PATH = "/kangxi_app/";                  //头像保存路径
        String APP_IMG_DEFAULT_NAME = "kangxi_app_default_img.jpg"; //头像图片默认名字
        String MORE_SHARE_APP_URL = "http://www.baidu.com";
    }

    /**
     * 设置模块网络接口错误码
     */
    public interface SettingHttpErrorCode {

        int BASE_ACTION_ID = 50002000;

    }


    /**
     * 设置模块消息ID
     */
    public interface SettingMsgID {

        int BASE_MSG_ID = 50001000;
        //消息页面,编辑按钮被按下
        int EDIT_BUTTON_PRESSED = BASE_MSG_ID + 1;
        int CLEAN_MESSAGES_SELECTED_STATE = BASE_MSG_ID + 2;
        int MESSAGE_FRAGMENT_SWITCH_OFF = BASE_MSG_ID + 3;

    }


    public interface FragmentActionId {
        int BASE_ACTION_ID = 70001000;

        int SETTING_FRAGMENT_LOGOUT_ACTION_ID = BASE_ACTION_ID + 1;

        int DAIL_FRAGMENT_SHOW_CALL_ACTION_ID = BASE_ACTION_ID + 2;

        int DAIL_FRAGMENT_HIDE_CALL_ACTION_ID = BASE_ACTION_ID + 3;

        int DAIL_FRAGMENT_SHOW_KEYBORD_MSG_ID = BASE_ACTION_ID + 4;

        int DAIL_FRAGMENT_HIDE_KEYBORD_MSG_ID = BASE_ACTION_ID + 5;

        int DAIL_FRAGMENT_CALL_MSG_ID = BASE_ACTION_ID + 6;

        int CONTACT_FRAGMENT_SHOW_NAVIGATOR_ACTION_ID = BASE_ACTION_ID + 7;

        int CONTACT_FRAGMENT_HIDE_NAVIGATOR_ACTION_ID = BASE_ACTION_ID + 8;
    }

}


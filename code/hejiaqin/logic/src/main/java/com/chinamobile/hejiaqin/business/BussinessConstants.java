package com.chinamobile.hejiaqin.business;

/**
 * 业务常量值
 * hejiaqin Version 001
 * author:
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

        String LITTLEC_APP_KEY = "027336cw";

        int CALL_OUTING_HANGUP = 0;
        int CALL_TALKING_HANGUP = 1;
        int CALL_INCOMING_HANGUP = 2;

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

        String HTTP_ADDRESS = "http://218.205.115.220:8099/hjq";

        String HTTPS_ADDRESS = "https://service.XXX.com";
    }

    /**
     * 公用字典值表(必须和服务器保持一致)
     */
    public interface DictInfo {

        int SIP_TEMPORARILY_UNAVAILABLE = 480;
        int SIP_BUSY_HERE = 486;
        int SIP_DECLINE = 603;
        int SIP_TERMINATED = 487;

        int YES = 1;
        int NO = 0;
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

        String HEADER_BOUNDARY = "----hejiaqinapplicationrequestboundary";

    }

    /**
     * 服务器返回的ResultCode
     */
    public interface HttpCommonCode {
        //通用成功的ResultCode
        String COMMON_SUCCESS_CODE = "0";
    }

    /**
     * 服务器返回的ResultCode
     */
    public interface CommonMsgId {

        int BASE_MSG_ID = 10001000;

        //网络错误消息ID
        int NETWORK_ERROR_MSG_ID = BASE_MSG_ID + 1;

        int SERVER_SIDE_ERROR = BASE_MSG_ID + 2;

        //登录网络错误消息ID
        int LOGIN_NETWORK_ERROR_MSG_ID = BASE_MSG_ID + 3;

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

        String REGISTER_SECOND_STEP_INFO_KEY = "registerSecondInfo";

        String PASSWORD_INFO_KEY = "passwordInfo";

        String INTENT_LOGIN_ID = "registerLoginId";

        String INTENT_FROM_LONGIN = "fromLogin";
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


        //检查验证码成功的消息ID
        int RESET_CHECK_VERIFY_CDOE_SUCCESS_MSG_ID = BASE_MSG_ID + 21;
        //检查验证码失败的消息ID
        int RESET_CHECK_VERIFY_CDOE_FAIL_MSG_ID = BASE_MSG_ID + 22;

        int SHOW_LAUNCH_PAGE_FINISHED = BASE_MSG_ID + 23;

        //修改密码成功的消息ID
        int UPDATE_PHOTO_SUCCESS_MSG_ID = BASE_MSG_ID + 24;
        //修改密码失败的消息ID
        int UPDATE_PHOTO_FAIL_MSG_ID = BASE_MSG_ID + 25;

        int GET_USER_INFO_SUCCESS_MSG_ID = BASE_MSG_ID + 26;

        int GET_USER_INFO_FAIL_MSG_ID = BASE_MSG_ID + 27;
    }

    /**
     * 联系人模块常量
     */
    public interface Contact {
        // 联系人详情key值
        String INTENT_CONTACTSINFO_KEY = "INTENT_CONTACTSINFO_KEY";

        String INTENT_CONTACT_NUMBER_KEY = "INTENT_CONTACT_NUMBER_KEY";

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
        // 获取应用联系人失败消息ID
        int GET_APP_CONTACTS_FAILED_MSG_ID = BASE_MSG_ID + 4;
        // 搜索应用联系人成功的消息ID
        int SEARCH_APP_CONTACTS_SUCCESS_MSG_ID = BASE_MSG_ID + 5;

        // 添加应用联系人成功的消息ID
        int ADD_APP_CONTACTS_SUCCESS_MSG_ID = BASE_MSG_ID + 6;
        // 添加应用联系人失败的消息ID
        int ADD_APP_CONTACTS_FAILED_MSG_ID = BASE_MSG_ID + 7;

        // 修改应用联系人成功的消息ID
        int EDIT_APP_CONTACTS_SUCCESS_MSG_ID = BASE_MSG_ID + 8;
        // 修改应用联系人失败的消息ID
        int EDIT_APP_CONTACTS_FAILED_MSG_ID = BASE_MSG_ID + 9;


        // 删除应用联系人成功的消息ID
        int DEL_APP_CONTACTS_SUCCESS_MSG_ID = BASE_MSG_ID + 10;
        // 删除应用联系人失败的消息ID
        int DEL_APP_CONTACTS_FAILED_MSG_ID = BASE_MSG_ID + 11;


        // 获取通话记录成功的消息ID
        int GET_CALL_RECORDS_SUCCESS_MSG_ID = BASE_MSG_ID + 12;
        // 删除通话记录成功的消息ID
        int DEL_CALL_RECORDS_SUCCESS_MSG_ID = BASE_MSG_ID + 13;

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

        String CALL_ACTION = "android.intent.action.hejiaqin.dial.videocall";

        String INTENT_CALLEE_NUMBER = "intent_callee_number";

        String INTENT_CALL_INCOMING = "intent_call_incoming";

        String INTENT_INCOMING_SESSION_ID = "intent_incoming_session_id";

        String INTENT_CALLEE_NAME = "intent_callee_name";

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

        int CALL_RECORD_START_SERTCH_CONTACT_MSG_ID = BASE_MSG_ID + 1;
        int CALL_ON_TALKING_MSG_ID = BASE_MSG_ID + 2;
        int CALL_CLOSED_MSG_ID = BASE_MSG_ID + 3;
        int CALL_RECORD_REFRESH_MSG_ID = BASE_MSG_ID + 4;

        int VOIP_REGISTER_CONNECTED_MSG_ID = BASE_MSG_ID + 5;
        int VOIP_REGISTER_CONNECTING_MSG_ID = BASE_MSG_ID + 6;
        int VOIP_REGISTER_DISCONNECTED_MSG_ID = BASE_MSG_ID + 7;
        int VOIP_REGISTER_KICK_OUT_MSG_ID = BASE_MSG_ID + 8;
        int VOIP_REGISTER_NET_UNAVAILABLE_MSG_ID = BASE_MSG_ID + 9;

        int CALL_RECORD_DEL_ALL_MSG_ID = BASE_MSG_ID + 10;
        int CALL_RECORD_GET_ALL_MSG_ID = BASE_MSG_ID + 11;

        int CALL_ON_TV_INCOMING_MSG_ID = BASE_MSG_ID + 12;
        int CALL_RECORD_DEL_MSG_ID = BASE_MSG_ID + 13;

    }


    /**
     * 设置模块常量
     */
    public interface Setting {

        //客户端必须更新
        String APP_MUST_UPDATE_Y = "1";

        //登录后获取版本
        String GET_VERSION_AFTER_LOGIN = "getVersionAfterLogin";

        String VERSION_INFO_KEY = "versionInfoKey";
        String USER_SETTING_KEY = "userSettingInfo";
        String APP_SAVE_PATH = "/hejiaqin_app/";                  //头像保存路径
        String APP_IMG_DEFAULT_NAME = "hejiaqin_app_default_img.jpg"; //头像图片默认名字
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
        int CONTACT_CHECKED_STATED_CHANGED = BASE_MSG_ID + 4;
        int SEND_FEED_BACK_SUCCESS = BASE_MSG_ID + 5;
        int NEW_VERSION_AVAILABLE = BASE_MSG_ID + 6;
        int NO_NEW_VERSION_AVAILABLE = BASE_MSG_ID + 7;
        int NEW_FORCE_VERSION_AVAILABLE = BASE_MSG_ID + 8;
        int AUTO_ANSWER_SETTING_COMMIT = BASE_MSG_ID + 9;
        int GET_DEVICE_LIST_SUCCESSFUL = BASE_MSG_ID + 10;
        int BIND_REQUEST = BASE_MSG_ID + 11;
        int BIND_SUCCESS = BASE_MSG_ID + 12;
        int BIND_DENIED = BASE_MSG_ID + 13;
        int SAVE_BIND_REQUEST_SUCCESS = BASE_MSG_ID + 14;

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

        int DAIL_SHOW_DEL_POP_WINDOW_MSG_ID = BASE_ACTION_ID + 9;

        int DAIL_FRAGMENT_RECORD_HIDE_KEYBORD_MSG_ID = BASE_ACTION_ID + 10;

        int DAIL_FRAGMENT_CONTACT_HIDE_KEYBORD_MSG_ID = BASE_ACTION_ID + 11;

    }

}


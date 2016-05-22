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

    }

    public interface ActivityRequestCode
    {
        int BASE_QEUEST_CODE = 1000;
        //选择偏好的RequestCode
        int CHOOSE_PREFER_REQUEST_CODE = BASE_QEUEST_CODE+1;
    }

    /**
     * 服务器的地址
     */
    public interface ServerInfo {

        String HTTP_ADDRESS = "http://service.pop121.com";

        String HTTPS_ADDRESS = "https://service.pop121.com";
    }

    /**
     * 公用字典值表(必须和服务器保持一致)
     */
    public interface DictInfo {

        //男
        String SEX_MALE = "1";

        //女
        String SEX_FEMALE = "0";

        //我的
        String PRACTICE_TYPE_MY = "1";
        //推荐
        String PRACTICE_TYPE_RECOMMOND = "2";

        //1置顶，0不置顶
        String PLACE_TOP ="1";

        //1置顶，0不置顶
        String CANCEL_PLACE_TOP ="0";

        // 1有氧
        String MOVEMENT_TYPE_AEROBICS = "1";
        // 2力量
        String MOVEMENT_TYPE_STRENGTH = "2";
        //3瑜伽
        String MOVEMENT_TYPE_YOGA = "3";
        //4国术；
        String MOVEMENT_TYPE_KUNGFU = "4";

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
     * 首页模块常量
     */
    public interface HomePage {


    }

    /**
     * 首页模块网络接口错误码
     */
    public interface HomePageHttpErrorCode {


    }

    /**
     * 首页模块消息ID
     */
    public interface HomePageMsgID {

        int BASE_MSG_ID = 30001000;

        int GET_MY_HEALTH_SUCCESS_MSG_ID = BASE_MSG_ID + 1;

        int GET_MY_HEALTH_FAIL_MSG_ID = BASE_MSG_ID + 2;

        int GET_MY_PRACTICE_SUCCESS_MSG_ID = BASE_MSG_ID + 3;

        int GET_MY_PRACTICE_FAIL_MSG_ID = BASE_MSG_ID + 4;

        int GET_MY_CARE_SUCCESS_MSG_ID = BASE_MSG_ID + 5;

        int GET_MY_CARE_FAIL_MSG_ID = BASE_MSG_ID + 6;

        int GET_INDEX_LECTURE_SUCCESS_MSG_ID = BASE_MSG_ID + 7;

        int GET_INDEX_LECTURE_FAIL_MSG_ID = BASE_MSG_ID + 8;

        int DO_TOP_COURSE_SUCCESS_MSG_ID = BASE_MSG_ID + 9;

        int DO_TOP_COURSE_FAIL_MSG_ID = BASE_MSG_ID + 10;


    }

    /**
     * 康兮课程模块常量
     */
    public interface Courses {

        String COURSE_PRACTICE_SELECT_INDEX = "course_practice_select_index";

        String COURSE_PRACTICE_SELECT_TEXT = "course_practice_select_text";

        String COURSE_HEALTHCARE_SELECT_INDEX = "course_healthcare_select_index";

        String COURSE_HEALTHCARE_SELECT_TEXT = "course_healthcare_select_text";
    }

    /**
     * 康兮课程模块网络接口错误码
     */
    public interface CoursesHttpErrorCode {
        int BASE_ACTION_ID = 40002000;
        //最后一页
        int COURSE_LAST_PAGE_ACTION_ID = BASE_ACTION_ID + 1;

        //我的练习
        int COURSE_PRACTICE_ACTION_ID = BASE_ACTION_ID + 2;

        //我的练习，显示加载框
        int COURSE_PRACTICE_SHOW_LOADING_ACTION_ID = BASE_ACTION_ID + 3;

        //全部保健
        int COURSE_HEALTHCARE_ALL_ACTION_ID = BASE_ACTION_ID + 4;

        //全部保健，显示加载框
        int COURSE_HEALTHCARE_ALL_SHOW_LOADING_ACTION_ID = BASE_ACTION_ID + 5;

        //推荐保健
        int COURSE_HEALTHCARE_RECOMMEND_ACTION_ID = BASE_ACTION_ID + 6;

        //推荐保健，显示加载框
        int COURSE_HEALTHCARE_RECOMMEND_SHOW_LOADING_ACTION_ID = BASE_ACTION_ID + 7;

        //康兮讲堂
        int COURSE_FORUM_ACTION_ID = BASE_ACTION_ID + 8;

        //康兮讲堂，显示加载框
        int COURSE_FORUM_SHOW_LOADING_ACTION_ID = BASE_ACTION_ID + 9;
    }

    /**
     * 康兮课程模块消息ID
     */
    public interface CoursesMsgID {

        int BASE_MSG_ID = 40001000;

        //练习课程
        int FIND_ALL_TRAINING_COURSE_SUCCESS_MSG_ID = BASE_MSG_ID + 1;
        int FIND_ALL_TRAINING_COURSE_FAILED_MSG_ID = BASE_MSG_ID + 2;
        int FIND_RECOMMEND_TRAINING_COURSE_SUCCESS_MSG_ID = BASE_MSG_ID + 3;
        int FIND_RECOMMEND_TRAINING_COURSE_FAILED_MSG_ID = BASE_MSG_ID + 4;

        //康复课程
        int FIND_ALL_REHABILITATION_COURSE_SUCCESS_MSG_ID = BASE_MSG_ID + 5;
        int FIND_ALL_REHABILITATION_COURSE_FAILED_MSG_ID = BASE_MSG_ID + 6;
        int FIND_RECOMMEND_REHABILITATION_COURSE_SUCCESS_MSG_ID = BASE_MSG_ID + 7;
        int FIND_RECOMMEND_REHABILITATION_COURSE_FAILED_MSG_ID = BASE_MSG_ID + 8;

        //康兮讲堂
        int FIND_ALL_LECTURE_COURSE_SUCCESS_MSG_ID = BASE_MSG_ID + 9;
        int FIND_ALL_LECTURE_COURSE_FAILED_MSG_ID = BASE_MSG_ID + 10;
        int FIND_LECTURE_COURSE_DETAIL_SUCCESS_MSG_ID = BASE_MSG_ID + 11;
        int FIND_LECTURE_COURSE_DETAIL_FAILED_MSG_ID = BASE_MSG_ID + 12;
    }

    /**
     * 健康银行模块常量
     */
    public interface HealthBank {


    }

    /**
     * 健康银行模块网络接口错误码
     */
    public interface HealthBankHttpErrorCode {


    }

    /**
     * 健康银行模块消息ID
     */
    public interface HealthBankMsgID {

        int BASE_MSG_ID = 50001000;


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
    }

    /**
     * 设置模块网络接口错误码
     */
    public interface SettingHttpErrorCode {

        int BASE_ACTION_ID = 50002000;

        //我的全部消息
        int MESSAGE_FRAGMENT_ALL_ACTION_ID = BASE_ACTION_ID + 1;

        //我的已读消息
        int MESSAGE_FRAGMENT_READ_YES_ACTION_ID = BASE_ACTION_ID + 2;

        //我的未读消息
        int MESSAGE_FRAGMENT_READ_NO_ACTION_ID = BASE_ACTION_ID + 3;

        //最后一页
        int MESSAGE_FRAGMENT_LAST_PAGE_ACTION_ID = BASE_ACTION_ID + 4;

        //我的全部消息，显示加载框
        int MESSAGE_FRAGMENT_ALL_SHOW_LOADING_ACTION_ID = BASE_ACTION_ID + 5;

        //我的已读消息，显示加载框
        int MESSAGE_FRAGMENT_READ_YES_SHOW_LOADING_ACTION_ID = BASE_ACTION_ID + 6;

        //我的未读消息，显示加载框
        int MESSAGE_FRAGMENT_READ_NO_SHOW_LOADING_ACTION_ID = BASE_ACTION_ID + 7;
    }


    /**
     * 设置模块消息ID
     */
    public interface SettingMsgID {

        int BASE_MSG_ID = 50001000;

        //登录后获取最新版本信息
        int GET_VERSION_AFTER_LOGIN_SUCCESS_MSG_ID = BASE_MSG_ID + 1;

        //登录后获取最新版本信息
        int GET_VERSION_AFTER_LOGIN_FAIL_MSG_ID = BASE_MSG_ID + 2;

        //设置后获取最新版本信息
        int GET_VERSION_SUCCESS_MSG_ID = BASE_MSG_ID + 3;

        //设置后获取最新版本信息
        int GET_VERSION_FAIL_MSG_ID = BASE_MSG_ID + 4;

        //获取关于信息成功的消息ID
        int GET_ABOUT_SUCCESS_MSG_ID = BASE_MSG_ID + 5;

        //获取关于信息失败的消息ID
        int GET_ABOUT_FAIL_MSG_ID = BASE_MSG_ID + 6;

        //获取关于信息失败的消息ID
        int GET_ABOUT_NETWORK_ERROR_MSG_ID = BASE_MSG_ID + 7;
        int GET_SERVICE_CONTENT_SUCCESS_MSG_ID = BASE_MSG_ID + 8;
        int GET_SERVICE_FAIL_CONTENT_MSG_ID = BASE_MSG_ID + 9;

        //获取关于信息成功的消息ID
        int GET_MESSAGE_SUCCESS_MSG_ID = BASE_MSG_ID + 10;

        //获取关于信息失败的消息ID
        int GET_MESSAGE_FAIL_MSG_ID = BASE_MSG_ID + 11;

        //获取关于信息失败的消息ID
        int GET_MESSAGE_NETWORK_ERROR_MSG_ID = BASE_MSG_ID + 12;

        //获取帮助问题信息成功的消息ID
        int HELP_QUESTION_LIST_SUCCESS_MSG_ID = BASE_MSG_ID + 13;
        //获取帮助问题信息失败的消息ID
        int HELP_QUESTION_LIST_FAIL_MSG_ID = BASE_MSG_ID + 14;

        //获取用户设置信息的消息ID
        int LOAD_USER_SETTING_SUCCESS_MSG_ID = BASE_MSG_ID + 15;
        int LOAD_USER_SETTING_FAIL_MSG_ID = BASE_MSG_ID + 16;

        //修改用户设置信息的消息ID
        int AMEND_USER_SETTING_SUCCESS_MSG_ID = BASE_MSG_ID + 17;

        int AMEND_USER_SETTING_FAIL_MSG_ID = BASE_MSG_ID + 18;

        //提交用户反馈信息
        int SUBMIT_USER_FEEDBACK_SUCCESS_MSG_ID = BASE_MSG_ID + 19;
        int SUBMIT_USER_FEEDBACK_FAIL_MSG_ID = BASE_MSG_ID + 20;

        int GET_MESSAGE_DETAIL_SUCCESS_MSG_ID = BASE_MSG_ID + 21;
        int GET_MESSAGE_DETAIL_FAIL_MSG_ID = BASE_MSG_ID + 22;

        //获取关于信息成功的消息ID
        int DEL_MESSAGE_SUCCESS_MSG_ID = BASE_MSG_ID + 23;

        //获取关于信息失败的消息ID
        int DEL_MESSAGE_FAIL_MSG_ID = BASE_MSG_ID + 24;
    }


    /**
     * 个人资料模块常量（注：测试MgsID，后期请zhanggj调整修改或者删除）
     */
    public interface Person {

        String INTENT_CHECKED_PREFER_IDS = "checkedPreferIds";

        String INTENT_CHECKED_PREFERS = "checkedPrefers";
    }

    /**
     * 个人资料消息ID（注：测试MgsID，后期请zhanggj调整修改或者删除）
     */
    public interface PersonMsgID {

        int BASE_MSG_ID = 60001000;

        //获取个人资料成功的消息ID
        int GET_PERSON_INFO_SUCCESS_MSG_ID = BASE_MSG_ID + 1;

        //获取个人资料失败的消息ID
        int GET_PERSON_INFO_FAIL_MSG_ID = BASE_MSG_ID + 2;

        //获取个人资料失败的消息ID（网络错误）
        int GET_PERSON_INFO_NETWORK_ERROR_MSG_ID = BASE_MSG_ID + 3;

        //获取生理数据成功的消息ID
        int GET_PHYSIOLOGY_INFO_SUCCESS_MSG_ID = BASE_MSG_ID + 4;

        //获取生理数据失败的消息ID
        int GET_PHYSIOLOGY_INFO_FAIL_MSG_ID = BASE_MSG_ID + 5;

        //获取生理数据失败的消息ID（网络错误）
        int GET_PHYSIOLOGY_INFO_NETWORK_ERROR_MSG_ID = BASE_MSG_ID + 6;
        int CHANGE_PERSONAL_DOC_SUCCESS_MSG_ID = BASE_MSG_ID + 7;
        int CHANGE_PERSONAL_DOC_FAIL_MSG_ID = BASE_MSG_ID + 8;
        //修改生理数据
        int CHANGE_PHYSIOLOGICAL_INFO_SUCCESS_MSG_ID = BASE_MSG_ID + 9;
        int CHANGE_PHYSIOLOGICAL_INFO_FAIL_MSG_ID = BASE_MSG_ID + 10;

        //上传用户图像
        int UPLOAD_USER_AVATAR_IMAGE_SUCCESS_MSG_ID = BASE_MSG_ID + 11;
        int UPLOAD_USER_AVATAR_IMAGE_FAIL_MSG_ID = BASE_MSG_ID + 12;
        int CHANGE_SPORTS_PREFER_SUCCESS_MSG_ID= BASE_MSG_ID+13;
        int CHANGE_SPORTS_PREFER_FAIL_MSG_ID=BASE_MSG_ID+14;

    }

    public interface FragmentActionId {
        int BASE_ACTION_ID = 70001000;

        int SETTING_FRAGMENT_LOGOUT_ACTION_ID = BASE_ACTION_ID + 1;

        int COURSES_SWITCH_RECOMMEND_PRACTICE_ID = BASE_ACTION_ID + 2;

        int COURSES_SWITCH_RECOMMEND_CARE_ID = BASE_ACTION_ID + 3;

        int COURSES_SWITCH_FORUM_ID = BASE_ACTION_ID + 4;
    }

}


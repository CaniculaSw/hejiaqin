package com.chinamobile.hejiaqin.business.logic.contacts;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.dbApdater.ContactsDbAdapter;
import com.chinamobile.hejiaqin.business.manager.ContactsInfoManager;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.contacts.ContactList;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.req.AddContactReq;
import com.chinamobile.hejiaqin.business.model.contacts.rsp.ContactBean;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.MapStrReqBody;
import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.contacts.ContactsHttpManager;
import com.customer.framework.component.ThreadPool.ThreadPoolUtil;
import com.customer.framework.component.ThreadPool.ThreadTask;
import com.customer.framework.component.log.Logger;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.logic.LogicImp;

import java.util.Arrays;
import java.util.List;

/**
 * Created by th on 5/24/16.
 */
public class ContactsLogic extends LogicImp implements IContactsLogic {
    private static final String TAG = "ContactsLogic";

    @Override
    public void fetchLocalContactLst() {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {
                List<ContactsInfo> contactsInfoList = ContactsInfoManager.getInstance().getLocalContactLst(getContext());
                ContactsInfoManager.getInstance().sortContactsInfoLst(getContext(), contactsInfoList);
                ContactsInfoManager.getInstance().cacheLocalContactInfo(contactsInfoList);

                sendMessage(BussinessConstants.ContactMsgID.GET_LOCAL_CONTACTS_SUCCESS_MSG_ID, contactsInfoList);
            }
        });
    }


    @Override
    public void fetchAppContactLst() {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {
                // 从网络获取联系人数据 TODO
                NVPReqBody reqBody = new NVPReqBody();
                reqBody.add("token", UserInfoCacheManager.getToken(getContext()));

                new ContactsHttpManager(getContext()).list(null, reqBody, new IHttpCallBack() {
                    /**
                     * 网络请求成功响应
                     *
                     * @param invoker 调用者(用来区分不同的调用场景，差异化实现回调逻辑)
                     * @param obj     服务器响应解析后的对象
                     */
                    @Override
                    public void onSuccessful(Object invoker, Object obj) {

                        ContactList contactList = new ContactList();
                        List<ContactBean> contactBeanList = (List<ContactBean>) obj;
                        if (null == contactBeanList) {
                            sendMessage(BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID, contactList.get());
                            return;
                        }
                        for (ContactBean contactBean : contactBeanList) {
                            contactList.addAppContact(contactBean);
                        }

                        List<ContactsInfo> contactsInfoList = contactList.get();
                        ContactsInfoManager.getInstance().sortContactsInfoLst(getContext(), contactsInfoList);
                        sendMessage(BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID, contactsInfoList);
                    }

                    /**
                     * 网络请求业务失败响应
                     *
                     * @param invoker 调用者(用来区分不同的调用场景，差异化实现回调逻辑)
                     * @param code    业务错误码
                     * @param desc    业务错误描述
                     */
                    @Override
                    public void onFailure(Object invoker, String code, String desc) {

                    }

                    /**
                     * 网络请求网络失败响应
                     *
                     * @param errorCode 网络错误码
                     */
                    @Override
                    public void onNetWorkError(NetResponse.ResponseCode errorCode) {

                    }
                });

                // 从数据库获取联系人数据
                // 伪造数据,用于测试,先从本地联系人中获取 TODO
//                ContactsDbAdapter.getInstance(getContext(), "aaa").delAll();
//                List<ContactsInfo> contactsInfoList = ContactsInfoManager.getInstance().getLocalContactLst(getContext());
//                ContactsInfoManager.getInstance().sortContactsInfoLst(getContext(), contactsInfoList);
//                ContactsDbAdapter.getInstance(getContext(), "aaa").add(contactsInfoList);
//
//                List<ContactsInfo> newContactsInfoList = ContactsDbAdapter.getInstance(getContext(), "aaa").queryAll();
//                ContactsInfoManager.getInstance().sortContactsInfoLst(getContext(), newContactsInfoList);
//                sendMessage(BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID, newContactsInfoList);
            }
        });
    }


    @Override
    public List<ContactsInfo> getCacheLocalContactLst() {
        return ContactsInfoManager.getInstance().getCachedLocalContactInfo();
    }

    @Override
    public void searchLocalContactLst(String input) {
        List<ContactsInfo> contactsInfoList = getCacheLocalContactLst();
        sendMessage(BussinessConstants.ContactMsgID.SEARCH_LOCAL_CONTACTS_SUCCESS_MSG_ID, ContactsInfoManager.getInstance().searchContactsInfoLst(contactsInfoList, input));
    }

    @Override
    public void addAppContact(final String name, final String number, final byte[] photo) {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {

                AddContactReq request = new AddContactReq();
                request.setToken(UserInfoCacheManager.getToken(getContext()));
                request.setName(name);
                request.setPhone(number);

                new ContactsHttpManager(getContext()).add(null, request, new IHttpCallBack() {

                    @Override
                    public void onSuccessful(Object invoker, Object obj) {
                        // 联系人信息添加到数据库
                        ContactBean contactBean = (ContactBean) obj;
                        if (null == contactBean) {

                        }

                        // 获取当前所有联系人列表


                        // 发送消息给界面刷新
                        sendMessage(BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_SUCCESS_MSG_ID, obj);
                    }

                    @Override
                    public void onFailure(Object invoker, String code, String desc) {

                    }

                    @Override
                    public void onNetWorkError(NetResponse.ResponseCode errorCode) {

                    }
                });

            }
        });
    }

    @Override
    public void batchAddAppContacts() {

    }

    @Override
    public void updateAppContact(final String contactId, final String name, final String number, final byte[] photo) {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {

                MapStrReqBody reqBody = new MapStrReqBody();
                reqBody.add("token", UserInfoCacheManager.getToken(getContext()));
                reqBody.add("contactId", String.valueOf(contactId));
                reqBody.add("file", String.valueOf(photo));
                reqBody.add("name", name);
                reqBody.add("phone", number);

                new ContactsHttpManager(getContext()).update(null, reqBody, new IHttpCallBack() {

                    @Override
                    public void onSuccessful(Object invoker, Object obj) {

                    }

                    @Override
                    public void onFailure(Object invoker, String code, String desc) {

                    }

                    @Override
                    public void onNetWorkError(NetResponse.ResponseCode errorCode) {

                    }
                });

            }
        });
    }

}

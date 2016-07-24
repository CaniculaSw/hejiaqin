package com.chinamobile.hejiaqin.business.logic.contacts;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.dbApdater.CallRecordDbAdapter;
import com.chinamobile.hejiaqin.business.dbApdater.ContactsDbAdapter;
import com.chinamobile.hejiaqin.business.manager.ContactsInfoManager;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.contacts.ContactList;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.model.contacts.req.AddContactReq;
import com.chinamobile.hejiaqin.business.model.contacts.req.EditContactReq;
import com.chinamobile.hejiaqin.business.model.contacts.req.SimpleContactInfo;
import com.chinamobile.hejiaqin.business.model.contacts.rsp.ContactBean;
import com.chinamobile.hejiaqin.business.model.dial.CallRecord;
import com.chinamobile.hejiaqin.business.model.dial.DialInfo;
import com.chinamobile.hejiaqin.business.model.dial.DialInfoGroup;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.contacts.ContactsHttpManager;
import com.chinamobile.hejiaqin.business.net.NVPWithTokenReqBody;
import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.contacts.ContactsHttpManager;
import com.chinamobile.hejiaqin.business.utils.CommonUtils;
import com.customer.framework.component.ThreadPool.ThreadPoolUtil;
import com.customer.framework.component.ThreadPool.ThreadTask;
import com.customer.framework.component.log.Logger;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.component.time.TimerUtil;
import com.customer.framework.logic.LogicImp;
import com.customer.framework.utils.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
                // 先从本地数据库获取数据并通知UI更新
                fetchAppContactFromDb();

                // 再从网络获取联系人数据
                fetchAppContactsFromServer();
            }
        });
    }


    @Override
    public List<ContactsInfo> getCacheLocalContactLst() {
        return ContactsInfoManager.getInstance().getCachedLocalContactInfo();
    }

    @Override
    public List<ContactsInfo> getCacheAppContactLst() {
        return ContactsInfoManager.getInstance().getCachedAppContactInfo();
    }

    @Override
    public void searchLocalContactLst(String input) {
        List<ContactsInfo> contactsInfoList = getCacheLocalContactLst();
        sendMessage(BussinessConstants.ContactMsgID.SEARCH_LOCAL_CONTACTS_SUCCESS_MSG_ID, ContactsInfoManager.getInstance().searchContactsInfoLst(contactsInfoList, input));
    }

    @Override
    public void searchAppContactLst(String input) {
        List<ContactsInfo> contactsInfoList = getCacheAppContactLst();
        sendMessage(BussinessConstants.ContactMsgID.SEARCH_APP_CONTACTS_SUCCESS_MSG_ID, ContactsInfoManager.getInstance().searchContactsInfoLst(contactsInfoList, input));
    }

    @Override
    public void addAppContact(final String name, final String number, final String photoFullPath) {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {

                AddContactReq request = new AddContactReq();
                request.setName(name);
                request.setPhone(number);
                request.setFile(photoFullPath);

                new ContactsHttpManager(getContext()).add(null, request, new IHttpCallBack() {

                    @Override
                    public void onSuccessful(Object invoker, Object obj) {
                        // 联系人信息添加到数据库
                        ContactBean contactBean = (ContactBean) obj;
                        if (null == contactBean) {
                            sendEmptyMessage(BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_FAILED_MSG_ID);
                            return;
                        }

                        ContactList contactList = new ContactList();
                        contactList.addAppContact(contactBean);
                        // 增加数据库中的联系人信息
                        ContactsDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()))
                                .add(contactList.get());
                        // 重新获取数据库数据刷新联系人列表
                        fetchAppContactFromDb();

                        // 发送消息给界面刷新
                        sendEmptyMessage(BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_SUCCESS_MSG_ID);
                    }

                    @Override
                    public void onFailure(Object invoker, String code, String desc) {
                        Logger.d(TAG, "add, onFailure, code: " + code + ", desc: " + desc);
                        sendEmptyMessage(BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_FAILED_MSG_ID);
                    }

                    @Override
                    public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                        Logger.d(TAG, "add, onNetWorkError, errorCode: " + errorCode);
                        sendEmptyMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
                    }
                });

            }
        });
    }


    @Override
    public void addAppContact(ContactsInfo contactsInfo) {
        List<ContactsInfo> contactsInfoList = new ArrayList<ContactsInfo>();
        contactsInfoList.add(contactsInfo);
        batchAddAppContacts(contactsInfoList);
    }

    @Override
    public void batchAddAppContacts(final List<ContactsInfo> contactsInfoList) {
        if (null == contactsInfoList || contactsInfoList.isEmpty()) {
            return;
        }

        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {

                List<SimpleContactInfo> simpleContactInfoList = new ArrayList<SimpleContactInfo>();
                for (ContactsInfo contactsInfo : contactsInfoList) {
                    List<NumberInfo> numberInfoList = contactsInfo.getNumberLst();
                    if (null == numberInfoList) {
                        continue;
                    }

                    for (NumberInfo numberInfo : numberInfoList) {
                        SimpleContactInfo simpleContactInfo = new SimpleContactInfo();
                        simpleContactInfo.setName(contactsInfo.getName());
                        simpleContactInfo.setPhone(numberInfo.getNumber());
                        simpleContactInfoList.add(simpleContactInfo);
                    }
                }

                Gson gson = new Gson();
                String contactJson = gson.toJson(simpleContactInfoList, new TypeToken<List<SimpleContactInfo>>() {
                }.getType());


//                BatchAddContactReq reqBody = new BatchAddContactReq();
//                reqBody.setToken(UserInfoCacheManager.getToken(getContext()));
//                reqBody.setContactJson(contactJson);

                NVPWithTokenReqBody reqBody = new NVPWithTokenReqBody();
                reqBody.add("contactJson", contactJson);

                new ContactsHttpManager(getContext()).batchAdd(null, reqBody, new IHttpCallBack() {

                    @Override
                    public void onSuccessful(Object invoker, Object obj) {
                        // 获取服务器返回的数据
                        List<ContactBean> contactBeanList = (List<ContactBean>) obj;

                        // 将服务器的数据ContactBean转换成本地的ContactInfo
                        ContactList contactList = new ContactList();
                        if (null == contactBeanList) {
                            sendMessage(BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID, contactList.get());
                            return;
                        }
                        for (ContactBean contactBean : contactBeanList) {
                            contactList.addAppContact(contactBean);
                        }
                        List<ContactsInfo> contactsInfoList = contactList.get();
                        // 增加数据库中的联系人信息
                        ContactsDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()))
                                .add(contactsInfoList);
                        // 重新获取数据库数据刷新联系人列表
                        fetchAppContactFromDb();
                        sendEmptyMessage(BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_SUCCESS_MSG_ID);
                    }

                    @Override
                    public void onFailure(Object invoker, String code, String desc) {
                        Logger.d(TAG, "batchAdd, onFailure, code: " + code + ", desc: " + desc);
                        sendEmptyMessage(BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_FAILED_MSG_ID);
                    }

                    @Override
                    public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                        Logger.d(TAG, "batchAdd, onNetWorkError, errorCode: " + errorCode);
                        sendEmptyMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
                    }
                });

            }
        });
    }

    @Override
    public void updateAppContact(final String contactId, final String name, final String number
            , final String photoFullPath) {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {


                EditContactReq request = new EditContactReq();
                request.setName(name);
                request.setPhone(number);
                request.setContactId(contactId);
                request.setFile(photoFullPath);

                new ContactsHttpManager(getContext()).update(null, request, new IHttpCallBack() {

                    @Override
                    public void onSuccessful(Object invoker, Object obj) {


                        ContactBean contactBean = (ContactBean) obj;
                        if (null == contactBean) {
                            sendEmptyMessage(BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_FAILED_MSG_ID);
                            return;
                        }

                        ContactsInfo contactsInfo = ContactList.convert(contactBean);

                        // 修改本地数据库联系人信息
                        ContactsDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).update(contactsInfo);
                        // 重新获取数据库数据刷新联系人列表
                        fetchAppContactFromDb();
                        // 发送UI消息更新界面
                        sendMessage(BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_SUCCESS_MSG_ID, contactsInfo);
                    }

                    @Override
                    public void onFailure(Object invoker, String code, String desc) {
                        Logger.d(TAG, "update, onFailure, code: " + code + ", desc: " + desc);
                        sendEmptyMessage(BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_FAILED_MSG_ID);
                    }

                    @Override
                    public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                        Logger.d(TAG, "update, onNetWorkError, errorCode: " + errorCode);
                        sendEmptyMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
                    }
                });

            }
        });
    }

    @Override
    public void deleteAppContact(final String contactId) {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {
                NVPWithTokenReqBody reqBody = new NVPWithTokenReqBody();
                reqBody.add("contactId", contactId);

                new ContactsHttpManager(getContext()).delete(null, reqBody, new IHttpCallBack() {

                    @Override
                    public void onSuccessful(Object invoker, Object obj) {

                        // 删除数据库中的联系人信息
                        ContactsDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()))
                                .delByContactId(contactId);
                        // 重新获取数据库数据刷新联系人列表
                        fetchAppContactFromDb();
                        sendEmptyMessage(BussinessConstants.ContactMsgID.DEL_APP_CONTACTS_SUCCESS_MSG_ID);
                    }

                    @Override
                    public void onFailure(Object invoker, String code, String desc) {
                        Logger.d(TAG, "delete, onFailure, code: " + code + ", desc: " + desc);
                        sendEmptyMessage(BussinessConstants.ContactMsgID.DEL_APP_CONTACTS_FAILED_MSG_ID);
                    }

                    @Override
                    public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                        Logger.d(TAG, "delete, onNetWorkError, errorCode: " + errorCode);
                        sendEmptyMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
                    }
                });
            }
        });
    }

    private void fetchAppContactFromDb() {
        List<ContactsInfo> newContactsInfoList = ContactsDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()))
                .queryAll();
        ContactsInfoManager.getInstance().sortContactsInfoLst(getContext(), newContactsInfoList);
        ContactsInfoManager.getInstance().cacheAppContactInfo(newContactsInfoList);
        sendMessage(BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID, newContactsInfoList);
    }

    private void fetchAppContactsFromServer() {
        NVPWithTokenReqBody reqBody = new NVPWithTokenReqBody();

        new ContactsHttpManager(getContext()).list(null, reqBody, new IHttpCallBack() {
            /**
             * 网络请求成功响应
             *
             * @param invoker 调用者(用来区分不同的调用场景，差异化实现回调逻辑)
             * @param obj     服务器响应解析后的对象
             */
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                // 获取服务器返回的数据
                List<ContactBean> contactBeanList = (List<ContactBean>) obj;

                // 将服务器的数据ContactBean转换成本地的ContactInfo
                ContactList contactList = new ContactList();
                if (null == contactBeanList) {
                    sendMessage(BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID, contactList.get());
                    return;
                }
                for (ContactBean contactBean : contactBeanList) {
                    contactList.addAppContact(contactBean);
                }
                List<ContactsInfo> contactsInfoList = contactList.get();

                // 汉字转拼音并进行排序
                ContactsInfoManager.getInstance().sortContactsInfoLst(getContext(), contactsInfoList);
                // 替换数据库中的旧数据
                ContactsDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).delAll();
                ContactsDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext())).add(contactsInfoList);
                ContactsInfoManager.getInstance().cacheAppContactInfo(contactsInfoList);

                // 通知界面更新数据
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
                Logger.d(TAG, "list, onFailure, code: " + code + ", desc: " + desc);
                sendEmptyMessage(BussinessConstants.ContactMsgID.GET_APP_CONTACTS_FAILED_MSG_ID);
            }

            /**
             * 网络请求网络失败响应
             *
             * @param errorCode 网络错误码
             */
            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                Logger.d(TAG, "list, onNetWorkError, errorCode: " + errorCode);
                sendEmptyMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID);
            }
        });
    }

    public void queryContactCallRecords(final ContactsInfo contactsInfo) {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {
                List<CallRecord> callRecordList = new ArrayList<CallRecord>();
                if (null == contactsInfo) {
                    sendMessage(BussinessConstants.ContactMsgID.GET_CALL_RECORDS_SUCCESS_MSG_ID,
                            callRecordList);
                    return;
                }


                List<NumberInfo> numberInfoList = contactsInfo.getNumberLst();
                if (null == numberInfoList) {
                    sendMessage(BussinessConstants.ContactMsgID.GET_CALL_RECORDS_SUCCESS_MSG_ID,
                            callRecordList);
                    return;
                }

                final int numberSize = numberInfoList.size();
                String[] numbers = new String[numberSize];
                for (int i = 0; i < numberSize; i++) {
                    String number = numberInfoList.get(i).getNumber();
                    numbers[i] = CommonUtils.getPhoneNumber(number);
                }

                callRecordList = CallRecordDbAdapter.getInstance(getContext(),
                        UserInfoCacheManager.getUserId(getContext())).queryWithNumbers(numbers);

                List<DialInfoGroup> dialInfoGroupList = callRecord2DialInfoGroup(callRecordList);

                sendMessage(BussinessConstants.ContactMsgID.GET_CALL_RECORDS_SUCCESS_MSG_ID, dialInfoGroupList);
            }
        });
    }

    public void deleteContactCallRecords(final ContactsInfo contactsInfo) {
        ThreadPoolUtil.execute(new ThreadTask() {
            @Override
            public void run() {
                if (null == contactsInfo) {
                    sendEmptyMessage(BussinessConstants.ContactMsgID.DEL_CALL_RECORDS_SUCCESS_MSG_ID);
                    return;
                }


                List<NumberInfo> numberInfoList = contactsInfo.getNumberLst();
                if (null == numberInfoList) {
                    sendEmptyMessage(BussinessConstants.ContactMsgID.DEL_CALL_RECORDS_SUCCESS_MSG_ID);
                    return;
                }

                final int numberSize = numberInfoList.size();
                String[] numbers = new String[numberSize];
                for (int i = 0; i < numberSize; i++) {
                    String number = numberInfoList.get(i).getNumber();
                    numbers[i] = CommonUtils.getPhoneNumber(number);
                }

                CallRecordDbAdapter.getInstance(getContext(),
                        UserInfoCacheManager.getUserId(getContext())).deleteByNumbers(numbers);
                sendEmptyMessage(BussinessConstants.ContactMsgID.DEL_CALL_RECORDS_SUCCESS_MSG_ID);
            }
        });
    }

    private List<DialInfoGroup> callRecord2DialInfoGroup(List<CallRecord> callRecordList) {
        List<DialInfoGroup> dialInfoGroupList = new ArrayList<DialInfoGroup>();
        if (null == callRecordList) {
            return dialInfoGroupList;
        }

        Map<String, List<DialInfo>> dialInfoMap = new HashMap<String, List<DialInfo>>();
        for (CallRecord callRecord : callRecordList) {
            String beginTime = callRecord.getBeginTime();
            if (null == beginTime) {
                continue;
            }

            List<DialInfo> dialInfoList = dialInfoMap.get(beginTime);
            if (null == dialInfoList) {
                dialInfoList = new ArrayList<>();
                dialInfoMap.put(beginTime, dialInfoList);
            }

            DialInfo dialInfo = new DialInfo();
            dialInfo.setType(DialInfo.convertType(callRecord.getType()));
            dialInfo.setDialTime(callRecord.getBeginHour());
            String durationTime = TimeUtil.disToTimeWithLanuage(Integer.parseInt(dialInfo.getDialDuration()));
            dialInfo.setDialDuration(durationTime);
            dialInfoList.add(dialInfo);
        }

        Iterator<Map.Entry<String, List<DialInfo>>> iter = dialInfoMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, List<DialInfo>> entry = iter.next();
            DialInfoGroup dialInfoGroup = new DialInfoGroup();
            dialInfoGroup.setGroupName(entry.getKey());
            dialInfoGroup.setDialInfoList(entry.getValue());
            dialInfoGroupList.add(dialInfoGroup);
        }

        return dialInfoGroupList;
    }
}

package com.chinamobile.hejiaqin.business.logic.person;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonalDocument;
import com.chinamobile.hejiaqin.business.model.person.PhysiologyInfo;
import com.chinamobile.hejiaqin.business.model.person.Preference;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.person.PersonHttpManager;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.logic.LogicImp;
import com.customer.framework.utils.StringUtil;
import com.customer.framework.utils.cryptor.AES;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/4/28.
 */
public class PersonLogic extends LogicImp implements IPersonLogic {
    @Override
    public void loadPersonInfo() {
        new PersonHttpManager(getContext()).loadPersonInfo(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                PersonInfo personInfo = (PersonInfo) obj;
                PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.GET_PERSON_INFO_SUCCESS_MSG_ID, obj);
                UserInfoCacheManager.updateUserInfo(getContext(), personInfo);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.GET_PERSON_INFO_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                PersonLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }


    @Override
    public void loadPhysiologyInfo() {
        new PersonHttpManager(getContext()).loadPhysiologyInfo(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                PhysiologyInfo physiologyInfo = (PhysiologyInfo) obj;
                PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.GET_PHYSIOLOGY_INFO_SUCCESS_MSG_ID, physiologyInfo);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.GET_PHYSIOLOGY_INFO_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                PersonLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void changePhysiologyInfo(final PhysiologyInfo physiologyInfo) {
        new PersonHttpManager(getContext()).changePhysiologyInfo(null, physiologyInfo, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.CHANGE_PHYSIOLOGICAL_INFO_SUCCESS_MSG_ID, physiologyInfo);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.CHANGE_PHYSIOLOGICAL_INFO_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                PersonLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void changePersonalDoc(final PersonalDocument personalDocument) {
        new PersonHttpManager(getContext()).changePersonalDoc(null, personalDocument, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.CHANGE_PERSONAL_DOC_SUCCESS_MSG_ID, personalDocument);
                UserInfoCacheManager.updateUserInfo(getContext(), personalDocument);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.CHANGE_PERSONAL_DOC_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                PersonLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void changeSportsPrefer(Preference preference) {
        new PersonHttpManager(getContext()).changeSportsPrefers(null, preference, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.CHANGE_SPORTS_PREFER_SUCCESS_MSG_ID, obj);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.CHANGE_SPORTS_PREFER_FAIL_MSG_ID, code);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                PersonLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    @Override
    public void updateUserHeader(String path) {
        //===============================To do: 测试图片上传=====================================
        String url =  BussinessConstants.ServerInfo.HTTP_ADDRESS + "/s/appuc/user/uploadAvatar";
        File imageFile = new File(path);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), imageFile);
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"avatar\"; filename=\"" + imageFile.getName() + "\""), fileBody);
        RequestBody requestBody = builder.build();

        UserInfo info = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        String unique = UUID.randomUUID().toString();

        Request.Builder reqBuilder = new Request.Builder();
        reqBuilder.url(url);
        reqBuilder.addHeader(BussinessConstants.HttpHeaderInfo.HEADER_TOKENID, AES.encrypt(info.getToken(), unique.substring(0, 8)));
        reqBuilder.addHeader(BussinessConstants.HttpHeaderInfo.HEADER_UNIQ, unique);
        reqBuilder.post(requestBody);

        Request request = reqBuilder.build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                PersonLogic.this.sendEmptyMessage(BussinessConstants.PersonMsgID.UPLOAD_USER_AVATAR_IMAGE_FAIL_MSG_ID);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String obj = response.body().string();
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(obj);
                        String data = jsonObject.get("data").toString();
                        Gson gson = new Gson();
                        PersonInfo info = gson.fromJson(data, PersonInfo.class);
                        if (info != null && !StringUtil.isNullOrEmpty(info.getAvatar())) {
                            PersonLogic.this.sendMessage(BussinessConstants.PersonMsgID.UPLOAD_USER_AVATAR_IMAGE_SUCCESS_MSG_ID,info.getAvatar());
                        }
                    } catch (Exception ex) {
                        Log.d(TAG, "解析数据异常");
                    }
                }

            }
        });
    }
}

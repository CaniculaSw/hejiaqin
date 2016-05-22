
package com.chinamobile.hejiaqin.business.model.login.req;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * 注册信息
 * Kangxi Version 001
 * author: zhanggj
 * Created: 2016/4/8.
 */
public class RegisterSecondStepInfo implements ReqBody, Parcelable {

    //用户ID
    private String loginid;

    //偏好
    private String prefer;

    // 必须要创建一个名叫CREATOR的常量。
    public static final Parcelable.Creator<RegisterSecondStepInfo> CREATOR = new Parcelable.Creator<RegisterSecondStepInfo>() {
        @Override
        public RegisterSecondStepInfo createFromParcel(Parcel source) {
            return new RegisterSecondStepInfo(source);
        }
        //重写createFromParcel方法，创建并返回一个获得了数据的user对象
        @Override
        public RegisterSecondStepInfo[] newArray(int size) {
            return new RegisterSecondStepInfo[size];
        }
    };

    public RegisterSecondStepInfo() {
    }


    // 带参构造器方法私用化，本构造器仅供类的方法createFromParcel调用
    private RegisterSecondStepInfo(Parcel source) {
        loginid = source.readString();
        prefer =  source.readString();
    }


    public String getLoginid() {
        return loginid;
    }


    public String getPrefer() {
        return prefer;
    }

    public void setPrefer(String prefer) {
        this.prefer = prefer;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(loginid);
        dest.writeString(prefer);

    }
}

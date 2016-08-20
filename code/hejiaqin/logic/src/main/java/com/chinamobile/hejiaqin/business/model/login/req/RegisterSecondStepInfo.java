
package com.chinamobile.hejiaqin.business.model.login.req;

import android.os.Parcel;
import android.os.Parcelable;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 * 注册信息
 * hejiaqin Version 001
 * author:
 * Created: 2016/4/8.
 */
public class RegisterSecondStepInfo implements ReqBody, Parcelable {

    private String phone;
    private String code;
    //用户密码
    private String pwd;



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
        pwd = source.readString();
        phone = source.readString();
        code = source.readString();
    }

    @Override
    public String toBody() {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("phone",getPhone());
        reqBody.add("code",getCode());
        reqBody.add("password",getPwd());
        return reqBody.toBody();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pwd);
        dest.writeString(phone);
        dest.writeString(code);
    }
}

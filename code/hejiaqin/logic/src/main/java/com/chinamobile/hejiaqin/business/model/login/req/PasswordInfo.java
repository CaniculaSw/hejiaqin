package com.chinamobile.hejiaqin.business.model.login.req;

import android.os.Parcel;
import android.os.Parcelable;

import com.chinamobile.hejiaqin.business.net.NVPReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;

/**
 *
 */
public class PasswordInfo implements ReqBody , Parcelable {

    private String resetToken;
    private String password;

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toBody() {
        NVPReqBody reqBody = new NVPReqBody();
        reqBody.add("resetToken",getResetToken());
        reqBody.add("password",getPassword());
        return reqBody.toBody();
    }

    // 必须要创建一个名叫CREATOR的常量。
    public static final Parcelable.Creator<PasswordInfo> CREATOR = new Parcelable.Creator<PasswordInfo>() {
        @Override
        public PasswordInfo createFromParcel(Parcel source) {
            return new PasswordInfo(source);
        }
        //重写createFromParcel方法，创建并返回一个获得了数据的user对象
        @Override
        public PasswordInfo[] newArray(int size) {
            return new PasswordInfo[size];
        }
    };
    public PasswordInfo() {
    }
    // 带参构造器方法私用化，本构造器仅供类的方法createFromParcel调用
    private PasswordInfo(Parcel source) {
        resetToken = source.readString();
        password = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resetToken);
        dest.writeString(password);
    }
}

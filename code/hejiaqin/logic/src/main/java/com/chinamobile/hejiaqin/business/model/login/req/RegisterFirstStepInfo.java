
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
public class RegisterFirstStepInfo implements ReqBody, Parcelable {

    //昵称
    private String name;
    //用户ID
    private String loginid;
    //用户密码
    private String pwd;

    private String phone;

    //性别
    private String sex;

    private String birthday;


    private RegisterPhysiologyInfo physiology = new RegisterPhysiologyInfo();

    // 必须要创建一个名叫CREATOR的常量。
    public static final Parcelable.Creator<RegisterFirstStepInfo> CREATOR = new Parcelable.Creator<RegisterFirstStepInfo>() {
        @Override
        public RegisterFirstStepInfo createFromParcel(Parcel source) {
            return new RegisterFirstStepInfo(source);
        }
        //重写createFromParcel方法，创建并返回一个获得了数据的user对象
        @Override
        public RegisterFirstStepInfo[] newArray(int size) {
            return new RegisterFirstStepInfo[size];
        }
    };

    public RegisterFirstStepInfo() {
    }

    // 带参构造器方法私用化，本构造器仅供类的方法createFromParcel调用
    private RegisterFirstStepInfo(Parcel source) {
        name = source.readString();
        loginid = source.readString();
        pwd = source.readString();
        phone = source.readString();
        sex = source.readString();
        birthday = source.readString();
        physiology.setHeight(source.readString());
        physiology.setWeight(source.readString());
    }

    @Override
    public String toBody() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public RegisterPhysiologyInfo getPhysiology() {
        return physiology;
    }

    public void setPhysiology(RegisterPhysiologyInfo physiology) {
        this.physiology = physiology;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(loginid);
        dest.writeString(pwd);
        dest.writeString(phone);
        dest.writeString(sex);
        dest.writeString(birthday);
        dest.writeString(physiology.getHeight());
        dest.writeString(physiology.getWeight());
    }
}

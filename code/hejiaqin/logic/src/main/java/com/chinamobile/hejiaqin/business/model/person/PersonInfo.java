package com.chinamobile.hejiaqin.business.model.person;

/**
 * desc:用户登录后服务器返回的信息
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/14.
 */
public class PersonInfo  {

    private String id;
    private String loginid;
    private String name;
    private String phone;
    private String userType = "1"; // 用户身份类型，默认1为普通用户，以后可能扩展。
    private String avatar;
    private String motto;
    private String growup; //成长值
    private String grade; //等级(康兮达人级别)
    private long credits; //我的当前积分
    private double balance; //余额
    private String sex; //性别
    private String birthday; //生日
    private PhysiologyInfo physiology;
    private String prefer; //我的偏好描述
    private String preferIds;//我的偏好ids

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreferIds() {
        return preferIds;
    }

    public void setPreferIds(String preferIds) {
        this.preferIds = preferIds;
    }

    public String getPrefer() {
        return prefer;
    }

    public void setPrefer(String prefer) {
        this.prefer = prefer;
    }

    public PhysiologyInfo getPhysiology() {
        return physiology;
    }

    public void setPhysiology(PhysiologyInfo physiology) {
        this.physiology = physiology;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getCredits() {
        return credits;
    }

    public void setCredits(long credits) {
        this.credits = credits;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGrowup() {
        return growup;
    }

    public void setGrowup(String growup) {
        this.growup = growup;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }
}

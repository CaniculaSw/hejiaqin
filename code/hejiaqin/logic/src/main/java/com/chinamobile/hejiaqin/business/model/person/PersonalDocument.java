package com.chinamobile.hejiaqin.business.model.person;

import com.chinamobile.hejiaqin.business.net.MapStrReqBody;
import com.chinamobile.hejiaqin.business.net.ReqBody;
import com.customer.framework.utils.StringUtil;

/**
 * Created by wubg on 2016/5/11.
 */
public class PersonalDocument implements ReqBody {
    private String name;
    private String avatar;
    private String sex;
    private String birthday;
    private String motto;
    private String preferIds;
    private String prefers;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getMotto() {
        return motto;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getPreferIds() {
        return preferIds;
    }

    public void setPreferIds(String preferIds) {
        this.preferIds = preferIds;
    }

    public String getPrefers() {
        return prefers;
    }

    public void setPrefers(String prefers) {
        this.prefers = prefers;
    }

    @Override
    public String toBody() {
        MapStrReqBody reqBody = new MapStrReqBody();
        reqBody.add("name", name);
        if(!StringUtil.isNullOrEmpty(avatar)) {
            reqBody.add("avatar", avatar);
        }
        reqBody.add("sex", sex);
        reqBody.add("birthday", birthday);
        reqBody.add("motto", motto);
        reqBody.add("preferIds", preferIds);
        return reqBody.toBody();
    }
}

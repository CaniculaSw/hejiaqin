package com.chinamobile.hejiaqin.business.logic;

import android.content.Context;

import com.chinamobile.hejiaqin.business.logic.contacts.ContactsLogic;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.logic.login.LoginLogic;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.logic.setting.SettingLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.business.logic.voip.VoipLogic;
import com.customer.framework.logic.BuilderImp;

/**
 * Logic buider
 * hejiaqin Version 001
 * author:
 * Created: 2016/4/8.
 */
public class LogicBuilder extends BuilderImp {


    /**
     * 构造方法，首先执行BaseLogicBuilder子类的init方法，然后对所有logic进行初始化。
     *
     * @param context 系统的context对象
     */
    private LogicBuilder(Context context) {
        super(context);
    }

    /**
     * 获取单例对象
     *
     * @param context 系统的context对象
     * @return LogicBuilder对象
     */
    public synchronized static BuilderImp getInstance(Context context) {
        if (getInstance() == null) {
            setInstance(new LogicBuilder(context));
        }
        return getInstance();
    }

    @Override
    protected void init(Context context) {
        //注册LoginLogic
        super.registerLogic(ILoginLogic.class, new LoginLogic());
        SettingLogic settingLogic = SettingLogic.getInstance(context);
        super.registerLogic(ISettingLogic.class, settingLogic);
        super.registerLogic(IContactsLogic.class, new ContactsLogic());
        VoipLogic voipLogic = VoipLogic.getInstance(context);
        super.registerLogic(IVoipLogic.class, voipLogic);
    }
}

package com.chinamobile.hejiaqin.business.logic.person;

import com.chinamobile.hejiaqin.business.model.person.PersonalDocument;
import com.chinamobile.hejiaqin.business.model.person.PhysiologyInfo;
import com.chinamobile.hejiaqin.business.model.person.Preference;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/4/28.
 */
public interface IPersonLogic {

    void loadPersonInfo();

    void loadPhysiologyInfo();

    void changePhysiologyInfo(PhysiologyInfo physiologyInfo);

    void changePersonalDoc(PersonalDocument personalDocument);

    void changeSportsPrefer(Preference preference);

    void updateUserHeader(String path);
}

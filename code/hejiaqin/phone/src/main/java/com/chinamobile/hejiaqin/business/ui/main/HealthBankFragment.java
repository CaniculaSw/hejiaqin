package com.chinamobile.hejiaqin.business.ui.main;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.person.IPersonLogic;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonalDocument;
import com.chinamobile.hejiaqin.business.model.person.PhysiologyInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.component.storage.StorageMgr;

/**
 * desc:健康银行
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/22.
 */
public class HealthBankFragment extends BasicFragment {

    private HeaderView headerView;
    private boolean isCreateView;
    private IPersonLogic personLogic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthblank, container, false);
        headerView = (HeaderView) view.findViewById(R.id.headView);
        headerView.title.setText(R.string.bank_title);
        initData();
        Log.d("HealthBankFragment", "onCreateView");
        isCreateView = true;
        return view;
    }

    private void initData() {
        UserInfo userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
//            setUpView(userInfo);

    }

    @Override
    protected void initLogics()
    {
        personLogic = (IPersonLogic) this.getLogicByInterfaceClass(IPersonLogic.class);
    }

    @Override
    public void recieveMsg(Message msg) {
        if (!isCreateView) {
            return;
        }
    }
    @Override
    public void handleStateMessage(Message msg) {
        if (!isCreateView) {
            return;
        }
        switch (msg.what) {
            case BussinessConstants.PersonMsgID.GET_PERSON_INFO_SUCCESS_MSG_ID:
                if(msg.obj!=null)
                {
                    PersonInfo personInfo = (PersonInfo)msg.obj;
//                    setUpView(personInfo);
                }
                break;
            case BussinessConstants.PersonMsgID.GET_PHYSIOLOGY_INFO_SUCCESS_MSG_ID:
                if(msg.obj!=null)
                {
                    PhysiologyInfo physiologyInfo = (PhysiologyInfo)msg.obj;
//                    setUpView(physiologyInfo);
                }
                break;
            case BussinessConstants.PersonMsgID.CHANGE_PERSONAL_DOC_SUCCESS_MSG_ID:
                if(msg.obj!=null)
                {
                    PersonalDocument personalDocument = (PersonalDocument)msg.obj;
//                    setUpView(personInfo);
                }
                break;
            case BussinessConstants.PersonMsgID.CHANGE_PHYSIOLOGICAL_INFO_SUCCESS_MSG_ID:
                if(msg.obj!=null)
                {
                    PhysiologyInfo physiologyInfo = (PhysiologyInfo)msg.obj;
//                    setUpView(personInfo);
                }
                break;
        }
    }

}

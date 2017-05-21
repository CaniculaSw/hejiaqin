package com.chinamobile.hejiaqin.business.ui.contact;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.model.contacts.SearchResultContacts;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class ModifyContactActivityTest extends ActivityUnitTestCase<ModifyContactActivity> {
    private ModifyContactActivity mActivity;

    private HeaderView titleLayout;

    private View headView;
    private CircleImageView headImg;
    private View nameView;
    private TextView nameText;
    private View numberView;
    private TextView numberText;

    public ModifyContactActivityTest() {
        super(ModifyContactActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setName("abc");
        contactsInfo.setContactMode(ContactsInfo.ContactMode.system);
        contactsInfo.setContactId("001");
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("137123412345");
        numberInfo.setType(1);
        contactsInfo.addNumber(numberInfo);

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                ModifyContactActivity.class);
       // intent.putExtra(BussinessConstants.Contact.INTENT_CONTACT_NUMBER_KEY, contactsInfo);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        // title
        titleLayout = (HeaderView) mActivity.findViewById(R.id.title);
        // 头像
        headView = mActivity.findViewById(R.id.contact_head_layout);
        headImg = (CircleImageView) mActivity.findViewById(R.id.contact_head_img);
        // 姓名
        nameView = mActivity.findViewById(R.id.contact_name_layout);
        nameText = (TextView) mActivity.findViewById(R.id.contact_name_hint);
        // 号码
        numberView = mActivity.findViewById(R.id.contact_number_layout);
        numberText = (TextView) mActivity.findViewById(R.id.contact_number_hint);
    }

    public void testInitView() {
        assertNotNull(titleLayout);
        assertNotNull(headView);
        assertNotNull(headImg);
        assertNotNull(nameView);
        assertNotNull(nameText);
        assertNotNull(numberView);
        assertNotNull(numberText);
    }

    public void testOnClick() {
        titleLayout.rightBtn.performClick();
        titleLayout.backImageView.performClick();
        nameView.performClick();
        numberView.performClick();
    }

    public void testHandleStateMessage() {
        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_SUCCESS_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_FAILED_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_SUCCESS_MSG_ID));

        ContactsInfo contactsInfo = new ContactsInfo();
        contactsInfo.setContactId("111");
        contactsInfo.setName("abc");
        contactsInfo.setContactMode(ContactsInfo.ContactMode.app);
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber("13455556666");
        contactsInfo.addNumber(numberInfo);
        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_SUCCESS_MSG_ID, contactsInfo));


        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_FAILED_MSG_ID));
    }

    private Message generateMessage(int what) {
        return generateMessage(what, null);
    }

    private Message generateMessage(int what, Object obj) {
        Message message = Message.obtain();
        message.what = what;
        message.obj = obj;
        return message;
    }
}

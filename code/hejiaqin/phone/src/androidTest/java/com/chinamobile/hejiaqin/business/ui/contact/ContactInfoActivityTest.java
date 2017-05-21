package com.chinamobile.hejiaqin.business.ui.contact;

import android.content.Intent;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.model.dial.DialInfoGroup;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class ContactInfoActivityTest extends ActivityUnitTestCase<ContactInfoActivity> {

    private ContactInfoActivity mActivity;

    private HeaderView titleLayout;
    private TextView mContactNameText;
    private CircleImageView mContactHeadImg;
    private View mContactInfoLay;
    private ImageView mContactInfoIcon;
    private ImageView mContactInfoSelected;
    private ImageView mContactInfoUnSelected;
    private View mDialInfoLay;
    private ImageView mDialInfoIcon;
    private ImageView mDialInfoSelected;
    private ImageView mDialInfoUnSelected;
    private ImageView dialImg;
    private ViewPager mViewPager;

    public ContactInfoActivityTest() {
        super(ContactInfoActivity.class);
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
                ContactInfoActivity.class);
        intent.putExtra(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY, contactsInfo);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        titleLayout = (HeaderView) mActivity.findViewById(R.id.title);
        mContactNameText = (TextView) mActivity.findViewById(R.id.contact_name_text);
        mContactHeadImg = (CircleImageView) mActivity.findViewById(R.id.contact_head_img);
        mContactInfoLay = mActivity.findViewById(R.id.contact_info_layout);
        mContactInfoIcon = (ImageView) mActivity.findViewById(R.id.contact_info_icon);
        mContactInfoSelected = (ImageView) mActivity.findViewById(R.id.contact_info_selected);
        mContactInfoUnSelected = (ImageView) mActivity.findViewById(R.id.contact_info_unselected);
        mDialInfoLay = mActivity.findViewById(R.id.dial_info_layout);
        mDialInfoIcon = (ImageView) mActivity.findViewById(R.id.dial_info_icon);
        mDialInfoSelected = (ImageView) mActivity.findViewById(R.id.dial_info_selected);
        mDialInfoUnSelected = (ImageView) mActivity.findViewById(R.id.dial_info_unselected);
        mViewPager = (ViewPager) mActivity.findViewById(R.id.id_stickynavlayout_viewpager);
        dialImg = (ImageView) mActivity.findViewById(R.id.dial_img);
    }

    public void testInitView() {
        assertNotNull(titleLayout);
        assertNotNull(mContactNameText);
        assertNotNull(mContactHeadImg);
        assertNotNull(mContactInfoLay);
        assertNotNull(mContactInfoIcon);
        assertNotNull(mContactInfoSelected);
        assertNotNull(mContactInfoUnSelected);
        assertNotNull(mDialInfoLay);
        assertNotNull(mDialInfoIcon);
        assertNotNull(mDialInfoSelected);
        assertNotNull(mDialInfoUnSelected);
        assertNotNull(mViewPager);
        assertNotNull(dialImg);
    }


    public void testOnClick(){
        mContactInfoLay.performClick();
        mDialInfoLay.performClick();
        dialImg.performClick();
        titleLayout.backImageView.performClick();
    }
    public void testHandleStateMessage() {
        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_SUCCESS_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.ADD_APP_CONTACTS_FAILED_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.DEL_APP_CONTACTS_SUCCESS_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.DEL_APP_CONTACTS_FAILED_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_SUCCESS_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.EDIT_APP_CONTACTS_FAILED_MSG_ID));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.DEL_CALL_RECORDS_SUCCESS_MSG_ID));

        List<DialInfoGroup> dialInfoGroupList = mActivity.genDialInfoGroup();
        mActivity.handleStateMessage(generateMessage(BussinessConstants.ContactMsgID.GET_CALL_RECORDS_SUCCESS_MSG_ID, dialInfoGroupList));

        mActivity.handleStateMessage(generateMessage(BussinessConstants.DialMsgID.CALL_RECORD_REFRESH_MSG_ID));
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

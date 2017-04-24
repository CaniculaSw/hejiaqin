package com.chinamobile.hejiaqin.business.ui.contact;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

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

    public void testPreconditons() {
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
}

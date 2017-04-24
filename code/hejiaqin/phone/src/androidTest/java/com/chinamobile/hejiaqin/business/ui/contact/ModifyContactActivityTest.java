package com.chinamobile.hejiaqin.business.ui.contact;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.model.contacts.NumberInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

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
        intent.putExtra(BussinessConstants.Contact.INTENT_CONTACTSINFO_KEY, contactsInfo);
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

    public void testPreconditons() {
        assertNotNull(titleLayout);
        assertNotNull(headView);
        assertNotNull(headImg);
        assertNotNull(nameView);
        assertNotNull(nameText);
        assertNotNull(numberView);
        assertNotNull(numberText);
    }
}

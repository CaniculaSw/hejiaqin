package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.os.Message;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.component.storage.StorageMgr;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/4/24 0024.
 */
public class UserInfoActivityTest extends ActivityUnitTestCase<UserInfoActivity> {
    private UserInfoActivity mActivity;

    private CircleImageView mUserAvatarIv;
    private TextView mUserAccountTv;
    HeaderView header;

    public UserInfoActivityTest() {
        super(UserInfoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        StorageMgr.getInstance().getSharedPStorage(getInstrumentation().getContext()).clearAll();
        StorageMgr.getInstance().getMemStorage().clearAll();
        Intent intent = new Intent(getInstrumentation().getTargetContext(), UserInfoActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mUserAvatarIv = (CircleImageView) mActivity.findViewById(R.id.more_user_avatar_ci);
        mUserAccountTv = (TextView) mActivity.findViewById(R.id.more_user_account_tv);
        header = (HeaderView) mActivity.findViewById(R.id.more_user_info_header);
    }

    public void testInitView() {
        assertNotNull(mUserAvatarIv);
        assertNotNull(mUserAccountTv);
        assertNotNull(header);
    }

    public void testOnClick() {
        header.backImageView.performClick();
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

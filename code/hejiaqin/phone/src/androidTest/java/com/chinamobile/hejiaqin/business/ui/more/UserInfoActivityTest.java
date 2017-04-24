package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

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

        Intent intent = new Intent(getInstrumentation().getTargetContext(), UserInfoActivity.class);
        startActivity(intent, null, null);

        mActivity = getActivity();
        assertNotNull(mActivity);

        mUserAvatarIv = (CircleImageView) mActivity.findViewById(R.id.more_user_avatar_ci);
        mUserAccountTv = (TextView) mActivity.findViewById(R.id.more_user_account_tv);
        header = (HeaderView) mActivity.findViewById(R.id.more_user_info_header);
    }

    public void testPreconditons() {
        assertNotNull(mUserAvatarIv);
        assertNotNull(mUserAccountTv);
        assertNotNull(header);
    }
}

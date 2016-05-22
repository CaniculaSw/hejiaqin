package com.chinamobile.hejiaqin.business.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonInfo;
import com.chinamobile.hejiaqin.business.model.person.PersonalDocument;
import com.chinamobile.hejiaqin.business.ui.setting.AboutActivity;
import com.chinamobile.hejiaqin.business.ui.setting.HelpActivity;
import com.chinamobile.hejiaqin.business.ui.myMesage.MyMessageActivity;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.person.PersonActivity;
import com.chinamobile.hejiaqin.business.ui.person.PhysiologyActivity;
import com.chinamobile.hejiaqin.business.ui.setting.SettingActivity;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.utils.StringUtil;

/**
 * desc: 设置
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/22.
 */
public class SettingFragment extends BasicFragment implements View.OnClickListener {

    private HeaderView headerView;
    private ImageView headerImageView;
    private TextView nickNameTextView;
    private TextView phoneNoTextView;

    private boolean isCreateView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        headerView = (HeaderView) view.findViewById(R.id.headView);
        headerView.title.setText(R.string.me_title);
        Log.d("SettingFragment", "onCreateView");
        initView(view);
        initData();
        isCreateView = true;
        return view;
    }

    @Override
    public void recieveMsg(Message msg) {

    }
    @Override
    public void handleStateMessage(Message msg) {
        if (!isCreateView) {
            return;
        }
        switch (msg.what) {
            case BussinessConstants.PersonMsgID.GET_PERSON_INFO_SUCCESS_MSG_ID:
                if (msg.obj != null) {
                    PersonInfo personInfo = (PersonInfo) msg.obj;
                    setUpView(personInfo);
                }
                break;
            case BussinessConstants.PersonMsgID.CHANGE_PERSONAL_DOC_SUCCESS_MSG_ID:
                if (msg.obj != null) {
                    PersonalDocument personalDocument = (PersonalDocument)msg.obj;
                    setUpView(personalDocument);
                }
                break;
            case BussinessConstants.PersonMsgID.UPLOAD_USER_AVATAR_IMAGE_SUCCESS_MSG_ID:
                if (msg.obj != null) {
                    String avatarPath = (String)msg.obj;
                    Picasso.with(this.getContext()).invalidate(avatarPath);
                    Picasso.with(this.getContext()).load(avatarPath).placeholder(R.mipmap.default_avatar).into(headerImageView);
                }
                break;
        }
    }

    private void initView(View view) {

        headerImageView = (ImageView) view.findViewById(R.id.img_head);
        nickNameTextView = (TextView) view.findViewById(R.id.tv_nickname);
        phoneNoTextView = (TextView) view.findViewById(R.id.tv_phone_no);

        LinearLayout line_person_info = (LinearLayout) view.findViewById(R.id.line_person_info);
        LinearLayout line_physical_data = (LinearLayout) view.findViewById(R.id.line_physical_data);
        LinearLayout line_my_set = (LinearLayout) view.findViewById(R.id.line_my_set);
        LinearLayout line_my_message = (LinearLayout) view.findViewById(R.id.line_my_message);
        LinearLayout line_help = (LinearLayout) view.findViewById(R.id.line_help);
        LinearLayout line_about = (LinearLayout) view.findViewById(R.id.line_about);
        Button logoutButton = (Button) view.findViewById(R.id.logout_button);
        line_person_info.setOnClickListener(this);
        line_physical_data.setOnClickListener(this);
        line_my_set.setOnClickListener(this);
        line_my_message.setOnClickListener(this);
        line_help.setOnClickListener(this);
        line_about.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_person_info:
                startActivity(new Intent(getActivity(), PersonActivity.class));
                break;
            case R.id.line_physical_data:
                startActivity(new Intent(getActivity(), PhysiologyActivity.class));
                break;
            case R.id.line_my_set:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.line_my_message:
                startActivity(new Intent(getActivity(), MyMessageActivity.class));
                break;
            case R.id.line_help:
                startActivity(new Intent(getActivity(), HelpActivity.class));
                break;
            case R.id.line_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.logout_button:
                logout();
                break;
            default:
                break;

        }
    }

    private void logout() {
        mListener.onAction(BussinessConstants.FragmentActionId.SETTING_FRAGMENT_LOGOUT_ACTION_ID, null);
    }


    private void initData() {
        UserInfo userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
        if (StringUtil.isNullOrEmpty(userInfo.getAvatar())) {
            headerImageView.setImageResource(R.mipmap.default_avatar);
        } else {
            Picasso.with(this.getContext()).load(userInfo.getAvatar()).placeholder(R.mipmap.default_avatar).into(headerImageView);
        }
        nickNameTextView.setText(userInfo.getName());
        phoneNoTextView.setText(userInfo.getPhone());
    }


    private void setUpView(PersonalDocument personalDocument) {
        nickNameTextView.setText(StringUtil.isNullOrEmpty(personalDocument.getName()) ? "" : personalDocument.getName());
    }

    private void setUpView(PersonInfo personInfo) {
        Picasso.with(this.getContext()).load(personInfo.getAvatar()).placeholder(R.mipmap.default_avatar).into(headerImageView);
        nickNameTextView.setText(StringUtil.isNullOrEmpty(personInfo.getName()) ? "" : personInfo.getName());
        phoneNoTextView.setText(personInfo.getPhone());
    }


}

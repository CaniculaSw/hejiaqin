package com.chinamobile.hejiaqin.business.ui.more;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.basic.view.stickylistview.StickyListHeadersListView;
import com.chinamobile.hejiaqin.business.ui.more.adapter.SelectContactAndBindAdapter;

import java.util.List;

/**
 * Created by eshaohu on 16/11/14.
 */
public class SelectContactAndBindActivity extends BasicActivity implements View.OnClickListener {
    private static final String TAG = "SelectContactAndBindActivity";
    private IContactsLogic contactsLogic;
    private ISettingLogic settingLogic;
    private StickyListHeadersListView contactListView;
    private SelectContactAndBindAdapter adapter;
    private TextView searchText;
    private TextView tipText;
    private HeaderView headerView;
    private View progressLayout;
    private TextView progressTips;
    private View searchLayout;
    private boolean isProgressShowing;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_contact_and_bind;
    }

    @Override
    protected void initView() {
        Context context = this;

        contactListView = (StickyListHeadersListView) findViewById(R.id.list);

        // 添加搜索框
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        searchLayout = inflater.inflate(R.layout.layout_contact_search_view, null);
        contactListView.addHeaderView(searchLayout);
        // 设置搜索显示的文字
        searchText = (TextView) searchLayout.findViewById(R.id.contact_search_text);
        // 添加点击事件
        searchLayout.findViewById(R.id.contact_search_layout).setOnClickListener(this);

        // 添加adapter
        adapter = new SelectContactAndBindAdapter(context, getHandler());
        contactListView.setAdapter(adapter);

        tipText = (TextView) findViewById(R.id.tip);

        headerView = (HeaderView) findViewById(R.id.header);
        headerView.title.setText(R.string.more_choose_contact);
        headerView.backImageView.setImageResource(R.mipmap.title_icon_close);
        headerView.backImageView.setOnClickListener(this);

        progressLayout = inflater.inflate(R.layout.layout_progress_tips, null);
        progressTips = (TextView) progressLayout.findViewById(R.id.progress_text);
        isProgressShowing = false;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initLogics() {
        contactsLogic = (IContactsLogic) this.getLogicByInterfaceClass(IContactsLogic.class);
        settingLogic = (ISettingLogic) this.getLogicByInterfaceClass(ISettingLogic.class);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.ContactMsgID.GET_APP_CONTACTS_SUCCESS_MSG_ID:
                List<ContactsInfo> contactsInfoList = (List<ContactsInfo>) msg.obj;
                adapter.setData(contactsInfoList);
                searchText.setText(String.format(this.getString(R.string.contact_search_hint_text), contactsInfoList.size()));
                break;
            case BussinessConstants.SettingMsgID.SENDING_BIND_REQUEST:
                switchHeaderView(true, getString(R.string.sending_bind_request));
                break;
            case BussinessConstants.SettingMsgID.STATUS_DELIVERY_OK:
            case BussinessConstants.SettingMsgID.STATUS_DISPLAY_OK:
                switchHeaderView(true, getString(R.string.waiting_for_respond));
                break;
            case BussinessConstants.SettingMsgID.STATUS_SEND_FAILED:
            case BussinessConstants.SettingMsgID.STATUS_UNDELIVERED:
                switchHeaderView(false, getString(R.string.sending_bind_request_failed));
                showToast(getString(R.string.sending_bind_request_failed), Toast.LENGTH_LONG, null);
                break;
            case BussinessConstants.SettingMsgID.BIND_SUCCESS:
                showToast("绑定成功", Toast.LENGTH_SHORT, null);
                settingLogic.bindSuccNotify();
                doBack();
                break;
            case BussinessConstants.SettingMsgID.BIND_DENIED:
                switchHeaderView(false, null);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initDate() {
        contactsLogic.fetchAppContactLst();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact_search_layout:
                // TODO
                enterSearchView();
                break;
            case R.id.back_iv:
                doBack();
                break;
            default:
                break;
        }
    }

    private void enterSearchView() {
        Intent intent = new Intent(this, MoreContactSearchActivity.class);
        intent.putExtra(MoreContactSearchActivity.Constant.INTENT_DATA_CONTACT_TYPE
                , MoreContactSearchActivity.Constant.CONTACT_TYPE_APP);
        startActivity(intent);
    }

    private void switchHeaderView(boolean showProgress, String tips) {
        if (showProgress) {
            contactListView.removeHeaderView(searchLayout);
            if (!isProgressShowing) {
                contactListView.addHeaderView(progressLayout);
            }
            if (tips != null && tips.length() > 0) {
                progressTips.setText(tips);
            }
            isProgressShowing = true;
        } else {
            contactListView.removeHeaderView(progressLayout);
            contactListView.removeHeaderView(searchLayout);
            contactListView.addHeaderView(searchLayout);
            isProgressShowing = false;
        }
    }
}

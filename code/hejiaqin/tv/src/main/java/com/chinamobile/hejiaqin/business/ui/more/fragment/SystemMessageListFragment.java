package com.chinamobile.hejiaqin.business.ui.more.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.dbApdater.SystemMessageDbAdapter;
import com.chinamobile.hejiaqin.business.manager.UserInfoCacheManager;
import com.chinamobile.hejiaqin.business.model.more.SystemMessage;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.more.SysMessageDetailActivity;
import com.chinamobile.hejiaqin.business.ui.more.adapter.SysMessageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/5/25.
 */
public class SystemMessageListFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "SystemMessageListFragment";
    private ListView msgListView;
    private List<SystemMessage> mMessageList;
    private SysMessageAdapter adapter;
    private TextView mDeleteButton;

    @Override
    protected void handleFragmentMsg(Message msg) {
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.EDIT_BUTTON_PRESSED:
                unSelectedAllData();
                mDeleteButton.setVisibility(mDeleteButton.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                adapter.setShow(adapter.isShow() ? false : true);
                adapter.notifyDataSetChanged();
                break;
            case BussinessConstants.SettingMsgID.CLEAN_MESSAGES_SELECTED_STATE:
                unSelectedAllData();
                break;
            case BussinessConstants.SettingMsgID.MESSAGE_FRAGMENT_SWITCH_OFF:
                unSelectedAllData();
                mDeleteButton.setVisibility(View.INVISIBLE);
                adapter.setShow(false);
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_message_sys_msg_list;
    }

    @Override
    protected void initView(View view) {
        Context context = getContext();

        msgListView = (ListView) view.findViewById(R.id.more_sys_message_list_lv);
        mDeleteButton = (TextView) view.findViewById(R.id.more_sys_msg_list_delete_tv);
        mDeleteButton.setOnClickListener(this);
        // 添加adapter
        adapter = new SysMessageAdapter(context);
        msgListView.setAdapter(adapter);

        msgListView.setClickable(true);
        msgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SystemMessage msg = mMessageList.get(position);
                Intent intent = new Intent(getActivity(), SysMessageDetailActivity.class);
                intent.putExtra("msgID", msg.getId());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        mMessageList = new ArrayList<SystemMessage>();

        mMessageList = SystemMessageDbAdapter.getInstance(getContext(), UserInfoCacheManager.getUserId(getContext()))
                .queryAll();
        adapter.setData(mMessageList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_sys_msg_list_delete_tv:
                deleteSelectedData();
                break;
            default:
                break;
        }
    }

    private void deleteSelectedData() {
        adapter.delSelectedSystemMessage();
    }

    private void unSelectedAllData() {
        adapter.unSelectAll();
    }

}

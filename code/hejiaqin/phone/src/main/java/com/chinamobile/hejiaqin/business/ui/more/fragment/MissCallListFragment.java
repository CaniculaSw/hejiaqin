package com.chinamobile.hejiaqin.business.ui.more.fragment;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.more.adapter.MissCallAdapter;

/**
 * Created by eshaohu on 16/5/25.
 */
public class MissCallListFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "MissCallListFragment";
    private MissCallAdapter adapter;
    TextView mDeleteButton;
    ListView msgListView;

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
        return R.layout.fragment_message_miss_call_list;
    }

    @Override
    protected void initView(View view) {
        Context context = getContext();

        msgListView = (ListView) view.findViewById(R.id.more_miss_call_message_list);
        // 添加搜索框
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        // 添加adapter
        adapter = new MissCallAdapter(context);
        msgListView.setAdapter(adapter);

        mDeleteButton = (TextView) view.findViewById(R.id.more_miss_call_list_delete);
        mDeleteButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
//        List<ContactCallLog> callLogList = CallLogApi.getAllContactCallLogList(CallLogApi.CALLLOG_TYPE_VIDEO,-1);
//        LogUtil.i(TAG,"callLogList size is: "+callLogList.size());
//        MissCallMessage testMessage1 = new MissCallMessage();
//        testMessage1.setDate(DateTimeUtil.getCurrentDateString("yyyy-MM-dd HH:mm:ss"));
//        testMessage1.setFrom("13776570335");
//        MissCallMessage testMessage2 = new MissCallMessage();
//
//        testMessage2.setDate(DateTimeUtil.getYYYYMMDDHHMMSS(DateTimeUtil.getYesterdayDate()));
//        testMessage2.setFrom("13776570335");
//
//        MissCallMessage testMessage3 = new MissCallMessage();
//        testMessage3.setDate("2016-03-15 14:02:03");
//        testMessage3.setFrom("13776570335");
//
//        MissCallMessage testMessage4 = new MissCallMessage();
//        testMessage4.setDate("2015-03-15 14:02:03");
//        testMessage4.setFrom("13776570335");
//
//        mMissCallMessages.add(testMessage1);
//        mMissCallMessages.add(testMessage2);
//        mMissCallMessages.add(testMessage3);
//        mMissCallMessages.add(testMessage4);

//        adapter.setData(callLogList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_miss_call_list_delete:
                deleteSelectedData();
                break;
            default:
                break;
        }
    }

    private void deleteSelectedData() {
        adapter.deleteSelectedData();
    }

    private void unSelectedAllData() {
        adapter.unSelectedAllData();
    }
}

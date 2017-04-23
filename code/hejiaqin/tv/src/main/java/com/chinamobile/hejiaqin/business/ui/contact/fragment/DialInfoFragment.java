package com.chinamobile.hejiaqin.business.ui.contact.fragment;

import android.os.Message;
import android.view.View;

import com.chinamobile.hejiaqin.business.model.dial.DialInfo;
import com.chinamobile.hejiaqin.business.model.dial.DialInfoGroup;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.tv.R;

import java.util.ArrayList;
import java.util.List;
/***/
public class DialInfoFragment extends BasicFragment implements View.OnClickListener {
    private static final String TAG = "DialInfoFragment";
//    private LayoutInflater inflater;
//
//    private LinearLayout dialInfoLayout;

    private List<DialInfoGroup> mDialInfoGroupList = new ArrayList<>();

    @Override
    protected void handleFragmentMsg(Message msg) {

    }

    @Override
    protected void handleLogicMsg(Message msg) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_dial_info;
    }

    @Override
    protected void initView(View view) {
//        inflater = (LayoutInflater) getContext().getSystemService
//                (Context.LAYOUT_INFLATER_SERVICE);
//        dialInfoLayout = (LinearLayout) view.findViewById(R.id.dial_info_layout);
    }

    @Override
    protected void initData() {
        refreshView();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
    }

    public void setDialInfo(List<DialInfoGroup> dialInfoGroupList) {
        if (null == dialInfoGroupList) {
            return;
        }
        mDialInfoGroupList.clear();
        mDialInfoGroupList.addAll(dialInfoGroupList);
    }
    /***/
    public void refreshView() {
//        if(!super.isCreateView)
//        {
//            return;
//        }
//        if (null == mDialInfoGroupList) {
//            return;
//        }
//
//
//        dialInfoLayout.removeAllViews();
//        for (DialInfoGroup dialInfoGroup : mDialInfoGroupList) {
//            if (null == dialInfoGroup.getGroupName() || null == dialInfoGroup.getDialInfoList()
//                    || dialInfoGroup.getDialInfoList().isEmpty()) {
//                continue;
//            }
//
//            View dialInfoGroupView = inflater.inflate(R.layout.layout_contact_dial_info_view, null);
//            TextView groupNameText = (TextView) dialInfoGroupView.findViewById(R.id.dial_group_name_text);
//            groupNameText.setText(dialInfoGroup.getGroupName());
//            groupNameText.setVisibility(View.VISIBLE);
//            dialInfoGroupView.findViewById(R.id.dial_info_item_layout).setVisibility(View.GONE);
//            dialInfoLayout.addView(dialInfoGroupView);
//
//            for (DialInfo dialInfo : dialInfoGroup.getDialInfoList()) {
//                View dialInfoView = inflater.inflate(R.layout.layout_contact_dial_info_view, null);
//                dialInfoView.findViewById(R.id.dial_group_name_text).setVisibility(View.GONE);
//                dialInfoView.findViewById(R.id.dial_info_item_layout).setVisibility(View.VISIBLE);
//
//                ImageView dialTypeImage = (ImageView) dialInfoView.findViewById(R.id.dial_type_icon);
//                dialTypeImage.setImageResource(getIconResIdByDialType(dialInfo.getType()));
//
//                TextView dialTypeText = (TextView) dialInfoView.findViewById(R.id.dial_type_text);
//                dialTypeText.setText(getStringResIdByDialType(dialInfo.getType()));
//
//                TextView dialTimeText = (TextView) dialInfoView.findViewById(R.id.dial_time_text);
//                dialTimeText.setText(dialInfo.getDialTime());
//
//                TextView dialDurationText = (TextView) dialInfoView.findViewById(R.id.dial_duration_text);
//                dialDurationText.setText(dialInfo.getDialDuration());
//                dialInfoLayout.addView(dialInfoView);
//            }
//        }
    }
    /***/
    public boolean hasData() {
        return null != mDialInfoGroupList && !mDialInfoGroupList.isEmpty();
    }

    private int getIconResIdByDialType(DialInfo.Type type) {
        if (null == type) {
            return R.drawable.icon_incoming;
        }
        switch (type) {
            case in:
                return R.drawable.icon_incoming;
            case out:
                return R.drawable.icon_outbound_call;
            case missed:
                return R.drawable.icon_missed_call;
            case reject:
                return R.drawable.icon_reject_call;
            default:
                return R.drawable.icon_incoming;
        }
    }

    private int getStringResIdByDialType(DialInfo.Type type) {
        if (null == type) {
            return R.string.contact_info_dial_incoming_text;
        }
        switch (type) {
            case in:
                return R.string.contact_info_dial_incoming_text;
            case out:
                return R.string.contact_info_dial_outbound_text;
            case missed:
                return R.string.contact_info_dial_missed_text;
            case reject:
                return R.string.contact_info_dial_reject_text;
            default:
                return R.string.contact_info_dial_incoming_text;
        }
    }

    public View getFirstFouseView()
    {
        return null;
    }
}

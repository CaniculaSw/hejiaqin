package com.chinamobile.hejiaqin.business.ui.more.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.more.SystemMessage;
import com.chinamobile.hejiaqin.business.ui.basic.BasicFragment;
import com.chinamobile.hejiaqin.business.ui.more.SysMessageDetailActivity;
import com.chinamobile.hejiaqin.business.ui.more.adapter.SysMessageAdapter;

import java.util.ArrayList;
import java.util.Iterator;
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
                intent.putExtra("msgTitle", msg.getTitle());
                intent.putExtra("msgDate", msg.getDate());
                intent.putExtra("msgBody", msg.getMsgBody());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        mMessageList = new ArrayList<SystemMessage>();
        SystemMessage testMessage1 = new SystemMessage();
        testMessage1.setDate("2016/04/20");
        testMessage1.setTitle("今夜20:20, 红包雨来袭");
        testMessage1.setMsgBody("1 当我在夜里独赴幽会的时候,鸟儿不叫,风儿不吹,街道两旁的房屋沉默地站立着. \n" +
                "是我自己的脚镯越走越响使我羞怯. \n" +
                "当我站在凉台上倾听他的足音,树叶不摇,河水静止像熟睡的哨兵膝上的刀剑. \n" +
                "是我自己的心在狂跳＿＿我不知道怎样使它宁静. \n" +
                "当我爱来了,坐在我身旁,当我的身躯震颤,我的眼睫下垂,夜更深了,风吹灯灭,云片在繁星上曳过轻纱. \n" +
                "是我自己胸前的珍宝放出光明.我不知道怎样把它遮起. \n" +
                "\n" +
                "2 若是你要忙着把水瓶灌满,来吧,到我的湖上来吧. \n" +
                "湖水将回绕在你的脚边,潺潺地说出它的秘密. \n" +
                "沙滩上有了欲来的雨云的阴影,云雾低垂在丛树的绿线上,像你眉上的浓发. \n" +
                "我深深地熟悉你脚步的韵律,它在我心中敲击. \n" +
                "来吧,到我的湖上来吧,如果你必须把水瓶灌满. \n" +
                "如果你想懒散闲坐,让你的水瓶飘浮在水面,来吧,到我的湖上来吧, \n" +
                "草坡碧绿,野花多得数不清. \n" +
                "你的思想将从你乌黑的眼眸中飞出,像鸟儿飞出窝巢. \n" +
                "你的披纱将褪落到脚上. \n" +
                "来吧,如果你要闲坐,到我的湖上来吧. \n" +
                "如果你想撇下嬉游跳进水里,来吧,到我的湖上来吧. \n" +
                "把你的蔚蓝的丝巾留在岸上;蔚蓝的水将没过你,盖住你. \n" +
                "水波将蹑足来吻你的颈项,在你耳边低语. \n" +
                "来吧,如果你想跳进水里,到我的湖上来吧. \n" +
                "如果你想发狂而投入死亡来吧,到我的湖上来吧. \n" +
                "它是清凉的,深到无底. \n" +
                "它沉黑得像无梦的睡眠. \n" +
                "在它的深处黑夜就是白天,歌曲就是静默. \n" +
                "来吧,如果你想投入死亡,到我的湖上来吧. \n" +
                "3 我一无所求,只站在林边树后. \n" +
                "倦意还逗留在黎明的眼上,露泣在空气里. \n" +
                "湿草的懒味悬垂在地面的薄雾中. \n" +
                "在榕树下你用乳油般柔嫩的手挤着牛奶. \n" +
                "我沉静地站立着. \n" +
                "我没有说出一个字.那是藏起的鸟儿在密叶中歌唱. \n" +
                "芒果树在村径上撒着繁花,蜜蜂一只事会嗡嗡飞来. \n" +
                "池塘边湿婆天的庙门开了,朝拜者开始诵经. \n" +
                "你把罐儿放在膝上挤着牛奶. \n" +
                "我提着空桶站立着. \n" +
                "我没有走近你. \n" +
                "天空和庙里的锣声一同醒起. \n" +
                "街尘在驱走的牛蹄下飞扬. \n" +
                "把汩汩发响的水瓶搂在腰上,女人们从河边走来. \n" +
                "你的钏镯丁当,乳沫溢出罐沿. \n" +
                "晨光渐逝而我没有走近你. ");
        SystemMessage testMessage2 = new SystemMessage();
        testMessage2.setDate("2016/04/20");
        testMessage2.setTitle("分享出去, 让小伙伴越来越多吧!");
        testMessage2.setMsgBody("4 我在路边行走,也不知道为什么,时忆已过午,和竹枝在风中簌簌作响. \n" +
                "横斜的影子伸臂拖住流光的双足 \n" +
                "布谷鸟都唱倦了. \n" +
                "我在路边行走,也不知道为什么. \n" +
                "低垂的树荫盖住水边的茅屋.有人正忙着工作,她的钏镯在一角放出音乐. \n" +
                "我在茅屋前面站着,我不知道为什么. \n" +
                "曲径穿过一片芥菜田地和几层芒果树木. \n" +
                "它经过村庙和渡头的市集. \n" +
                "我在这茅屋面前停住了,,我不知道为什么. \n" +
                "好几年前,三月风吹的一天,春天倦慵地低语,芒果花落在地上. \n" +
                "浪花跳起掠过立在渡头阶沿上的铜瓶. \n" +
                "我想三月风吹的这一天,我不知道为什么. \n" +
                "阴影更深,牛群归栏. \n" +
                "冷落的牧场上日色苍白,村人在河边待渡. \n" +
                "我缓步回去,我不知道为什么. \n" +
                "\n" +
                "5 我像麝鹿一样在林荫中奔走,为着自己的香气而发狂. \n" +
                "夜晚是五月正中的夜晚,清风是南国的清风. \n" +
                "我迷了路,我游荡着,我寻求那得不到的东西,我得到我所没有寻求的东西. \n" +
                "我自己的愿望的形象从我心中走出,跳起舞来. \n" +
                "这闪光的形象飞掠过去. \n" +
                "我想把它紧紧捉住,它躲开了又引着我飞走下去 \n" +
                "我寻求那得不到的东西,我得到我所没有寻求的东西. \n" +
                "\n" +
                "6 手握着手,眼恋着眼;这样开始了我们的心的纪录. \n" +
                "这是三月的月明之夜;空气里有凤仙花的芬芳;我的横笛抛在地上,你的花串也没有编成. \n" +
                "你我之间的爱像歌曲一样地单纯. \n" +
                "你橙黄色的面纱使我眼睛陶醉. \n" +
                "你给我编的茉莉花环使我心震颤,像是受了赞扬. \n" +
                "这是一个又予又留,又隐又现的游戏;有些微笑,有些娇羞,也有些甜柔的无用的抵拦. \n" +
                "你我之间的爱像歌曲一样单纯. \n" +
                "没有现在以外的神秘;不强求那做不到的事情;没有魅惑后面的阴影;没有黑暗深处的探索. \n" +
                "你我之间的爱像歌曲一样的单纯. \n" +
                "我们没有走出一切语言之外进入永远的沉默;我们没有向空举手寻求希望以外的东西. \n" +
                "我们付与,我们取得,这就够了. \n" +
                "我们没有把喜乐压成微尘来榨取痛苦之酒. \n" +
                "你我之间的爱像歌曲一样的单纯. \n" +
                "\n" +
                "拉宾德拉纳特·泰戈尔（1861年—1941年），印度著名诗人、文学家、社会活动家、哲学家和印度民族主义者。1861年5月7日，拉宾德拉纳特·泰戈尔出生于印度加尔各答一个富有的贵族家庭。1913年，他以《吉檀迦利》成为第一位获得诺贝尔文学奖的亚洲人。他的诗中含有深刻的宗教和哲学的见解，泰戈尔的诗在印度享有史诗的地位，代表作《吉檀迦利》、《飞鸟集》、《眼中沙》、《四个人》、《家庭与世界》、《园丁集》、《新月集》、《最后的诗篇》、《戈拉》、《文明的危机》等。");

        mMessageList.add(testMessage1);
        mMessageList.add(testMessage2);

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
        Iterator<SystemMessage> it = mMessageList.iterator();
        while (it.hasNext()) {
            SystemMessage systemMsg = it.next();
            if (systemMsg.isChecked()) {
                it.remove();
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void unSelectedAllData() {
        for (SystemMessage systemMsg : mMessageList) {
            systemMsg.setChecked(false);
        }
        adapter.notifyDataSetChanged();
    }

}

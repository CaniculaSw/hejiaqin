package com.chinamobile.hejiaqin.business.ui.courses;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.courses.ICoursesLogic;
import com.chinamobile.hejiaqin.business.model.courses.LectureInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.utils.StringUtil;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Kangxi Version 001
 * author: huangzq
 * Created: 2016/5/19.
 */
public class ForumDetailActivity extends BasicActivity {

    private ICoursesLogic coursesLogic;
    private int mId;
    private LectureInfo mLectureInfo;
    private HeaderView headerView;
    private JCVideoPlayer mVideoplayer;
    //    private VideoView forum_videoview;
    private ClickListener mClickListener;
    private TextView tv_keypoint;
    private TextView tv_authorIntro;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forum_detail;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView) findViewById(R.id.header_view);
        headerView.backImageView.setImageResource(R.mipmap.back);
        tv_authorIntro = (TextView) findViewById(R.id.tv_authorIntro);
        tv_keypoint = (TextView) findViewById(R.id.tv_keypoint);
        mVideoplayer = (JCVideoPlayer) findViewById(R.id.forum_videoplayer);
//        mVideoplayer.skProgress.setProgressDrawable(getResources().getDrawable(R.drawable.test_style));   //3.1版才公开(3.1版有bug)

//        原生播放器
//        forum_videoview = (VideoView) findViewById(R.id.forum_videoview);
//        forum_videoview.setVideoURI(Uri.parse("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"));
//        forum_videoview.setMediaController(new MediaController(this));
    }

    @Override
    protected void initDate() {

        if (getIntent() != null) {
            mId = getIntent().getIntExtra("id", -1);
            String title = StringUtil.isNullOrEmpty(getIntent().getStringExtra("title")) ? "" : getIntent().getStringExtra("title");
            headerView.title.setText(title);
        }

        if (mId > 0) {
            showWaitDailog();
            coursesLogic.lectureCourseDetail(mId);
        } else {
            showToast("抱歉，没有该课程，已被关闭", 1, null);
        }
    }

    @Override
    protected void initListener() {
        mClickListener = new ClickListener();
        headerView.backLayout.setOnClickListener(mClickListener);
    }

    @Override
    protected void initLogics() {
        coursesLogic = (ICoursesLogic) this.getLogicByInterfaceClass(ICoursesLogic.class);
    }

    @Override
    protected void handleStateMessage(Message msg) {
        dismissWaitDailog();
        super.handleStateMessage(msg);
        switch (msg.what) {
            case BussinessConstants.CoursesMsgID.FIND_LECTURE_COURSE_DETAIL_SUCCESS_MSG_ID:
                if (msg.obj != null) {
                    mLectureInfo = (LectureInfo) msg.obj;
                    setUpView();
                }
                break;
        }
    }

    private void setUpView() {
        if (mLectureInfo != null) {
            tv_authorIntro.setText(StringUtil.isNullOrEmpty(mLectureInfo.getAuthorIntro()) ? "" : mLectureInfo.getAuthorIntro());
            tv_keypoint.setText(StringUtil.isNullOrEmpty(mLectureInfo.getKeypoint()) ? "" : mLectureInfo.getKeypoint());
            String videoTitle = StringUtil.isNullOrEmpty(mLectureInfo.getTitle()) ? "" : mLectureInfo.getTitle();
            String videoUrl = BussinessConstants.ServerInfo.HTTP_ADDRESS + mLectureInfo.getMediapath();
            mVideoplayer.setUp(videoUrl, "");
            if(!StringUtil.isNullOrEmpty(mLectureInfo.getCover())) {
                Picasso.with(this).load(BussinessConstants.ServerInfo.HTTP_ADDRESS + mLectureInfo.getCover()).into(mVideoplayer.ivThumb);
//                Picasso.with(this).load("http://www.feizl.com/upload2007/2011_05/110505164429412.jpg").into(mVideoplayer.ivThumb);
            }}
    }

    class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backLayout:   //回退
                    finish();
                    break;
            }
        }
    }
}

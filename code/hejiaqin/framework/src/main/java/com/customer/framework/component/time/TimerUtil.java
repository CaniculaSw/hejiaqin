package com.customer.framework.component.time;

import android.os.Handler;
import android.os.Message;

public class TimerUtil extends Thread {
	private int mInterval;
	private Handler mHandler;
	private int mMessageID;
	private int mTotalSecond;
	private boolean mRunning;

	public TimerUtil(Handler handler, int messageID, int totalSecond) {
		this(handler, messageID, totalSecond, 1);
	}

	public TimerUtil(Handler handler, int messageID, int totalSecond, int interval) {
		super();
		this.mInterval = interval;
		this.mHandler = handler;
		this.mMessageID = messageID;
		this.mTotalSecond = totalSecond;
	}

	public void cancel() {
		mRunning = false;
	}

	@Override
	public void run() {
		mRunning = true;
		int remainTime = mTotalSecond;
		do {
			Message msg = new Message();
			msg.what = mMessageID;
			msg.obj = remainTime;
			mHandler.sendMessage(msg);
			try {
				Thread.sleep((long) mInterval * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (remainTime-- > 0 && mRunning);
	}
}

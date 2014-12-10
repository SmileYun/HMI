package com.cqupt.model.threaten;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Message;

import com.cqupt.entity.CanMsgInfo;
import com.cqupt.entity.CanMsgInfo.DISPLAYTYPE;
import com.cqupt.model.RecvThread.RawCanMsgHandler;
import com.cqupt.model.threaten.CanMsgCache.Segment;
import com.cqupt.persenter.Dispatcher;
import com.example.myblue_new.MainActivity;
import com.example.myblue_new.R;

public class BMHandler extends Dispatcher.AbHandler {
	
	private static final DISPLAYTYPE LEVEL = DISPLAYTYPE.BITMAP;
	
	public BMHandler() {
		super(LEVEL);
	}

	@Override
	public Bundle response(CanMsgInfo info) {
		Bundle bd=new Bundle();
		switch (((info[2] & 0xfc) >> 2)){
		int arg1=data[2] % 4;
		case 0x00: // rew
			if (arg1 == 0x01) {
				b.putInt("RID_1", R.drawable.rew);
				b.putInt("RID", R.drawable.rew_1);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.YJTIME);
				b.putInt("CJ", 0x10);
			} else if (arg1 == 0x02) {
				b.putInt("RID_1", R.drawable.rew);
				b.putInt("RID", R.drawable.rew_2);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.EJTIME);
				b.putInt("CJ", 0x20);
			}
			break;
		case 0x01:// fcw
			if (arg1 == 0x01) {
				b.putInt("RID_1", R.drawable.fcw);
				b.putInt("RID", R.drawable.fcw_1);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.YJTIME);
				b.putInt("CJ", 0x11);
			} else if (arg1 == 0x02) {
				b.putInt("RID_1", R.drawable.fcw);
				b.putInt("RID", R.drawable.fcw_2);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.EJTIME);
			}
			break;
		case 0x02:// icw
			if (arg1 == 0x01 && "left".equals(Dger_Dir)) {
				b.putInt("RID_1", R.drawable.icw_left);
				b.putInt("RID", R.drawable.icw_1_left);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.YJTIME);
				b.putInt("CJ", 0x12);
			} else if (arg1 == 0x01
					&& "right".equals(Dger_Dir)) {
				b.putInt("RID_1", R.drawable.icw_right);
				b.putInt("RID", R.drawable.icw_1_right);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.YJTIME);
				b.putInt("CJ", 0x22);
			} else if (arg1 == 0x02
					&& "left".equals(Dger_Dir)) {
				b.putInt("RID_1", R.drawable.icw_left);
				b.putInt("RID", R.drawable.icw_2_left);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.EJTIME);
			} else if (msg2.arg1 == 0x02
					&& "right".equals(Dger_Dir)) {
				b.putInt("RID_1", R.drawable.icw_right);
				b.putInt("RID", R.drawable.icw_2_right);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.EJTIME);
			}
			break;
		case 0x03:// cfcw
			if (arg1 == 0x01) {
				b.putInt("RID_1", R.drawable.cfcw);
				b.putInt("RID", R.drawable.cfcw_1);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.YJTIME);
			} else if (arg1 == 0x02) {
				b.putInt("RID_1", R.drawable.cfcw);
				b.putInt("RID", R.drawable.cfcw_2);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.EJTIME);
			}
			break;
		case 0x05:// dnpw
			if (arg1 == 0x01) {
				b.putInt("RID_1", R.drawable.dnpw);
				b.putInt("RID", R.drawable.dnpw_1);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.YJTIME);
			} else if (msg5.arg1 == 0x02) {
				b.putInt("RID_1", R.drawable.dnpw);
				b.putInt("RID", R.drawable.dnpw_2);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.EJTIME);
			}
			break;

		case 0x06:// lcw
			if (msg6.arg1 == 0x01 && "left".equals(Dger_Dir)) {
				b.putInt("RID_1", R.drawable.lcw_left);
				b.putInt("RID", R.drawable.lcw_1_left);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.YJTIME);
			} else if (msg6.arg1 == 0x01
					&& "right".equals(Dger_Dir)) {
				b.putInt("RID_1", R.drawable.lcw_right);
				b.putInt("RID", R.drawable.lcw_1_right);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.YJTIME);
			} else if (msg6.arg1 == 0x02
					&& "left".equals(Dger_Dir)) {
				b.putInt("RID_1", R.drawable.lcw_left);
				b.putInt("RID", R.drawable.lcw_2_left);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.EJTIME);
			} else if (msg6.arg1 == 0x02
					&& "right".equals(Dger_Dir)) {
				b.putInt("RID_1", R.drawable.lcw_right);
				b.putInt("RID", R.drawable.lcw_2_right);
				b.putInt("RRID", AudioManager.STREAM_ALARM);
				b.putInt("time", MainActivity.EJTIME);
			}
			break;

		}
		
		return bd;
	}

}

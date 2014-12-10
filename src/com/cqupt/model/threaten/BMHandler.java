package com.cqupt.model.threaten;

import android.R.integer;
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

	private static final int LEVEL1TIME = 500;
	private static final int LEVEL2TIME = 200;
	private static final int AUDIO = AudioManager.STREAM_ALARM;

	@Override
	public Bundle response(CanMsgInfo info) {
		Bundle bd = new Bundle();
		int alaLevel = info[2] % 4;
		int dir = (info[6] >> 6) & 0x03;

		switch (((info[2] & 0xfc) >> 2)) {
		case 0x00: // rew
			if (alaLevel == 0x01) {

				bundlePutInt(bd, R.drawable.rew, R.drawable.rew_1, AUDIO, LEVEL1TIME);

			} else if (alaLevel == 0x02) {

				bundlePutInt(bd, R.drawable.rew, R.drawable.rew_2, AUDIO, LEVEL2TIME);

			}
			break;
		case 0x01:// fcw
			if (alaLevel == 0x01) {

				bundlePutInt(bd, R.drawable.fcw, R.drawable.fcw_1, AUDIO, LEVEL1TIME);

			} else if (alaLevel == 0x02) {

				bundlePutInt(bd, R.drawable.fcw, R.drawable.fcw_2, AUDIO, LEVEL2TIME);

			}
			break;
		case 0x02:// icw
			if (alaLevel == 0x01 && dir == 0x01) {

				bundlePutInt(bd, R.drawable.icw_left, R.drawable.icw_1_left, AUDIO, LEVEL1TIME);

			} else if (alaLevel == 0x01 && dir == 0x02) {

				bundlePutInt(bd, R.drawable.icw_right, R.drawable.icw_1_right, AUDIO, LEVEL1TIME);

			} else if (alaLevel == 0x02 && dir == 0x01) {

				bundlePutInt(bd, R.drawable.icw_left, R.drawable.icw_2_left, AUDIO, LEVEL2TIME);

			} else if (alaLevel == 0x02 && dir == 0x02) {

				bundlePutInt(bd, R.drawable.icw_right, R.drawable.icw_2_right, AUDIO, LEVEL2TIME);

			}
			break;
		case 0x03:// cfcw
			if (alaLevel == 0x01) {

				bundlePutInt(bd, R.drawable.cfcw, R.drawable.cfcw_1, AUDIO, LEVEL1TIME);

			} else if (alaLevel == 0x02) {

				bundlePutInt(bd, R.drawable.cfcw, R.drawable.cfcw_2, AUDIO, LEVEL2TIME);

			}
			break;
		case 0x05:// dnpw
			if (alaLevel == 0x01) {

				bundlePutInt(bd, R.drawable.dnpw, R.drawable.dnpw_1, AUDIO, LEVEL1TIME);

			} else if (alaLevel == 0x02) {

				bundlePutInt(bd, R.drawable.dnpw, R.drawable.dnpw_2, AUDIO, LEVEL2TIME);

			}
			break;

		case 0x06:// lcw
			if (alaLevel == 0x01 && dir == 0x01) {

				bundlePutInt(bd, R.drawable.lcw_left, R.drawable.lcw_1_left, AUDIO, LEVEL1TIME);

			} else if (alaLevel == 0x01 && dir == 0x02) {

				bundlePutInt(bd, R.drawable.lcw_right, R.drawable.lcw_1_right, AUDIO, LEVEL1TIME);

			} else if (alaLevel == 0x02 && dir == 0x01) {

				bundlePutInt(bd, R.drawable.lcw_left, R.drawable.lcw_2_left, AUDIO, LEVEL2TIME);

			} else if (alaLevel == 0x02 && dir == 0x02) {

				bundlePutInt(bd, R.drawable.lcw_right, R.drawable.lcw_2_right, AUDIO, LEVEL2TIME);

			}
			break;

		}
		return bd;
	}

	private Bundle bundlePutInt(Bundle bundle, int bmapID1, int bmapID2, int audio, int time) {
		bundle.putInt("BMAP1", bmapID1);
		bundle.putInt("BMAP2", bmapID2);
		bundle.putInt("AUDIO", audio);
		bundle.putInt("TIME", time);
		return bundle;
	}

}

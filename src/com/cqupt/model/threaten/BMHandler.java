package com.cqupt.model.threaten;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Message;

import com.cqupt.entity.CanMsgInfo;
import com.cqupt.entity.CanMsgInfo.DISPLAYTYPE;
import com.cqupt.model.RecvThread.RawCanMsgHandler;
import com.cqupt.model.threaten.CanMsgCache.Segment;
import com.cqupt.persenter.Dispatcher;

public class BMHandler extends Dispatcher.AbHandler {
	
	private static final DISPLAYTYPE LEVEL = DISPLAYTYPE.BITMAP;
	
	public BMHandler() {
		super(LEVEL);
	}

	@Override
	public Bundle response(CanMsgInfo info) {
		Bundle bd = new Bundle();
		byte[] b = info.getData(); 
		int time , imdrid,im2;
		bd.putInt("level", this.LEVEL.ordinal());
		return bd;
	}

}

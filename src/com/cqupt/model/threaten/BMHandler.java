package com.cqupt.model.threaten;

import android.os.Bundle;

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
		return null;
	}

}

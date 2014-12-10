package com.cqupt.model.threaten;

import com.cqupt.entity.CanMsgInfo;
import com.cqupt.model.RecvThread.RawCanMsgHandler;
import com.cqupt.model.threaten.CanMsgCache.Segment;
import com.cqupt.persenter.Dispatcher;

public class BMHandler extends Dispatcher.AbHandler {
	
	public static final int LEVEL = 1;
	
	public BMHandler() {
		super(LEVEL);
	}

	@Override
	public void response(CanMsgInfo info) {
		
	}

}

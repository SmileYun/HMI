package com.cqupt.model.threaten;

import com.cqupt.entity.CanMsgInfo;
import com.cqupt.persenter.Dispatcher;
import com.cqupt.persenter.Dispatcher.AbHandler;

public class SVHandler extends Dispatcher.AbHandler {

	public static final int LEVEL = 2;
	
	public SVHandler() {
		super(LEVEL);
	}
	
	@Override
	public void response(CanMsgInfo info) {
		
	}
}

package com.cqupt.model.threaten;

import android.os.Bundle;

import com.cqupt.entity.CanMsgInfo;
import com.cqupt.entity.CanMsgInfo.DISPLAYTYPE;
import com.cqupt.persenter.Dispatcher;
import com.cqupt.persenter.Dispatcher.AbHandler;

public class SVHandler extends Dispatcher.AbHandler {

private static final DISPLAYTYPE LEVEL = DISPLAYTYPE.SURFACEVIEW;
	
	public SVHandler() {
		super(LEVEL);
	}

	@Override
	public Bundle response(CanMsgInfo info) {
		return null;
	}
}

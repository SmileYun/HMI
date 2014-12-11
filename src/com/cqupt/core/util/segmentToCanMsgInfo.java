package com.cqupt.core.util;

import com.cqupt.entity.CanMsgInfo;
import com.cqupt.model.threaten.CanMsgCache.Segment;

public class segmentToCanMsgInfo {

	public static CanMsgInfo segmentToCanMsgInfo(Segment segment) {
		CanMsgInfo canMsgInfo=new CanMsgInfo(segment);
		canMsgInfo.setId(segment.getCanID());
		canMsgInfo.setData(segment.getData());
		return canMsgInfo;
	}

}

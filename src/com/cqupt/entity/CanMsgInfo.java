package com.cqupt.entity;

import com.cqupt.model.threaten.CanMsgCache.Segment;



public class CanMsgInfo {
	private byte[] mCanID; // 2
	
	private byte[] mData; // 8
	
	public enum DISPLAYTYPE{BITMAP, SURFACEVIEW, UNKNOW}
	
	
	

	public CanMsgInfo(Segment info) {
		mCanID = info.getCanID();
		mData = info.getData();
	}

	public CanMsgInfo(byte[] id, byte[] data) {
		super();
		this.mCanID = id;
		this.mData = data;
	}

	public byte[] getId() {
		return mCanID;
	}

	public void setId(byte[] id) {
		this.mCanID = id;
	}

	public byte[] getData() {
		return mData;
	}

	public void setData(byte[] data) {
		this.mData = data;
	}
	
	/**
	 * 
	 * ���ش�������   BMHandler.LEVEL SVHandler.LEVEL
	 * @return
	 * DISPLAYTYPE    ��������
	 */
	public DISPLAYTYPE getType(){
		DISPLAYTYPE _type = DISPLAYTYPE.UNKNOW;
		return _type;
	}
	
	
}
